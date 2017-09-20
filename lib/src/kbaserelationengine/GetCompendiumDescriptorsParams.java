
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
 * <p>Original spec-file type: GetCompendiumDescriptorsParams</p>
 * <pre>
 * *
 * data_type - one of ["expression","fitness"]
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "taxonomy_guid",
    "data_type"
})
public class GetCompendiumDescriptorsParams {

    @JsonProperty("taxonomy_guid")
    private String taxonomyGuid;
    @JsonProperty("data_type")
    private String dataType;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("taxonomy_guid")
    public String getTaxonomyGuid() {
        return taxonomyGuid;
    }

    @JsonProperty("taxonomy_guid")
    public void setTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
    }

    public GetCompendiumDescriptorsParams withTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
        return this;
    }

    @JsonProperty("data_type")
    public String getDataType() {
        return dataType;
    }

    @JsonProperty("data_type")
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public GetCompendiumDescriptorsParams withDataType(String dataType) {
        this.dataType = dataType;
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
        return ((((((("GetCompendiumDescriptorsParams"+" [taxonomyGuid=")+ taxonomyGuid)+", dataType=")+ dataType)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
