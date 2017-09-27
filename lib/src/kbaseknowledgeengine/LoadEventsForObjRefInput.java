
package kbaseknowledgeengine;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: LoadEventsForObjRefInput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "accessGroupId",
    "accessGroupObjectId",
    "version"
})
public class LoadEventsForObjRefInput {

    @JsonProperty("accessGroupId")
    private Long accessGroupId;
    @JsonProperty("accessGroupObjectId")
    private String accessGroupObjectId;
    @JsonProperty("version")
    private Long version;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("accessGroupId")
    public Long getAccessGroupId() {
        return accessGroupId;
    }

    @JsonProperty("accessGroupId")
    public void setAccessGroupId(Long accessGroupId) {
        this.accessGroupId = accessGroupId;
    }

    public LoadEventsForObjRefInput withAccessGroupId(Long accessGroupId) {
        this.accessGroupId = accessGroupId;
        return this;
    }

    @JsonProperty("accessGroupObjectId")
    public String getAccessGroupObjectId() {
        return accessGroupObjectId;
    }

    @JsonProperty("accessGroupObjectId")
    public void setAccessGroupObjectId(String accessGroupObjectId) {
        this.accessGroupObjectId = accessGroupObjectId;
    }

    public LoadEventsForObjRefInput withAccessGroupObjectId(String accessGroupObjectId) {
        this.accessGroupObjectId = accessGroupObjectId;
        return this;
    }

    @JsonProperty("version")
    public Long getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Long version) {
        this.version = version;
    }

    public LoadEventsForObjRefInput withVersion(Long version) {
        this.version = version;
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
        return ((((((((("LoadEventsForObjRefInput"+" [accessGroupId=")+ accessGroupId)+", accessGroupObjectId=")+ accessGroupObjectId)+", version=")+ version)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
