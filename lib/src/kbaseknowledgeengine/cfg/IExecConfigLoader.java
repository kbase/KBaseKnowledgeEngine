package kbaseknowledgeengine.cfg;

import java.io.IOException;
import java.util.List;

public interface IExecConfigLoader {

    public List<AppConfig> loadAppConfigs() throws IOException;
    
    public List<ConnectorConfig> loadConnectorConfigs() throws IOException;

}
