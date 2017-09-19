package kbaseknowledgeengine.test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import kbaseknowledgeengine.WSEventProcessor;
import kbaseknowledgeengine.cfg.ExecConfigLoader;
import kbaseknowledgeengine.db.MongoStorage;
import kbaseknowledgeengine.db.WSEvent;
import us.kbase.common.test.controllers.mongo.MongoController;

public class WSEventProcessorTest {
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
        WSEvent evt1 = new WSEvent();
        evt1.eventType = WSEventProcessor.EVENT_TYPE_NEW_OBJECT_VER;
        evt1.accessGroupId = 3;
        evt1.accessGroupObjectId = "2";
        evt1.version = 1;
        evt1.storageCode = "WS";
        evt1.storageObjectType = "KBaseGenomes.Genome";
        evt1.storageObjectTypeVersion = 12;
        evt1.timestamp = System.currentTimeMillis();
        evt1.isGlobalAccessed = false;
        evt1.processed = false;
        evt1.indexed = false;
        store.insertEvent(evt1);
        List<WSEvent> events = new ArrayList<>();
        WSEventProcessor processor = new WSEventProcessor(store, 
                new WSEventProcessor.WSEventListener() {
            @Override
            public void objectVersionCreated(WSEvent evt) {
                evt.processed = true;
                store.updateEvent(evt);
                events.add(evt);
            }
        }, ExecConfigLoader.getInstance());
        for (int i = 0; events.isEmpty() && i < 10; i++) {
            Thread.sleep(1000);
        }
        Assert.assertEquals(1, events.size());
        Assert.assertTrue(events.get(0).processed);
        System.out.println(events.get(0));
        processor.stopWatcher();
        Assert.assertEquals(0, store.loadUnprocessedEvents().size());
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
