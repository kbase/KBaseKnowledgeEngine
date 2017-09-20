
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
 * <p>Original spec-file type: GetFeatureSequencesParams</p>
 * <pre>
 * *
 * One of guids should provided.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "taxonomy_guid",
    "ortholog_guid",
    "goterm_guid"
})
public class GetFeatureSequencesParams {

    @JsonProperty("taxonomy_guid")
    private String taxonomyGuid;
    @JsonProperty("ortholog_guid")
    private String orthologGuid;
    @JsonProperty("goterm_guid")
    private String gotermGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("taxonomy_guid")
    public String getTaxonomyGuid() {
        return taxonomyGuid;
    }

    @JsonProperty("taxonomy_guid")
    public void setTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
    }

    public GetFeatureSequencesParams withTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
        return this;
    }

    @JsonProperty("ortholog_guid")
    public String getOrthologGuid() {
        return orthologGuid;
    }

    @JsonProperty("ortholog_guid")
    public void setOrthologGuid(String orthologGuid) {
        this.orthologGuid = orthologGuid;
    }

    public GetFeatureSequencesParams withOrthologGuid(String orthologGuid) {
        this.orthologGuid = orthologGuid;
        return this;
    }

    @JsonProperty("goterm_guid")
    public String getGotermGuid() {
        return gotermGuid;
    }

    @JsonProperty("goterm_guid")
    public void setGotermGuid(String gotermGuid) {
        this.gotermGuid = gotermGuid;
    }

    public GetFeatureSequencesParams withGotermGuid(String gotermGuid) {
        this.gotermGuid = gotermGuid;
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
        return ((((((((("GetFeatureSequencesParams"+" [taxonomyGuid=")+ taxonomyGuid)+", orthologGuid=")+ orthologGuid)+", gotermGuid=")+ gotermGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
