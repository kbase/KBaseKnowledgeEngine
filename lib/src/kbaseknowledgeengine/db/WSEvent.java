package kbaseknowledgeengine.db;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WSEvent {
    @JsonProperty("storageCode")
    public String storageCode;
    @JsonProperty("accessGroupId")
    public Integer accessGroupId;
    @JsonProperty("accessGroupObjectId")
    public Integer accessGroupObjectId;
    @JsonProperty("version")
    public Integer version;
    @JsonProperty("newName")
    public String newName;
    @JsonProperty("timestamp")
    public Long timestamp;
    @JsonProperty("eventType")
    public String eventType;
    @JsonProperty("storageObjectType")
    public String storageObjectType;
    @JsonProperty("storageObjectTypeVersion")
    public Integer storageObjectTypeVersion;
    @JsonProperty("isGlobalAccessed")
    public Boolean isGlobalAccessed;
    @JsonProperty("indexed")
    public Boolean indexed;
    @JsonProperty("processed")
    public Boolean processed;
}
