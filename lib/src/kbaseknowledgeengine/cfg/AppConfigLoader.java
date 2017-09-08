package kbaseknowledgeengine.cfg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AppConfigLoader {
    private static Map<String, AppConfig> appConfigs = null;
    
    public static Map<String, AppConfig> loadAppConfigs() throws IOException {
        if (appConfigs == null) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, AppConfig> cfgs = new HashMap<>();
            File dir = new File("config/apps");
            for (File jsonFile : dir.listFiles()) {
                if (!jsonFile.getName().endsWith(".json")) {
                    continue;
                }
                AppConfig cfg = mapper.readValue(jsonFile, AppConfig.class);
                cfgs.put(cfg.getApp(), cfg);
            }
            appConfigs = cfgs;
        }
        return appConfigs;
    }
}
