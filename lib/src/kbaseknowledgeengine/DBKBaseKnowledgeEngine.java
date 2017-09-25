package kbaseknowledgeengine;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import kbaseknowledgeengine.WSAdminHelper.ObjectInfo;
import kbaseknowledgeengine.cfg.AppConfig;
import kbaseknowledgeengine.cfg.ConnectorConfig;
import kbaseknowledgeengine.cfg.IExecConfigLoader;
import kbaseknowledgeengine.db.AppJob;
import kbaseknowledgeengine.db.ConnJob;
import kbaseknowledgeengine.db.IJob;
import kbaseknowledgeengine.db.MongoStorage;
import kbaseknowledgeengine.db.MongoStorageException;
import kbaseknowledgeengine.db.WSEvent;
import kbaserelationengine.CleanKEAppResultsParams;
import kbaserelationengine.GetKEAppDescriptorParams;
import kbaserelationengine.KBaseRelationEngineServiceClient;
import kbaserelationengine.KEAppDescriptor;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.narrativejobservice.JobState;
import us.kbase.narrativejobservice.NarrativeJobServiceClient;
import us.kbase.narrativejobservice.RunJobParams;

public class DBKBaseKnowledgeEngine implements IKBaseKnowledgeEngine {
    private final MongoStorage store;
    private final Map<String, AppConfig> appConfigs;
    private final Map<String, ConnectorConfig> connConfigs;
    private final URL executionEngineUrl;
    private final Set<String> admins;
    private final Set<Thread> monitors = Collections.synchronizedSet(new HashSet<>());
    private final WSEventProcessor eventProcessor;
    private final Map<String, List<ConnectorConfig>> storageTypeToConnectorCfg;
    private final AuthToken keAdminToken;
    private final URL wsUrl;
    private final URL srvWizUrl;
    private final WSAdminHelper wsAdminHelper;
    
    public static final String MONGOEXE_DEFAULT = "/opt/mongo/bin/mongod";
    public static final boolean SUPPORT_COPY_OPERATIONS = false;
    
    public DBKBaseKnowledgeEngine(String mongoHosts, String mongoDb, String mongoUser,
            String mongoPassword, URL executionEngineUrl, String admins,
            Map<String, String> srvConfig, AuthToken keAdminToken, IExecConfigLoader ecl) 
                    throws MongoStorageException, IOException, UnauthorizedException {
        if (mongoHosts == null || mongoHosts.trim().length() == 0) {
            throw new MongoStorageException("Mongo host is not set in secure config parameters");
        }
        store = new MongoStorage(mongoHosts, mongoDb, mongoUser, mongoPassword, null);
        List<AppConfig> appCfgList = ecl.loadAppConfigs();
        appConfigs = appCfgList.stream().collect(Collectors.toMap(item -> item.getApp(), 
                Function.identity()));
        List<ConnectorConfig> connCfgList = ecl.loadConnectorConfigs();
        connConfigs = connCfgList.stream().collect(Collectors.toMap(item -> item.getConnectorApp(),
                Function.identity()));
        storageTypeToConnectorCfg = connCfgList.stream()
                .collect(Collectors.groupingBy(ConnectorConfig::getWorkspaceType));
        this.executionEngineUrl = executionEngineUrl;
        this.admins = new HashSet<>(Arrays.asList(admins.split(",")));
        this.keAdminToken = keAdminToken;
        eventProcessor = new WSEventProcessor(store, new WSEventProcessor.WSEventListener() {
            
            @Override
            public void objectVersionCreated(WSEvent evt) {
                DBKBaseKnowledgeEngine.this.objectVersionCreated(evt);
            }
        }, ecl);
        srvWizUrl = new URL(srvConfig.get("srv-wiz-url"));
        wsUrl = new URL(srvConfig.get("workspace-url"));
        wsAdminHelper = new WSAdminHelper(wsUrl, keAdminToken);
    }

    @Override
    public void cleanConnectorErrors(AuthToken authPart) {
        checkAdmin(authPart);
        List<ConnJob> connJobs = store.loadAllConnJobs();
        for (ConnJob job : connJobs) {
            if (job.getUser().equals("<owner>") || 
                    job.getState().equals(MongoStorage.JOB_STATE_ERROR)) {
                store.deleteConnJob(job.getJobId());
            }
        }
    }
    
    protected void objectVersionCreated(WSEvent evt) {
        evt.processed = true;
        setEventProcessed(evt);
        if (evt.version == null && !SUPPORT_COPY_OPERATIONS) {
            return;
        }
        runConnector(evt);
    }
    
