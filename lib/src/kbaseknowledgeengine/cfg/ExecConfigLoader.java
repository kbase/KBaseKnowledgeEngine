package kbaseknowledgeengine.cfg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExecConfigLoader implements IExecConfigLoader {
    private List<AppConfig> appConfigs = null;
    private List<ConnectorConfig> connConfigs = null;
    
    public static final String APP_CONFIG_PATH = "config/apps";
    public static final String CONN_CONFIG_PATH = "config/connectors";
    public static final String KB_DEP_CFG = "KB_DEPLOYMENT_CONFIG";
    
    private static volatile ExecConfigLoader instance = null;
    
    private ExecConfigLoader() throws IOException {
        appConfigs = loadConfigs(APP_CONFIG_PATH, AppConfig.class);
        connConfigs = loadConfigs(CONN_CONFIG_PATH, ConnectorConfig.class);
    }
    
    public List<AppConfig> loadAppConfigs() throws IOException {
        return appConfigs;
    }

    public List<ConnectorConfig> loadConnectorConfigs() throws IOException {
        return connConfigs;
    }

    public static IExecConfigLoader getInstance() throws IOException {
        if (instance == null) {
            instance = new ExecConfigLoader();
        }
        return instance;
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
