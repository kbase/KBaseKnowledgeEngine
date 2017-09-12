package kbaseknowledgeengine.test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import kbaseknowledgeengine.AppStatus;
import kbaseknowledgeengine.DBKBaseKnowledgeEngine;
import kbaseknowledgeengine.RunAppParams;
import kbaseknowledgeengine.db.MongoStorage;
import us.kbase.auth.AuthConfig;
import us.kbase.auth.AuthToken;
import us.kbase.auth.ConfigurableAuthService;
import us.kbase.common.test.controllers.mongo.MongoController;

public class DBKBaseKnowledgeEngineTest {
    private static MongoController mongo = null;
    private static File tempDir = null;
    private static DBKBaseKnowledgeEngine engine = null;
    private static AuthToken token = null;

    @BeforeClass
    public static void prepare() throws Exception {
        tempDir = Paths.get(TestCommon.getTempDir()).resolve("MongoStorageTest").toFile();
        FileUtils.deleteQuietly(tempDir);
        tempDir.mkdirs();
        mongo = new MongoController(TestCommon.getMongoExe(), tempDir.toPath());
        String serviceName = "KBaseKnowledgeEngine";
        Map<String, String> config = getConfig(serviceName);
        URL eeUrl = new URL(config.get("njsw-url"));
        String authUrl = config.get("auth-service-url");
        String authUrlInsecure = config.get("auth-service-url-allow-insecure");
        ConfigurableAuthService authService = new ConfigurableAuthService(
                new AuthConfig().withKBaseAuthServerURL(new URL(authUrl))
                .withAllowInsecureURLs("true".equals(authUrlInsecure)));
        token = TestCommon.getToken(authService);
        engine = new DBKBaseKnowledgeEngine("localhost:" + mongo.getServerPort(), 
                "test_" + System.currentTimeMillis(), null, null, eeUrl, token.getUserName(),
                config);
    }
    
    private static Map<String, String> getConfig(final String serviceName) throws Exception {
        String KB_DEP = "KB_DEPLOYMENT_CONFIG";
        final String file = System.getProperty(KB_DEP) == null ?
                System.getenv(KB_DEP) : System.getProperty(KB_DEP);
        final File deploy = new File(file);
        final Ini ini = new Ini(deploy);
        return ini.get(serviceName);
    }
    
    @Test
    public void testMain() throws Exception {
        Set<String> appNames = engine.getAppsStatus(token, null).stream()
                .map(item -> item.getApp()).collect(Collectors.toSet());
        String app = "A0";
        Assert.assertTrue(appNames.contains(app));
        engine.runApp(new RunAppParams().withApp(app), token, null).getJobId();
        boolean ok = false;
        AppStatus appSt = null;
        for (int i = 0; i < 30; i++) {
            Thread.sleep(5000);
            appSt = findAppStatus(app);
            if (appSt.getState().equals(MongoStorage.JOB_STATE_ERROR)) {
                throw new IllegalStateException("Unexpected error in App: " + appSt.getOutput());
            }
            if (appSt.getState().equals(MongoStorage.JOB_STATE_FINISHED)) {
                ok = true;
                break;
            }
        }
        Assert.assertTrue(ok);
        Assert.assertNotNull(appSt.getQueuedEpochMs());
        Assert.assertNotNull(appSt.getStartedEpochMs());
        Assert.assertNotNull(appSt.getFinishedEpochMs());
    }
    
    private static AppStatus findAppStatus(String app) {
        for (AppStatus ret : engine.getAppsStatus(token, null)) {
            if (ret.getApp().equals(app)) {
                return ret;
            }
        }
        return null;
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
