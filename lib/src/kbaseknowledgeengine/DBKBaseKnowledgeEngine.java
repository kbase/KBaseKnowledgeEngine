package kbaseknowledgeengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kbaseknowledgeengine.cfg.AppConfig;
import kbaseknowledgeengine.cfg.AppConfigLoader;
import kbaseknowledgeengine.db.AppJob;
import kbaseknowledgeengine.db.MongoStorage;
import kbaseknowledgeengine.db.MongoStorageException;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.RpcContext;

public class DBKBaseKnowledgeEngine implements IKBaseKnowledgeEngine {
    private MongoStorage store = null;
    private Map<String, AppConfig> appConfigs = null;
    
    public DBKBaseKnowledgeEngine(String mongoHosts, String mongoDb, String mongoUser,
            String mongoPassword) throws MongoStorageException, IOException {
        store = new MongoStorage(mongoHosts, mongoDb, mongoUser, mongoPassword, null);
        appConfigs = AppConfigLoader.loadAppConfigs();
    }
	
    @Override
    public List<AppStatus> getAppsStatus(AuthToken authPart,
            RpcContext jsonRpcContext) {
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
        throw new IllegalStateException("Method is not supported yet");
    }
    
    @Override
    public RunAppOutput runApp(RunAppParams params, AuthToken authPart,
            RpcContext jsonRpcContext) {
        throw new IllegalStateException("Method is not supported yet");
    }
    
    @Override
    public void testInit(AuthToken authPart, RpcContext jsonRpcContext) {
        throw new IllegalStateException("Method is not supported yet");
    }
	
}
