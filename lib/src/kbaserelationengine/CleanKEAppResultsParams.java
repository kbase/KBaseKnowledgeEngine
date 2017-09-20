
package kbaserelationengine;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: CleanKEAppResultsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "app_guid"
})
public class CleanKEAppResultsParams {

    @JsonProperty("app_guid")
    private String appGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("app_guid")
    public String getAppGuid() {
        return appGuid;
    }

    @JsonProperty("app_guid")
    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public CleanKEAppResultsParams withAppGuid(String appGuid) {
        this.appGuid = appGuid;
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
        return ((((("CleanKEAppResultsParams"+" [appGuid=")+ appGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
