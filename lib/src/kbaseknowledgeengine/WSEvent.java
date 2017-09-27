
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
 * <p>Original spec-file type: WSEvent</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "storageCode",
    "accessGroupId",
    "accessGroupObjectId",
    "version",
    "newName",
    "timestamp",
    "eventType",
    "storageObjectType",
    "storageObjectTypeVersion",
    "isGlobalAccessed",
    "processed"
})
public class WSEvent {

    @JsonProperty("storageCode")
    private String storageCode;
    @JsonProperty("accessGroupId")
    private Long accessGroupId;
    @JsonProperty("accessGroupObjectId")
    private String accessGroupObjectId;
    @JsonProperty("version")
    private Long version;
    @JsonProperty("newName")
    private String newName;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("storageObjectType")
    private String storageObjectType;
    @JsonProperty("storageObjectTypeVersion")
    private Long storageObjectTypeVersion;
    @JsonProperty("isGlobalAccessed")
    private Long isGlobalAccessed;
    @JsonProperty("processed")
    private Long processed;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("storageCode")
    public String getStorageCode() {
        return storageCode;
    }

    @JsonProperty("storageCode")
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public WSEvent withStorageCode(String storageCode) {
        this.storageCode = storageCode;
        return this;
    }

    @JsonProperty("accessGroupId")
    public Long getAccessGroupId() {
        return accessGroupId;
    }

    @JsonProperty("accessGroupId")
    public void setAccessGroupId(Long accessGroupId) {
        this.accessGroupId = accessGroupId;
    }

    public WSEvent withAccessGroupId(Long accessGroupId) {
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

    public WSEvent withAccessGroupObjectId(String accessGroupObjectId) {
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

    public WSEvent withVersion(Long version) {
        this.version = version;
        return this;
    }

    @JsonProperty("newName")
    public String getNewName() {
        return newName;
    }

    @JsonProperty("newName")
    public void setNewName(String newName) {
        this.newName = newName;
    }

    public WSEvent withNewName(String newName) {
        this.newName = newName;
        return this;
    }

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public WSEvent withTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("eventType")
    public String getEventType() {
        return eventType;
    }

    @JsonProperty("eventType")
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public WSEvent withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    @JsonProperty("storageObjectType")
    public String getStorageObjectType() {
        return storageObjectType;
    }

    @JsonProperty("storageObjectType")
    public void setStorageObjectType(String storageObjectType) {
        this.storageObjectType = storageObjectType;
    }

    public WSEvent withStorageObjectType(String storageObjectType) {
        this.storageObjectType = storageObjectType;
        return this;
    }

    @JsonProperty("storageObjectTypeVersion")
    public Long getStorageObjectTypeVersion() {
        return storageObjectTypeVersion;
    }

    @JsonProperty("storageObjectTypeVersion")
    public void setStorageObjectTypeVersion(Long storageObjectTypeVersion) {
        this.storageObjectTypeVersion = storageObjectTypeVersion;
    }

    public WSEvent withStorageObjectTypeVersion(Long storageObjectTypeVersion) {
        this.storageObjectTypeVersion = storageObjectTypeVersion;
        return this;
    }

    @JsonProperty("isGlobalAccessed")
    public Long getIsGlobalAccessed() {
        return isGlobalAccessed;
    }

    @JsonProperty("isGlobalAccessed")
    public void setIsGlobalAccessed(Long isGlobalAccessed) {
        this.isGlobalAccessed = isGlobalAccessed;
    }

    public WSEvent withIsGlobalAccessed(Long isGlobalAccessed) {
        this.isGlobalAccessed = isGlobalAccessed;
        return this;
    }

    @JsonProperty("processed")
    public Long getProcessed() {
        return processed;
    }

    @JsonProperty("processed")
    public void setProcessed(Long processed) {
        this.processed = processed;
    }

    public WSEvent withProcessed(Long processed) {
        this.processed = processed;
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
        return ((((((((((((((((((((((((("WSEvent"+" [storageCode=")+ storageCode)+", accessGroupId=")+ accessGroupId)+", accessGroupObjectId=")+ accessGroupObjectId)+", version=")+ version)+", newName=")+ newName)+", timestamp=")+ timestamp)+", eventType=")+ eventType)+", storageObjectType=")+ storageObjectType)+", storageObjectTypeVersion=")+ storageObjectTypeVersion)+", isGlobalAccessed=")+ isGlobalAccessed)+", processed=")+ processed)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
