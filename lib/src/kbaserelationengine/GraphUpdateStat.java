
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
 * <p>Original spec-file type: GraphUpdateStat</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "nodes_created",
    "nodes_deleted",
    "relationships_created",
    "relationships_deleted",
    "properties_set"
})
public class GraphUpdateStat {

    @JsonProperty("nodes_created")
    private Long nodesCreated;
    @JsonProperty("nodes_deleted")
    private Long nodesDeleted;
    @JsonProperty("relationships_created")
    private Long relationshipsCreated;
    @JsonProperty("relationships_deleted")
    private Long relationshipsDeleted;
    @JsonProperty("properties_set")
    private Long propertiesSet;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nodes_created")
    public Long getNodesCreated() {
        return nodesCreated;
    }

    @JsonProperty("nodes_created")
    public void setNodesCreated(Long nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    public GraphUpdateStat withNodesCreated(Long nodesCreated) {
        this.nodesCreated = nodesCreated;
        return this;
    }

    @JsonProperty("nodes_deleted")
    public Long getNodesDeleted() {
        return nodesDeleted;
    }

    @JsonProperty("nodes_deleted")
    public void setNodesDeleted(Long nodesDeleted) {
        this.nodesDeleted = nodesDeleted;
    }

    public GraphUpdateStat withNodesDeleted(Long nodesDeleted) {
        this.nodesDeleted = nodesDeleted;
        return this;
    }

    @JsonProperty("relationships_created")
    public Long getRelationshipsCreated() {
        return relationshipsCreated;
    }

    @JsonProperty("relationships_created")
    public void setRelationshipsCreated(Long relationshipsCreated) {
        this.relationshipsCreated = relationshipsCreated;
    }

    public GraphUpdateStat withRelationshipsCreated(Long relationshipsCreated) {
        this.relationshipsCreated = relationshipsCreated;
        return this;
    }

    @JsonProperty("relationships_deleted")
    public Long getRelationshipsDeleted() {
        return relationshipsDeleted;
    }

    @JsonProperty("relationships_deleted")
    public void setRelationshipsDeleted(Long relationshipsDeleted) {
        this.relationshipsDeleted = relationshipsDeleted;
    }

    public GraphUpdateStat withRelationshipsDeleted(Long relationshipsDeleted) {
        this.relationshipsDeleted = relationshipsDeleted;
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

    public GraphUpdateStat withPropertiesSet(Long propertiesSet) {
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
        return ((((((((((((("GraphUpdateStat"+" [nodesCreated=")+ nodesCreated)+", nodesDeleted=")+ nodesDeleted)+", relationshipsCreated=")+ relationshipsCreated)+", relationshipsDeleted=")+ relationshipsDeleted)+", propertiesSet=")+ propertiesSet)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
