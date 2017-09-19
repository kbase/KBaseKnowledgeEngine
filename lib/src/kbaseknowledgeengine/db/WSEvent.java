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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accessGroupId == null) ? 0 : accessGroupId.hashCode());
        result = prime * result + ((accessGroupObjectId == null) ? 0
                : accessGroupObjectId.hashCode());
        result = prime * result
                + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + ((storageObjectType == null) ? 0
                : storageObjectType.hashCode());
        result = prime * result
                + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WSEvent other = (WSEvent) obj;
        if (accessGroupId == null) {
            if (other.accessGroupId != null)
                return false;
        } else if (!accessGroupId.equals(other.accessGroupId))
            return false;
        if (accessGroupObjectId == null) {
            if (other.accessGroupObjectId != null)
                return false;
        } else if (!accessGroupObjectId.equals(other.accessGroupObjectId))
            return false;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        if (storageObjectType == null) {
            if (other.storageObjectType != null)
                return false;
        } else if (!storageObjectType.equals(other.storageObjectType))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WSEvent [storageCode=" + storageCode + ", accessGroupId="
                + accessGroupId + ", accessGroupObjectId=" + accessGroupObjectId
                + ", version=" + version + ", newName=" + newName
                + ", timestamp=" + timestamp + ", eventType=" + eventType
                + ", storageObjectType=" + storageObjectType
                + ", storageObjectTypeVersion=" + storageObjectTypeVersion
                + ", isGlobalAccessed=" + isGlobalAccessed + ", indexed="
                + indexed + ", processed=" + processed + "]";
    }
}