    private void setEventProcessed(WSEvent evt) {
        try {
            store.updateEvent(evt);
            WSEvent evt2 = store.loadEvent(evt.accessGroupId, evt.accessGroupObjectId, 
                    evt.version, evt.timestamp, evt.eventType);
            if (!evt.equals(evt2)) {
                throw new IllegalStateException("Event wasn't stored properly");
            }
        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
        }
    }
    
    public void stopEventProcessor() {
        eventProcessor.stopWatcher();
    }
    
    private void checkAdmin(AuthToken auth) {
        if (!admins.contains(auth.getUserName())) {
            throw new IllegalStateException("Only admin can preform this operation");
        }
    }
    
    @Override
    public List<AppStatus> getAppsStatus(AuthToken authPart,
            RpcContext jsonRpcContext) {
        checkAdmin(authPart);
        List<AppStatus> ret = new ArrayList<>();
        Map<String, AppJob> jobs = store.getLastJobsPerApp();
        for (String app : appConfigs.keySet()) {
            AppConfig cfg = appConfigs.get(app);
            AppStatus st = new AppStatus().withApp(app)
                    .withAppTitle(cfg.getTitle());
            AppJob job = jobs.get(app);
            if (job != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long scheduleTime = job.getQueuedEpochMs() + 
                        30 * 24 * 3600 * 1000;
                try {
                    long startingTime = sdf.parse(cfg.getStartingFromDate()).getTime();
                    // TODO: Use regularity from AppConfig
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date(startingTime));
                    while (cal.getTimeInMillis() < job.getQueuedEpochMs()) {
                        cal.add(Calendar.MONTH, 1);
                    }
                    scheduleTime = cal.getTimeInMillis();
                } catch (Exception e) {
                    System.out.println("Error setting schedule time: " + e.getMessage());
                }
                st.withJobId(job.getJobId())
                .withState(job.getState())
                .withOutput(job.getMessage())
                .withUpdatedReNodes(asLong(job.getUpdatedReNodes()))
                .withNewReNodes(asLong(job.getNewReNodes()))
                .withNewReLinks(asLong(job.getNewReLinks()))
                .withQueuedEpochMs(job.getQueuedEpochMs())
                .withStartedEpochMs(job.getStartedEpochMs())
                .withFinishedEpochMs(job.getFinishedEpochMs())
                .withScheduledEpochMs(scheduleTime)
                .withJobId(job.getJobId());
            }
            ret.add(st);
        }
        return ret;
    }
    
    private static Long asLong(Integer value) {
        return value == null ? null : (long)(int)value;
    }
    
    @Override
    public List<ConnectorStatus> getConnectorsStatus(AuthToken authPart,
            RpcContext jsonRpcContext) {
        checkAdmin(authPart);
        List<ConnJob> jobs = store.loadAllConnJobs();
        List<ConnectorStatus> ret = new ArrayList<>();
        for (ConnJob job : jobs) {
            ConnectorConfig cfg = connConfigs.get(job.getConnectorApp());
            String objRef = job.getObjRef();
            ConnectorStatus cs = new ConnectorStatus().withConnectorApp(cfg.getConnectorApp())
                    .withConnectorTitle(cfg.getTitle()).withObjRef(objRef)
                    .withObjType(cfg.getWorkspaceType()).withUser(job.getUser())
                    .withOutput(job.getMessage()).withState(job.getState())
                    .withNewReNodes(asLong(job.getNewReNodes()))
                    .withUpdatedReNodes(asLong(job.getUpdatedReNodes()))
                    .withNewReLinks(asLong(job.getNewReLinks()))
                    .withQueuedEpochMs(job.getQueuedEpochMs())
                    .withStartedEpochMs(job.getStartedEpochMs())
                    .withFinishedEpochMs(job.getFinishedEpochMs())
                    .withJobId(job.getJobId());
            ret.add(cs);
        }
        return ret;
    }
    
    @Override
    public RunAppOutput runApp(RunAppParams params, AuthToken authPart,
            RpcContext jsonRpcContext) {
        checkAdmin(authPart);
        String app = params.getApp();
        AppConfig cfg = appConfigs.get(app);
        if (cfg == null) {
            throw new IllegalStateException("App [" + app + "] is not found in registry");
        }
        AppJob job = store.getLastJobForApp(app);
        if (job != null && !isJobDone(job)) {
            throw new IllegalStateException("App [" + app + "] has unfinished job");
        }
        try {
            // Let's check first that we still can change Mongo database (there is no newer version
            // of service working in parallel).
            store.checkDbVersion();
            NarrativeJobServiceClient njs = new NarrativeJobServiceClient(executionEngineUrl, authPart);
            njs.setAllSSLCertificatesTrusted(true);
            njs.setIsInsecureHttpConnectionAllowed(true);
            Map<String, String> jobParams = new HashMap<>();
            jobParams.put("app_guid", cfg.getApp());
            String jobId = njs.runJob(new RunJobParams().withMethod(cfg.getModuleMethod())
                    .withServiceVer(cfg.getVersionTag()).withParams(Arrays.asList(
                            new UObject(jobParams))));
            System.out.println("Runnng app [" + app + "] with job id=" + jobId);
            job = new AppJob();
            job.setApp(app);
            job.setJobId(jobId);
            job.setQueuedEpochMs(System.currentTimeMillis());
            job.setState(MongoStorage.JOB_STATE_QUEUED);
            job.setUser(authPart.getUserName());
            store.insertUpdateAppJob(job);
            KBaseRelationEngineServiceClient reCl = null;
            if (keAdminToken != null) {
                reCl = new KBaseRelationEngineServiceClient(srvWizUrl, keAdminToken);
                reCl.setServiceVersion("dev");
                reCl.setIsInsecureHttpConnectionAllowed(true);
            }
            startBackgroundMonitor(njs, reCl, job);
            return new RunAppOutput().withJobId(jobId);
        } catch (Exception e) {
            throw new IllegalStateException("Error running app [" + app + "]: " + 
                    e.getMessage(), e);
        }
    }
    
    public void runConnector(WSEvent evt) {
        ConnectorConfig cfg = storageTypeToConnectorCfg.get(evt.storageObjectType).get(0);
        try {
            ConnJob job = new ConnJob();
            job.setConnectorApp(cfg.getConnectorApp());
            // Let's check first that we still can change Mongo database (there is no newer version
            // of service working in parallel).
            store.checkDbVersion();
            ObjectInfo objInfo = wsAdminHelper.getObjectInfo(evt.accessGroupId, 
                    evt.accessGroupObjectId, evt.version);
            if (objInfo.getFeatureCount() == null || objInfo.getFeatureCount() > 10000) {
                return;  // We now support Genome objects only.
            }
            NarrativeJobServiceClient njs = new NarrativeJobServiceClient(executionEngineUrl, 
                    keAdminToken);
            njs.setAllSSLCertificatesTrusted(true);
            njs.setIsInsecureHttpConnectionAllowed(true);
            Map<String, String> jobParams = new HashMap<>();
            jobParams.put("app_guid", cfg.getConnectorApp());
            String objRef = objInfo.getResolvedRef();
            jobParams.put("obj_ref", objRef);
            String jobId = njs.runJob(new RunJobParams().withMethod(cfg.getModuleMethod())
                    .withServiceVer(cfg.getVersionTag()).withParams(Arrays.asList(
                            new UObject(jobParams))));
            System.out.println("Runnig connector [" + cfg.getConnectorApp() + "] with job id=" + 
                            jobId);
            job.setJobId(jobId);
            job.setObjRef(objRef);
            job.setQueuedEpochMs(System.currentTimeMillis());
            job.setState(MongoStorage.JOB_STATE_QUEUED);
            job.setUser(objInfo.getOwner());
            store.insertUpdateConnJob(job);
            startBackgroundMonitor(njs, null, job);
        } catch (Exception e) {
            System.out.println("Error running connector [" + cfg.getConnectorApp() + "]: " + 
                    e.getMessage());
        }
    }

    private Thread startBackgroundMonitor(final NarrativeJobServiceClient njs, 
            final KBaseRelationEngineServiceClient reCl, final IJob job) {
        final String jobId = job.getJobId();
        Thread ret = new Thread(new Runnable() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        boolean toUpdate = false;
                        boolean toBreak = false;
                        try {
                            JobState js = njs.checkJob(jobId);
                            if (js.getCanceled() != null && js.getCanceled() == 1L) {
                                job.setState(MongoStorage.JOB_STATE_ERROR);
                                job.setMessage("Job was canceled");
                                toUpdate = true;
                                toBreak = true;
                            } else if (js.getFinished() != null && js.getFinished() == 1L) {
                                if (js.getError() != null) {
                                    job.setState(MongoStorage.JOB_STATE_ERROR);
                                    job.setMessage(js.getError().getMessage());
                                    System.out.println("Job " + jobId + " failed, error: " + 
                                            job.getMessage());
                                } else {
                                    job.setState(MongoStorage.JOB_STATE_FINISHED);
                                    System.out.println("Job " + jobId + " is done");
                                    List<Object> retArr = UObject.transformObjectToObject(
                                            js.getResult(), List.class);
                                    Map<String, Object> retMap = (Map)retArr.get(0);
                                    System.out.println("Output for job [" + jobId + "]: " +
                                            UObject.transformObjectToString(retMap));
                                    job.setNewReNodes(asInteger(retMap.get("new_re_nodes")));
                                    job.setUpdatedReNodes(asInteger(
                                            retMap.get("updated_re_nodes")));
                                    job.setNewReLinks(asInteger(retMap.get("new_re_links")));
                                    job.setMessage((String)retMap.get("message"));
                                    System.out.println("New-nodes: " + job.getNewReNodes() + ", " +
                                            "updated-nodes: " + job.getUpdatedReNodes() + ", " + 
                                            "new-links: " + job.getNewReLinks());
                                }
                                if (job.getStartedEpochMs() == null) {
                                    job.setStartedEpochMs(js.getExecStartTime());
                                }
                                job.setFinishedEpochMs(js.getFinishTime());
                                toUpdate = true;
                                toBreak = true;
                            } else {
                                String njsState = js.getJobState();
                                if (njsState != null && njsState.equals("in-progress")) {
                                    String newState = MongoStorage.JOB_STATE_STARTED;
                                    toUpdate = !job.getState().equals(newState);
                                    if (toUpdate) {
                                        System.out.println("Job " + jobId + " is in progress...");
                                        job.setState(newState);
                                        job.setStartedEpochMs(js.getExecStartTime());
                                    }
                                }
                                if (job instanceof AppJob) {
                                    String appGuid = ((AppJob)job).getApp();
                                    KEAppDescriptor appDescr = requestAppNodesLinks(reCl, appGuid);
                                    if (appDescr != null) {
                                        job.setNewReNodes(asInteger(appDescr.getNodesCreated()));
                                        job.setNewReLinks(asInteger(appDescr.getRelationsCreated()));
                                        toUpdate = true;
                                        // TODO: only update if counters are changed
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error checking job state for " + 
                                    "[" + job.getJobId() + "]:");
                            e.printStackTrace(System.out);
                            job.setState(MongoStorage.JOB_STATE_ERROR);
                            job.setMessage("Error monitoring job: " + e.getMessage());
                            toUpdate = true;
                            toBreak = true;
                        }
                        if (toUpdate) {
                            if (job instanceof AppJob) {
                                store.insertUpdateAppJob((AppJob)job);
                            } else if (job instanceof ConnJob) {
                                store.insertUpdateConnJob((ConnJob)job);
                            } else {
                                throw new IllegalStateException("Unsupported job type: " + 
                                        job.getClass().getSimpleName());
                            }
                        }
                        if (toBreak) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error watching job state for " +
                                "[" + job.getJobId() + "]:");
                        e.printStackTrace(System.out);
                        break;
                    }
                }
                monitors.remove(Thread.currentThread());
            }
        });
        ret.start();
        monitors.add(ret);
        return ret;
    }
    
    private KEAppDescriptor requestAppNodesLinks(KBaseRelationEngineServiceClient reCl,
            String appGuid) {
        if (reCl == null) {
            return null;
        }
        try {
            KEAppDescriptor ret = reCl.getKEAppDescriptor(
                    new GetKEAppDescriptorParams().withAppGuid(appGuid));
            //System.out.println("RelationEngine.getKEAppDescriptor(" + appGuid + "): " + ret);
            return ret;
        } catch (Exception e) {
            System.out.println("Error getting app descriptor: " + e.getMessage());
            return null;
        }
    }
    
    private static Integer asInteger(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof Integer) {
            return (Integer)input;
        }
        if (input instanceof Long) {
            return (int)(long)(Long)input;
        }
        if (input instanceof String) {
            return Integer.parseInt((String)input);
        }
        throw new IllegalStateException("Can not convert " + input + " of type " + 
                input.getClass().getSimpleName() + " to Integer");
    }
    
    private static boolean isJobDone(AppJob job) {
        return job.getState().equals(MongoStorage.JOB_STATE_FINISHED) ||
                job.getState().equals(MongoStorage.JOB_STATE_ERROR);
    }
    
    @Override
    public String getConnectorState(GetConnectorStateParams params,
            AuthToken authPart) {
        //TODO: check that user from authPart can read object
        try {
            String objRef = wsAdminHelper.getObjectInfo(params.getObjRef()).getResolvedRef();
            ConnJob ret = store.getLastConnJobForObjRef(objRef);
            return ret == null ? null : ret.getState();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    @Override
    public void cleanAppData(CleanAppDataParams params, AuthToken authPart) {
        checkAdmin(authPart);
        try {
            KBaseRelationEngineServiceClient reCl = new KBaseRelationEngineServiceClient(
                    srvWizUrl, keAdminToken);
            reCl.setServiceVersion("dev");
            reCl.setIsInsecureHttpConnectionAllowed(true);
            reCl.cleanKEAppResults(new CleanKEAppResultsParams().withAppGuid(params.getApp()));
            store.deleteAppJobs(params.getApp());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
