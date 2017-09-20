
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
 * <p>Original spec-file type: FeatureTerms</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "feature_guid",
    "term_guids"
})
public class FeatureTerms {

    @JsonProperty("feature_guid")
    private java.lang.String featureGuid;
    @JsonProperty("term_guids")
    private List<String> termGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("feature_guid")
    public java.lang.String getFeatureGuid() {
        return featureGuid;
    }

    @JsonProperty("feature_guid")
    public void setFeatureGuid(java.lang.String featureGuid) {
        this.featureGuid = featureGuid;
    }

    public FeatureTerms withFeatureGuid(java.lang.String featureGuid) {
        this.featureGuid = featureGuid;
        return this;
    }

    @JsonProperty("term_guids")
    public List<String> getTermGuids() {
        return termGuids;
    }

    @JsonProperty("term_guids")
    public void setTermGuids(List<String> termGuids) {
        this.termGuids = termGuids;
    }

    public FeatureTerms withTermGuids(List<String> termGuids) {
        this.termGuids = termGuids;
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
        return ((((((("FeatureTerms"+" [featureGuid=")+ featureGuid)+", termGuids=")+ termGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
