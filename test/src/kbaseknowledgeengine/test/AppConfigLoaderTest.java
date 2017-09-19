package kbaseknowledgeengine.test;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import kbaseknowledgeengine.cfg.AppConfig;
import kbaseknowledgeengine.cfg.ExecConfigLoader;

public class AppConfigLoaderTest {

    @Test
    public void testMain() throws Exception {
        List<AppConfig> cfgs = ExecConfigLoader.getInstance().loadAppConfigs();
        Assert.assertTrue(cfgs.size() > 0);
        Assert.assertNotNull(cfgs.get(0).getModuleMethod());
    }
}
