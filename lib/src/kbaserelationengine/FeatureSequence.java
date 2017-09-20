
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
 * <p>Original spec-file type: FeatureSequence</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "taxonomy_guid",
    "feature_guid",
    "proteinSequence",
    "nucleotideSequence"
})
public class FeatureSequence {

    @JsonProperty("taxonomy_guid")
    private String taxonomyGuid;
    @JsonProperty("feature_guid")
    private String featureGuid;
    @JsonProperty("proteinSequence")
    private String proteinSequence;
    @JsonProperty("nucleotideSequence")
    private String nucleotideSequence;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("taxonomy_guid")
    public String getTaxonomyGuid() {
        return taxonomyGuid;
    }

    @JsonProperty("taxonomy_guid")
    public void setTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
    }

    public FeatureSequence withTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
        return this;
    }

    @JsonProperty("feature_guid")
    public String getFeatureGuid() {
        return featureGuid;
    }

    @JsonProperty("feature_guid")
    public void setFeatureGuid(String featureGuid) {
        this.featureGuid = featureGuid;
    }

    public FeatureSequence withFeatureGuid(String featureGuid) {
        this.featureGuid = featureGuid;
        return this;
    }

    @JsonProperty("proteinSequence")
    public String getProteinSequence() {
        return proteinSequence;
    }

    @JsonProperty("proteinSequence")
    public void setProteinSequence(String proteinSequence) {
        this.proteinSequence = proteinSequence;
    }

    public FeatureSequence withProteinSequence(String proteinSequence) {
        this.proteinSequence = proteinSequence;
        return this;
    }

    @JsonProperty("nucleotideSequence")
    public String getNucleotideSequence() {
        return nucleotideSequence;
    }

    @JsonProperty("nucleotideSequence")
    public void setNucleotideSequence(String nucleotideSequence) {
        this.nucleotideSequence = nucleotideSequence;
    }

    public FeatureSequence withNucleotideSequence(String nucleotideSequence) {
        this.nucleotideSequence = nucleotideSequence;
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
        return ((((((((((("FeatureSequence"+" [taxonomyGuid=")+ taxonomyGuid)+", featureGuid=")+ featureGuid)+", proteinSequence=")+ proteinSequence)+", nucleotideSequence=")+ nucleotideSequence)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
