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
    private MongoCollection appJobs;
    private MongoCollection appScheds;
    
    private static final Map<String, MongoClient> HOSTS_TO_CLIENT = new HashMap<>();

    public static final String COL_APP_JOBS = "app_jobs";
    public static final String PK_APP_JOBS = "job_id";
    public static final String COL_APP_SCHEDS = "app_scheds";
    public static final String PK_APP_SCHEDS = "app";
    
    public static final String JOB_STATE_QUEUED = "queued";
    public static final String JOB_STATE_STARTED = "started";
    public static final String JOB_STATE_FINISHED = "finished";
    public static final String JOB_STATE_ERROR = "error";
    
    public MongoStorage(String hosts, String db, String user, String pwd,
            Integer mongoReconnectRetry) throws MongoStorageException {
        try {
            mongo = getDB(hosts, db, user, pwd, mongoReconnectRetry == null ? 0 : 
                mongoReconnectRetry, 10);
            jongo = new Jongo(mongo);
            appJobs = jongo.getCollection(COL_APP_JOBS);
            appJobs.ensureIndex(String.format("{%s:1}", PK_APP_JOBS), "{unique:true}");
            appScheds = jongo.getCollection(COL_APP_SCHEDS);
            appScheds.ensureIndex(String.format("{%s:1}", PK_APP_SCHEDS), "{unique:true}");
        } catch (Exception e) {
            throw new MongoStorageException(e);
        }
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
