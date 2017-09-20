
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
 * <p>Original spec-file type: StoreWSGenomeParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome_ref",
    "feature_guids"
})
public class StoreWSGenomeParams {

    @JsonProperty("genome_ref")
    private java.lang.String genomeRef;
    @JsonProperty("feature_guids")
    private List<String> featureGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("genome_ref")
    public java.lang.String getGenomeRef() {
        return genomeRef;
    }

    @JsonProperty("genome_ref")
    public void setGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
    }

    public StoreWSGenomeParams withGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
        return this;
    }

    @JsonProperty("feature_guids")
    public List<String> getFeatureGuids() {
        return featureGuids;
    }

    @JsonProperty("feature_guids")
    public void setFeatureGuids(List<String> featureGuids) {
        this.featureGuids = featureGuids;
    }

    public StoreWSGenomeParams withFeatureGuids(List<String> featureGuids) {
        this.featureGuids = featureGuids;
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
        return ((((((("StoreWSGenomeParams"+" [genomeRef=")+ genomeRef)+", featureGuids=")+ featureGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
