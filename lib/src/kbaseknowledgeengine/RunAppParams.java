
package kbaseknowledgeengine;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: RunAppParams</p>
 * <pre>
 * app - name of registered KB-SDK module configured to be compatible with KE.
 * ref_mode - flag for public reference data processing (accessible only for admins).
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "app",
    "ref_mode"
})
public class RunAppParams {

    @JsonProperty("app")
    private String app;
    @JsonProperty("ref_mode")
    private Long refMode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("app")
    public String getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
    }

    public RunAppParams withApp(String app) {
        this.app = app;
        return this;
    }

    @JsonProperty("ref_mode")
    public Long getRefMode() {
        return refMode;
    }

    @JsonProperty("ref_mode")
    public void setRefMode(Long refMode) {
        this.refMode = refMode;
    }

    public RunAppParams withRefMode(Long refMode) {
        this.refMode = refMode;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((((("RunAppParams"+" [app=")+ app)+", refMode=")+ refMode)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
