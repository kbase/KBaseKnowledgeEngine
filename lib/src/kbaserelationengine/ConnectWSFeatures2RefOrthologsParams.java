
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
 * <p>Original spec-file type: ConnectWSFeatures2RefOrthologsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws2ref_feature_guids"
})
public class ConnectWSFeatures2RefOrthologsParams {

    @JsonProperty("ws2ref_feature_guids")
    private Map<String, String> ws2refFeatureGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("ws2ref_feature_guids")
    public Map<String, String> getWs2refFeatureGuids() {
        return ws2refFeatureGuids;
    }

    @JsonProperty("ws2ref_feature_guids")
    public void setWs2refFeatureGuids(Map<String, String> ws2refFeatureGuids) {
        this.ws2refFeatureGuids = ws2refFeatureGuids;
    }

    public ConnectWSFeatures2RefOrthologsParams withWs2refFeatureGuids(Map<String, String> ws2refFeatureGuids) {
        this.ws2refFeatureGuids = ws2refFeatureGuids;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((("ConnectWSFeatures2RefOrthologsParams"+" [ws2refFeatureGuids=")+ ws2refFeatureGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
