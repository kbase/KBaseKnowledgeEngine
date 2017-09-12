package kbaseknowledgeengine.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthToken;
import us.kbase.auth.ConfigurableAuthService;
import us.kbase.common.test.TestException;

public class TestCommon {
    
    public static final String TYPES_REPO_DIR = "resources/types";
    
    public static final String MONGOEXE = "test.mongo.exe";
    public static final String MONGOEXE_DEFAULT = "/opt/mongo/bin/mongod";
    
    public static final String JARS_PATH = "test.jars.dir";
    public static final String JARS_PATH_DEFAULT = "/kb/deployment/lib/jars";
    public static final String WS_VER = "test.workspace.ver";
    public static final String WS_VER_DEFAULT = "0.7.2-dev1";
    
    public static final String AUTHSERV = "auth_service_url";
    public static final String TEST_TOKEN = "test_token";
    
    public static final String TEST_TEMP_DIR = "test.temp.dir";
    public static final String TEST_TEMP_DIR_DEFAULT = "/kb/module/work/tmp/testtmp";
    public static final String KEEP_TEMP_DIR = "test.temp.dir.keep";
    public static final String KEEP_TEMP_DIR_DEFAULT = "false";
    
    public static final String TEST_CONFIG_FILE_PROP_NAME = "test.cfg";
    public static final Path TEST_CONFIG_FILE_DEFAULT_PATH =
            Paths.get("/kb/module/work/test.cfg");
    
    private static Map<String, String> testConfig = null;
    private static Path testConfigFilePath = null;

    public static String getTestProperty(final String propertyKey) {
        return getTestProperty(propertyKey, null);
    }
    
    public static String getTestProperty(final String propertyKey, final String default_) {
        getTestConfig();
        final String prop = testConfig.get(propertyKey);
        if (prop == null || prop.trim().isEmpty()) {
            if (default_ != null) {
                System.out.println(String.format(
                        "Property %s of test file %s is missing, using default %s",
                        propertyKey, getConfigFilePath(), default_));
                return default_;
            }
            throw new TestException(String.format("Property %s of test file %s is missing",
                    propertyKey, getConfigFilePath()));
        }
        return prop;
    }

    private static void getTestConfig() {
        if (testConfig != null) {
            return;
        }
        final Path testCfgFilePath = getConfigFilePath();
        Properties p = new Properties();
        try {
            p.load(Files.newInputStream(testCfgFilePath));
        } catch (IOException ioe) {
            throw new TestException(String.format(
                    "IO Error reading the test configuration file %s: %s",
                    testCfgFilePath, ioe.getMessage()), ioe);
        }
        testConfig = new HashMap<>();
        for (final String s: p.stringPropertyNames()) {
            testConfig.put(s, p.getProperty(s));
        }
    }

    public static Path getConfigFilePath() {
        if (testConfigFilePath != null) {
            return testConfigFilePath;
        }
        String testCfgFilePathStr = System.getProperty(TEST_CONFIG_FILE_PROP_NAME);
        if (testCfgFilePathStr == null || testCfgFilePathStr.trim().isEmpty()) {
            System.out.println(String.format(
                    "No test config file specified in system property %s, using default of %s",
                    TEST_CONFIG_FILE_PROP_NAME, TEST_CONFIG_FILE_DEFAULT_PATH));
            testConfigFilePath = TEST_CONFIG_FILE_DEFAULT_PATH;
        } else {
            testConfigFilePath = Paths.get(testCfgFilePathStr).toAbsolutePath().normalize();
        }
        return testConfigFilePath;
    }
    
    /*public static void destroyDB(MongoDatabase db) {
        for (String name: db.listCollectionNames()) {
            if (!name.startsWith("system.")) {
                // dropping collection also drops indexes
                db.getCollection(name).deleteMany(new Document());
            }
        }
    }*/
    
    public static String getMongoExe() {
        return getTestProperty(MONGOEXE, MONGOEXE_DEFAULT);
    }
    
    public static String getTempDir() {
        return getTestProperty(TEST_TEMP_DIR, TEST_TEMP_DIR_DEFAULT);
    }
    
    public static boolean getDeleteTempFiles() {
        return !"true".equals(getTestProperty(KEEP_TEMP_DIR, KEEP_TEMP_DIR_DEFAULT));
    }

    public static Path getJarsDir() {
        return Paths.get(getTestProperty(JARS_PATH, JARS_PATH_DEFAULT));
    }
    
    public static String getWorkspaceVersion() {
        return getTestProperty(WS_VER, WS_VER_DEFAULT);
    }
    
    public static URL getAuthUrl() {
        return getURL(AUTHSERV, null);
    }
    
    private static URL getURL(final String prop, final String default_) {
        try {
            return new URL(getTestProperty(prop, default_));
        } catch (MalformedURLException e) {
            throw new TestException("Property " + prop + " is not a valid url", e);
        }
    }
    
    public static String getToken() {
        return getTestProperty(TEST_TOKEN);
    }
    
    public static AuthToken getToken(
            final ConfigurableAuthService auth) {
        try {
            return auth.validateToken(getToken());
        } catch (AuthException | IOException e) {
            throw new TestException(String.format(
                    "Couldn't log in user with token : %s", e.getMessage()), e);
        }
    }
    
    public static void assertExceptionCorrect(
            final Exception got,
            final Exception expected) {
        final StringWriter sw = new StringWriter();
        got.printStackTrace(new PrintWriter(sw));
        assertThat("incorrect exception. trace:\n" +
                sw.toString(),
                got.getLocalizedMessage(),
                is(expected.getLocalizedMessage()));
        assertThat("incorrect exception type", got, instanceOf(expected.getClass()));
    }
    
    @SafeVarargs
    public static <T> Set<T> set(T... objects) {
        return new HashSet<T>(Arrays.asList(objects));
    }
}