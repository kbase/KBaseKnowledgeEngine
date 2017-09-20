
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
 * <p>Original spec-file type: KEAppDescriptor</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "name",
    "version",
    "last_run_epoch",
    "nodes_created",
    "relations_created",
    "properties_set"
})
public class KEAppDescriptor {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private String version;
    @JsonProperty("last_run_epoch")
    private Long lastRunEpoch;
    @JsonProperty("nodes_created")
    private Long nodesCreated;
    @JsonProperty("relations_created")
    private Long relationsCreated;
    @JsonProperty("properties_set")
    private Long propertiesSet;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public KEAppDescriptor withGuid(String guid) {
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

    public KEAppDescriptor withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public KEAppDescriptor withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("last_run_epoch")
    public Long getLastRunEpoch() {
        return lastRunEpoch;
    }

    @JsonProperty("last_run_epoch")
    public void setLastRunEpoch(Long lastRunEpoch) {
        this.lastRunEpoch = lastRunEpoch;
    }

    public KEAppDescriptor withLastRunEpoch(Long lastRunEpoch) {
        this.lastRunEpoch = lastRunEpoch;
        return this;
    }

    @JsonProperty("nodes_created")
    public Long getNodesCreated() {
        return nodesCreated;
    }

    @JsonProperty("nodes_created")
    public void setNodesCreated(Long nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    public KEAppDescriptor withNodesCreated(Long nodesCreated) {
        this.nodesCreated = nodesCreated;
        return this;
    }

    @JsonProperty("relations_created")
    public Long getRelationsCreated() {
        return relationsCreated;
    }

    @JsonProperty("relations_created")
    public void setRelationsCreated(Long relationsCreated) {
        this.relationsCreated = relationsCreated;
    }

    public KEAppDescriptor withRelationsCreated(Long relationsCreated) {
        this.relationsCreated = relationsCreated;
        return this;
    }

    @JsonProperty("properties_set")
    public Long getPropertiesSet() {
        return propertiesSet;
    }

    @JsonProperty("properties_set")
    public void setPropertiesSet(Long propertiesSet) {
        this.propertiesSet = propertiesSet;
    }

    public KEAppDescriptor withPropertiesSet(Long propertiesSet) {
        this.propertiesSet = propertiesSet;
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
        return ((((((((((((((((("KEAppDescriptor"+" [guid=")+ guid)+", name=")+ name)+", version=")+ version)+", lastRunEpoch=")+ lastRunEpoch)+", nodesCreated=")+ nodesCreated)+", relationsCreated=")+ relationsCreated)+", propertiesSet=")+ propertiesSet)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
