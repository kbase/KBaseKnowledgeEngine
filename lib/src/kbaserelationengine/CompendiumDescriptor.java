
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
 * <p>Original spec-file type: CompendiumDescriptor</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "name",
    "data_type",
    "taxonomy_guid",
    "ws_ndarray_id"
})
public class CompendiumDescriptor {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("data_type")
    private String dataType;
    @JsonProperty("taxonomy_guid")
    private String taxonomyGuid;
    @JsonProperty("ws_ndarray_id")
    private String wsNdarrayId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public CompendiumDescriptor withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public CompendiumDescriptor withName(String name) {
        this.name = name;
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

    public CompendiumDescriptor withDataType(String dataType) {
        this.dataType = dataType;
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

    public CompendiumDescriptor withTaxonomyGuid(String taxonomyGuid) {
        this.taxonomyGuid = taxonomyGuid;
        return this;
    }

    @JsonProperty("ws_ndarray_id")
    public String getWsNdarrayId() {
        return wsNdarrayId;
    }

    @JsonProperty("ws_ndarray_id")
    public void setWsNdarrayId(String wsNdarrayId) {
        this.wsNdarrayId = wsNdarrayId;
    }

    public CompendiumDescriptor withWsNdarrayId(String wsNdarrayId) {
        this.wsNdarrayId = wsNdarrayId;
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
        return ((((((((((((("CompendiumDescriptor"+" [guid=")+ guid)+", name=")+ name)+", dataType=")+ dataType)+", taxonomyGuid=")+ taxonomyGuid)+", wsNdarrayId=")+ wsNdarrayId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
