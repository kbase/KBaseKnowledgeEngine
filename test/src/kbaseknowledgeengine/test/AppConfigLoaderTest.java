package kbaseknowledgeengine.test;

import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;
import kbaseknowledgeengine.cfg.AppConfig;
import kbaseknowledgeengine.cfg.AppConfigLoader;

public class AppConfigLoaderTest {

    @Test
    public void testMain() throws Exception {
        Map<String, AppConfig> cfgs = AppConfigLoader.loadAppConfigs();
        Assert.assertTrue(cfgs.size() > 0);
        Assert.assertNotNull(cfgs.values().iterator().next().getModuleMethod());
    }
}
