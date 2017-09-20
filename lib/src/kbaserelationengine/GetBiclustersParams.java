
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
 * <p>Original spec-file type: GetBiclustersParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "keapp_guid",
    "taxonomy_guid",
    "compendium_guid"
})
public class GetBiclustersParams {

    @JsonProperty("keapp_guid")
    private String keappGuid;
    @JsonProperty("taxonomy_guid")
    private String taxonomyGuid;
    @JsonProperty("compendium_guid")
    private String compendiumGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("keapp_guid")
    public String getKeappGuid() {
        return keappGuid;
    }

    @JsonProperty("keapp_guid")
    public void setKeappGuid(String keappGuid) {
        this.keappGuid = keappGuid;
    }

    public GetBiclustersParams withKeappGuid(String keappGuid) {
        this.keappGuid = keappGuid;
        return this;
    }

    @JsonProperty("taxonomy_guid")
    public String getTaxonomyGuid() {
        return taxonomyGuid;
    }

    @JsonProperty("taxonomy_guid")
    public void setTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
    }

    public GetBiclustersParams withTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
        return this;
    }

    @JsonProperty("compendium_guid")
    public String getCompendiumGuid() {
        return compendiumGuid;
    }

    @JsonProperty("compendium_guid")
    public void setCompendiumGuid(String compendiumGuid) {
        this.compendiumGuid = compendiumGuid;
    }

    public GetBiclustersParams withCompendiumGuid(String compendiumGuid) {
        this.compendiumGuid = compendiumGuid;
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
        return ((((((((("GetBiclustersParams"+" [keappGuid=")+ keappGuid)+", taxonomyGuid=")+ taxonomyGuid)+", compendiumGuid=")+ compendiumGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
