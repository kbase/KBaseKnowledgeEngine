package kbaseknowledgeengine;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import kbaseknowledgeengine.cfg.AppConfig;
import kbaseknowledgeengine.cfg.ExecConfigLoader;
import kbaseknowledgeengine.db.AppJob;
import kbaseknowledgeengine.db.MongoStorage;
import kbaseknowledgeengine.db.MongoStorageException;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UObject;
import us.kbase.common.test.controllers.mongo.MongoController;
import us.kbase.narrativejobservice.JobState;
import us.kbase.narrativejobservice.NarrativeJobServiceClient;
import us.kbase.narrativejobservice.RunJobParams;

public class DBKBaseKnowledgeEngine implements IKBaseKnowledgeEngine {
    private final MongoStorage store;
    private final Map<String, AppConfig> appConfigs;
    private final URL executionEngineUrl;
    private final Set<String> admins;
    private final Map<String, String> reConfig;
    private final Set<Thread> monitors = Collections.synchronizedSet(new HashSet<>());
    
    public static final String MONGOEXE_DEFAULT = "/opt/mongo/bin/mongod";
    
    public DBKBaseKnowledgeEngine(String mongoHosts, String mongoDb, String mongoUser,
            String mongoPassword, URL executionEngineUrl, String admins,
            Map<String, String> reConfig) throws MongoStorageException, IOException {
        if (mongoHosts == null || mongoHosts.trim().length() == 0) {
            // Startup internal Mongo
            /*File tempDir = new File(reConfig.get("scratch"), "MongoStorage");
            FileUtils.deleteQuietly(tempDir);
            tempDir.mkdirs();
            try {
                MongoController mongo = new MongoController(MONGOEXE_DEFAULT, tempDir.toPath());
                mongoHosts = "localhost:" + mongo.getServerPort();
                if (mongoDb == null || mongoDb.trim().length() == 0) {
                    mongoDb = "db_" + System.currentTimeMillis();
                }
            } catch (Exception e) {
                throw new MongoStorageException(e);
            }*/
            throw new MongoStorageException("Mongo host is not set in secure config parameters");
        }
        store = new MongoStorage(mongoHosts, mongoDb, mongoUser, mongoPassword, null);
        List<AppConfig> appCfgList = ExecConfigLoader.loadAppConfigs();
        appConfigs = appCfgList.stream().collect(Collectors.toMap(item -> item.getApp(), 
                Function.identity()));
        this.executionEngineUrl = executionEngineUrl;
        this.admins = new HashSet<>(Arrays.asList(admins.split(",")));
        this.reConfig = reConfig;
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
                st.withJobId(job.getJobId())
                .withState(job.getState())
                .withOutput(job.getMessage())
                .withUpdatedReNodes(asLong(job.getUpdatedReNodes()))
                .withNewReNodes(asLong(job.getNewReNodes()))
                .withNewReLinks(asLong(job.getNewReLinks()))
                .withQueuedEpochMs(job.getQueuedEpochMs())
                .withStartedEpochMs(job.getStartedEpochMs())
                .withFinishedEpochMs(job.getFinishedEpochMs());
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
        return Collections.emptyList();
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
            jobParams.put("re_url", reConfig.get("re-url"));
            jobParams.put("re_user", reConfig.get("re-user"));
            jobParams.put("re_password", reConfig.get("re-password"));
            String jobId = njs.runJob(new RunJobParams().withMethod(cfg.getModuleMethod())
                    .withServiceVer(cfg.getVersionTag()).withParams(Arrays.asList(
                            new UObject(jobParams))));
            job = new AppJob();
            job.setApp(app);
            job.setJobId(jobId);
            job.setQueuedEpochMs(System.currentTimeMillis());
            job.setState(MongoStorage.JOB_STATE_QUEUED);
            job.setUser(authPart.getUserName());
            store.insertUpdateAppJob(job);
            startBackgroundMonitor(njs, jobId);
            return new RunAppOutput().withJobId(jobId);
        } catch (Exception e) {
            throw new IllegalStateException("Error running app [" + app + "]: " + 
                    e.getMessage(), e);
        }
    }
    
    private Thread startBackgroundMonitor(final NarrativeJobServiceClient njs, 
            final String jobId) {
        Thread ret = new Thread(new Runnable() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public void run() {
                AppJob job = store.loadAppJob(jobId);
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
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
                            } else {
                                job.setState(MongoStorage.JOB_STATE_FINISHED);
                                List<Object> retArr = UObject.transformObjectToObject(js.getResult(), List.class);
                                Map<String, Object> retMap = (Map)retArr.get(0); 
                                job.setNewReNodes(asInteger((Long)retMap.get("new_re_nodes")));
                                job.setUpdatedReNodes(asInteger((Long)retMap.get("updated_re_nodes")));
                                job.setNewReLinks(asInteger((Long)retMap.get("new_re_links")));
                                job.setMessage((String)retMap.get("message"));
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
                                    job.setState(newState);
                                    job.setStartedEpochMs(js.getExecStartTime());
                                }
                            }
                        }
                    } catch (Exception e) {
                        job.setState(MongoStorage.JOB_STATE_ERROR);
                        job.setMessage("Error monitoring job: " + e.getMessage());
                        toUpdate = true;
                        toBreak = true;
                    }
                    if (toUpdate) {
                        store.insertUpdateAppJob(job);
                    }
                    if (toBreak) {
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
    
    private static Integer asInteger(Long input) {
        return input == null ? null : (int)(long)input;
    }
    
    private static boolean isJobDone(AppJob job) {
        return job.getState().equals(MongoStorage.JOB_STATE_FINISHED) ||
                job.getState().equals(MongoStorage.JOB_STATE_ERROR);
    }
    
    @Override
    public void testInit(AuthToken authPart, RpcContext jsonRpcContext) {
        checkAdmin(authPart);
        store.deleteAllAppJobs();
    }
	
}
