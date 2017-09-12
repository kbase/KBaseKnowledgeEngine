package kbaseknowledgeengine.db;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SysProp {
    @JsonProperty("prop")
    private String prop;
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("prop")
    public String getProp() {
        return prop;
    }
    
    @JsonProperty("prop")
    public void setProp(String prop) {
        this.prop = prop;
    }
    
    @JsonProperty("value")
    public String getValue() {
        return value;
    }
    
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }
}
