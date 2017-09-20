
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
 * <p>Original spec-file type: GetFeatureTermsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "taxon_guid",
    "term_space"
})
public class GetFeatureTermsParams {

    @JsonProperty("taxon_guid")
    private String taxonGuid;
    @JsonProperty("term_space")
    private String termSpace;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("taxon_guid")
    public String getTaxonGuid() {
        return taxonGuid;
    }

    @JsonProperty("taxon_guid")
    public void setTaxonGuid(String taxonGuid) {
        this.taxonGuid = taxonGuid;
    }

    public GetFeatureTermsParams withTaxonGuid(String taxonGuid) {
        this.taxonGuid = taxonGuid;
        return this;
    }

    @JsonProperty("term_space")
    public String getTermSpace() {
        return termSpace;
    }

    @JsonProperty("term_space")
    public void setTermSpace(String termSpace) {
        this.termSpace = termSpace;
    }

    public GetFeatureTermsParams withTermSpace(String termSpace) {
        this.termSpace = termSpace;
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
        return ((((((("GetFeatureTermsParams"+" [taxonGuid=")+ taxonGuid)+", termSpace=")+ termSpace)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
