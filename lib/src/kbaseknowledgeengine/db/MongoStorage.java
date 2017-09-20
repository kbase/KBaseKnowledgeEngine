package kbaseknowledgeengine.db;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

import us.kbase.common.mongo.exceptions.InvalidHostException;
import us.kbase.common.mongo.exceptions.MongoAuthException;

public class MongoStorage {

    private DB mongo;
    private Jongo jongo;
    private MongoCollection sysProps;
    private MongoCollection appJobs;
    private MongoCollection connJobs;
    private MongoCollection wsEvents;
    
    private static final Map<String, MongoClient> HOSTS_TO_CLIENT = new HashMap<>();

    public static final String COL_SYS_PROPS = "sys_props";
    public static final String PK_SYS_PROPS = "prop";
    public static final String COL_APP_JOBS = "app_jobs";
    public static final String PK_APP_JOBS = "job_id";
    public static final String COL_CONN_JOBS = "conn_jobs";
    public static final String PK_CONN_JOBS = "job_id";
    public static final String COL_EVENTS = "KEObjectEvents";
    
    public static final String JOB_STATE_QUEUED = "queued";
    public static final String JOB_STATE_STARTED = "started";
    public static final String JOB_STATE_FINISHED = "finished";
    public static final String JOB_STATE_ERROR = "error";
    
    public static final int DB_VERSION = 1;
    public static final String PROP_DB_VERSION = "db_version";
    
    public MongoStorage(String hosts, String db, String user, String pwd,
            Integer mongoReconnectRetry) throws MongoStorageException {
        try {
            mongo = getDB(hosts, db, user, pwd, mongoReconnectRetry == null ? 0 : 
                mongoReconnectRetry, 10);
            jongo = new Jongo(mongo);
            sysProps = jongo.getCollection(COL_SYS_PROPS);
            sysProps.ensureIndex(String.format("{%s:1}", PK_SYS_PROPS), "{unique:true}");
            appJobs = jongo.getCollection(COL_APP_JOBS);
            appJobs.ensureIndex(String.format("{%s:1}", PK_APP_JOBS), "{unique:true}");
            connJobs = jongo.getCollection(COL_CONN_JOBS);
            connJobs.ensureIndex(String.format("{%s:1}", PK_CONN_JOBS), "{unique:true}");
            wsEvents = jongo.getCollection(COL_EVENTS);
            wsEvents.ensureIndex(String.format("{%s:1,%s:1,%s:1,%s:1,%s:1}", "accessGroupId",
                    "accessGroupObjectId", "version", "timestamp", "eventType"), "{unique:true}");
        } catch (Exception e) {
            throw new MongoStorageException(e);
        }
        try {
            if (checkDbVersion() < DB_VERSION) {
                setSysProp(PROP_DB_VERSION, String.valueOf(DB_VERSION));
            }
        } catch (MongoStorageException e) {
            throw e;
        } catch (Exception ex) {
            throw new MongoStorageException(ex);
        }
    }
    
    public int checkDbVersion() throws MongoStorageException {
        String storedDbVerText = getSysProp(PROP_DB_VERSION);
        Integer storedDbVer = storedDbVerText == null ? null : Integer.parseInt(storedDbVerText);
        if (storedDbVer != null && storedDbVer > DB_VERSION) {
            throw new MongoStorageException("Mongo database containes future version: " + storedDbVer);
        }
        return storedDbVer == null ? 0 : storedDbVer;
    }
    
    public String getSysProp(String prop) {
        SysProp ret = sysProps.findOne(String.format("{%s:#}", PK_SYS_PROPS), prop).as(SysProp.class);
        return ret == null ? null : ret.getValue();
    }

    public void setSysProp(String prop, String value) {
        SysProp sp = new SysProp();
        sp.setProp(prop);
        sp.setValue(value);
        appJobs.update(String.format("{%s:#}", PK_SYS_PROPS), prop).upsert().with(sp);
    }

    public void deleteAllAppJobs() {
        appJobs.remove();
    }
    
    public void insertUpdateAppJob(AppJob job) {
        appJobs.update(String.format("{%s:#}", "job_id"), job.getJobId()).upsert().with(job);
    }
    
    public List<AppJob> loadAllAppJobs() {
        return asList(appJobs.find().as(AppJob.class));
    }

    public List<AppJob> loadAppJobs(String app) {
        return asList(appJobs.find(String.format("{%s:#}", "app"), app).as(AppJob.class));
    }

    public AppJob loadAppJob(String jobId) {
        return appJobs.findOne(String.format("{%s:#}", "job_id"), jobId).as(AppJob.class);
    }

