package kbaseknowledgeengine.cfg;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectorConfig {
    @JsonProperty("connector_app")
    private String connectorApp;
    @JsonProperty("module_method")
    private String moduleMethod;
    @JsonProperty("version_tag")
    private String versionTag;
    @JsonProperty("title")
    private String title;
    @JsonProperty("workspace_type")
    private String workspaceType;

    @JsonProperty("connector_app")
    public String getConnectorApp() {
        return connectorApp;
    }
    
    @JsonProperty("connector_app")
    public void setConnectorApp(String connectorApp) {
        this.connectorApp = connectorApp;
    }
    
    @JsonProperty("module_method")
    public String getModuleMethod() {
        return moduleMethod;
    }
    
    @JsonProperty("module_method")
    public void setModuleMethod(String moduleMethod) {
        this.moduleMethod = moduleMethod;
    }
    
    @JsonProperty("version_tag")
    public String getVersionTag() {
        return versionTag;
    }
    
    @JsonProperty("version_tag")
    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }
    
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
    
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }
    
    @JsonProperty("workspace_type")
    public String getWorkspaceType() {
        return workspaceType;
    }
    
    @JsonProperty("workspace_type")
    public void setWorkspaceType(String workspaceType) {
        this.workspaceType = workspaceType;
    }
}
