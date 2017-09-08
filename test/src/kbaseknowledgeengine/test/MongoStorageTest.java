package kbaseknowledgeengine.test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import kbaseknowledgeengine.db.AppJob;
import kbaseknowledgeengine.db.MongoStorage;
import us.kbase.common.test.controllers.mongo.MongoController;

public class MongoStorageTest {
    private static MongoController mongo = null;
    private static File tempDir = null;
    private static MongoStorage store = null;

    @BeforeClass
    public static void prepare() throws Exception {
        tempDir = Paths.get(TestCommon.getTempDir()).resolve("MongoStorageTest").toFile();
        FileUtils.deleteQuietly(tempDir);
        tempDir.mkdirs();
        mongo = new MongoController(TestCommon.getMongoExe(), tempDir.toPath());
        store = new MongoStorage("localhost:" + mongo.getServerPort(), 
                "test_" + System.currentTimeMillis(), null, null, null);
    }
    
    @Test
    public void testMain() throws Exception {
        Assert.assertEquals(0, store.getLastJobsPerApp().size());
        // app1
        AppJob job = new AppJob();
        job.setApp("app1");
        job.setJobId("123");
        job.setQueuedEpochMs(1L);
        job.setState(MongoStorage.JOB_STATE_QUEUED);
        store.insertUpdateAppJob(job);
        Assert.assertEquals(1, store.getLastJobsPerApp().size());
        Assert.assertEquals(job.getApp(), store.loadAppJob(job.getJobId()).getApp());
        job.setStartedEpochMs(2L);
        job.setFinishedEpochMs(3L);
        job.setState(MongoStorage.JOB_STATE_FINISHED);
        store.insertUpdateAppJob(job);
        Assert.assertEquals(job.getApp(), store.loadAppJob(job.getJobId()).getApp());
        Assert.assertEquals(1, store.loadAllAppJobs().size());
        Assert.assertEquals(job.getState(), store.loadAppJob(job.getJobId()).getState());
        // next job of app1
        AppJob job2 = new AppJob();
        job2.setApp("app1");
        job2.setJobId("124");
        job2.setQueuedEpochMs(4L);
        job2.setState(MongoStorage.JOB_STATE_QUEUED);
        store.insertUpdateAppJob(job2);
        Assert.assertEquals(2, store.loadAllAppJobs().size());
        Map<String, AppJob> lastJobs = store.getLastJobsPerApp();
        Assert.assertEquals(1, lastJobs.size());
        Assert.assertEquals(job2.getJobId(), lastJobs.get(job2.getApp()).getJobId());
        Assert.assertEquals(job2.getJobId(), store.getLastJobForApp(job2.getApp()).getJobId());
        // app2
        AppJob job3 = new AppJob();
        job3.setApp("app2");
        job3.setJobId("125");
        job3.setQueuedEpochMs(5L);
        job3.setState(MongoStorage.JOB_STATE_QUEUED);
        store.insertUpdateAppJob(job3);
        Assert.assertEquals(2, store.getLastJobsPerApp().size());
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        final boolean deleteTempFiles = TestCommon.getDeleteTempFiles();
        if (mongo != null) {
            mongo.destroy(deleteTempFiles);
        }
        if (tempDir != null && tempDir.exists() && deleteTempFiles) {
            FileUtils.deleteQuietly(tempDir);
        }
    }
}
