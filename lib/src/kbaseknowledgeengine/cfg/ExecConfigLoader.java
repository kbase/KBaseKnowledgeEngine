package kbaseknowledgeengine.cfg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExecConfigLoader {
    private static List<AppConfig> appConfigs = null;
    private static List<ConnectorConfig> connConfigs = null;
    
    public static final String APP_CONFIG_PATH = "config/apps";
    public static final String CONN_CONFIG_PATH = "config/connectors";
    public static final String KB_DEP_CFG = "KB_DEPLOYMENT_CONFIG";
    
    public static List<AppConfig> loadAppConfigs() throws IOException {
        if (appConfigs == null) {
            appConfigs = loadConfigs(APP_CONFIG_PATH, AppConfig.class);
        }
        return appConfigs;
    }

    public static List<ConnectorConfig> loadConnectorConfigs() throws IOException {
        if (connConfigs == null) {
            connConfigs = loadConfigs(CONN_CONFIG_PATH, ConnectorConfig.class);
        }
        return connConfigs;
    }

    private static <T> List<T> loadConfigs(String configPath, 
            Class<T> target) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<T> cfgs = new ArrayList<>();
        File dir = new File(configPath);
        if (!dir.exists()) {
            String file = System.getProperty(KB_DEP_CFG) == null ?
                    System.getenv(KB_DEP_CFG) : System.getProperty(KB_DEP_CFG);
                    File deploymentDir = new File(file).getParentFile();
                    dir = new File(deploymentDir, configPath);
        }
        for (File jsonFile : dir.listFiles()) {
            if (!jsonFile.getName().endsWith(".json")) {
                continue;
            }
            T cfg = mapper.readValue(jsonFile, target);
            cfgs.add(cfg);
        }
        return cfgs;
    }
}
