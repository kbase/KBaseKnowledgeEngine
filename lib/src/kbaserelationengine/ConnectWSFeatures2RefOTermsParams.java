
package kbaserelationengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: ConnectWSFeatures2RefOTermsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "feature2term_list"
})
public class ConnectWSFeatures2RefOTermsParams {

    @JsonProperty("feature2term_list")
    private Map<String, List<String>> feature2termList;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("feature2term_list")
    public Map<String, List<String>> getFeature2termList() {
        return feature2termList;
    }

    @JsonProperty("feature2term_list")
    public void setFeature2termList(Map<String, List<String>> feature2termList) {
        this.feature2termList = feature2termList;
    }

    public ConnectWSFeatures2RefOTermsParams withFeature2termList(Map<String, List<String>> feature2termList) {
        this.feature2termList = feature2termList;
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
        return ((((("ConnectWSFeatures2RefOTermsParams"+" [feature2termList=")+ feature2termList)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