    public Map<String, AppJob> getLastJobsPerApp() {
        List<AppJob> list = loadAllAppJobs();
        Map<String, AppJob> ret = new HashMap<>();
        for (AppJob job : list) {
            AppJob prev = ret.get(job.getApp());
            if (prev == null || prev.queuedEpochMs < job.queuedEpochMs ||
                    job.getState().equals("queued") || job.getState().equals("started")) {
                ret.put(job.getApp(), job);
            }
        }
        return ret;
    }

    public AppJob getLastJobForApp(String app) {
        List<AppJob> list = loadAppJobs(app);
        AppJob ret = null;
        for (AppJob job : list) {
            if (ret == null || ret.queuedEpochMs < job.queuedEpochMs ||
                    job.getState().equals("queued") || job.getState().equals("started")) {
                ret = job;
            }
        }
        return ret;
    }

    public List<ConnJob> loadAllConnJobs() {
        return asList(connJobs.find().as(ConnJob.class));
    }

    public void insertUpdateConnJob(ConnJob job) {
        connJobs.update(String.format("{%s:#}", "job_id"), job.getJobId()).upsert().with(job);
    }

    public void deleteAllConnJobs() {
        connJobs.remove();
    }

    public ConnJob getLastConnJobForObjRef(String objRef) {
        List<ConnJob> list = asList(connJobs.find(String.format("{%s:#}", "obj_ref"), 
                objRef).as(ConnJob.class));
        ConnJob ret = null;
        for (ConnJob job : list) {
            if (ret == null || ret.queuedEpochMs < job.queuedEpochMs) {
                ret = job;
            }
        }
        return ret;
    }

    public List<WSEvent> loadUnprocessedEvents() {
        return asList(wsEvents.find(String.format("{%s:#}", "processed"), false).as(WSEvent.class));
    }
    
    public void updateEvent(WSEvent evt) {
        wsEvents.update(String.format("{%s:#,%s:#,%s:#,%s:#,%s:#}", "accessGroupId",
                    "accessGroupObjectId", "version", "timestamp", "eventType"), evt.accessGroupId,
                evt.accessGroupObjectId, evt.version, evt.timestamp, evt.eventType).with(evt);
    }
    
    public WSEvent loadEvent(int accessGroupId, String accessGroupObjectId, int version,
            long timestamp, String eventType) {
        return wsEvents.findOne(String.format("{%s:#,%s:#,%s:#,%s:#,%s:#}", "accessGroupId",
                "accessGroupObjectId", "version", "timestamp", "eventType"), accessGroupId,
            accessGroupObjectId, version, timestamp, eventType).as(WSEvent.class);
    }

    public void insertEvent(WSEvent evt) {
        wsEvents.insert(evt);
    }

    private static <T> List<T> asList(Iterable<T> iter) {
        List<T> ret = new ArrayList<T>();
        for (T item : iter) {
            ret.add(item);
        }
        return ret;
    }
    
    private synchronized static MongoClient getMongoClient(final String hosts)
            throws UnknownHostException, InvalidHostException {
        //Only make one instance of MongoClient per JVM per mongo docs
        final MongoClient client;
        if (!HOSTS_TO_CLIENT.containsKey(hosts)) {
            // Don't print to stderr
            java.util.logging.Logger.getLogger("com.mongodb").setLevel(Level.OFF);
            @SuppressWarnings("deprecation")
            final MongoClientOptions opts = MongoClientOptions.builder().autoConnectRetry(true)
                .build();
            try {
                List<ServerAddress> addr = new ArrayList<ServerAddress>();
                for (String s: hosts.split(","))
                    addr.add(new ServerAddress(s));
                client = new MongoClient(addr, opts);
            } catch (NumberFormatException nfe) {
                //throw a better exception if 10gen ever fixes this
                throw new InvalidHostException(hosts
                        + " is not a valid mongodb host");
            }
            HOSTS_TO_CLIENT.put(hosts, client);
        } else {
            client = HOSTS_TO_CLIENT.get(hosts);
        }
        return client;
    }
    
    @SuppressWarnings("deprecation")
    private static DB getDB(final String hosts, final String database,
            final String user, final String pwd,
            final int retryCount, final int logIntervalCount)
                    throws UnknownHostException, InvalidHostException, IOException,
                    MongoAuthException, InterruptedException {
        if (database == null || database.isEmpty()) {
            throw new IllegalArgumentException(
                    "database may not be null or the empty string");
        }
        final DB db = getMongoClient(hosts).getDB(database);
        if (user != null && pwd != null) {
            int retries = 0;
            while (true) {
                try {
                    db.authenticate(user, pwd.toCharArray());
                    break;
                } catch (MongoException.Network men) {
                    if (retries >= retryCount) {
                        throw (IOException) men.getCause();
                    }
                    Thread.sleep(1000);
                }
                retries++;
            }
        }
        try {
            db.getCollectionNames();
        } catch (MongoException me) {
            throw new MongoAuthException("Not authorized for database "
                    + database, me);
        }
        return db;
    }

}
