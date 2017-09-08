package kbaseknowledgeengine.cfg;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig {
    @JsonProperty("app")
    private String app;
    @JsonProperty("module_method")
    private String moduleMethod;
    @JsonProperty("version_tag")
    private String versionTag;
    @JsonProperty("title")
    private String title;
    @JsonProperty("starting_from_date")
    private String startingFromDate;
    @JsonProperty("regularity")
    private String regularity;

    @JsonProperty("app")
    public String getApp() {
        return app;
    }
    
    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
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
    
    @JsonProperty("starting_from_date")
    public String getStartingFromDate() {
        return startingFromDate;
    }
    
    @JsonProperty("starting_from_date")
    public void setStartingFromDate(String startingFromDate) {
        this.startingFromDate = startingFromDate;
    }
    
    @JsonProperty("regularity")
    public String getRegularity() {
        return regularity;
    }
    
    @JsonProperty("regularity")
    public void setRegularity(String regularity) {
        this.regularity = regularity;
    }
}
