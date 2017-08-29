
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
 * <p>Original spec-file type: ConnectorStatus</p>
 * <pre>
 * state - one of ?queued?, ?started?, ?finished?, ?error?.
 * output - either empty for queued/started states or error message for error state or output message for finished.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "user",
    "obj_ref",
    "obj_type",
    "connector_app",
    "job_id",
    "state",
    "output",
    "new_re_nodes",
    "updated_re_nodes",
    "new_re_links",
    "queued_epoch_ms",
    "started_epoch_ms",
    "finished_epoch_ms"
})
public class ConnectorStatus {

    @JsonProperty("user")
    private String user;
    @JsonProperty("obj_ref")
    private String objRef;
    @JsonProperty("obj_type")
    private String objType;
    @JsonProperty("connector_app")
    private String connectorApp;
    @JsonProperty("job_id")
    private String jobId;
    @JsonProperty("state")
    private String state;
    @JsonProperty("output")
    private String output;
    @JsonProperty("new_re_nodes")
    private Long newReNodes;
    @JsonProperty("updated_re_nodes")
    private Long updatedReNodes;
    @JsonProperty("new_re_links")
    private Long newReLinks;
    @JsonProperty("queued_epoch_ms")
    private Long queuedEpochMs;
    @JsonProperty("started_epoch_ms")
    private Long startedEpochMs;
    @JsonProperty("finished_epoch_ms")
    private Long finishedEpochMs;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    public ConnectorStatus withUser(String user) {
        this.user = user;
        return this;
    }

    @JsonProperty("obj_ref")
    public String getObjRef() {
        return objRef;
    }

    @JsonProperty("obj_ref")
    public void setObjRef(String objRef) {
        this.objRef = objRef;
    }

    public ConnectorStatus withObjRef(String objRef) {
        this.objRef = objRef;
        return this;
    }

    @JsonProperty("obj_type")
    public String getObjType() {
        return objType;
    }

    @JsonProperty("obj_type")
    public void setObjType(String objType) {
        this.objType = objType;
    }

    public ConnectorStatus withObjType(String objType) {
        this.objType = objType;
        return this;
    }

    @JsonProperty("connector_app")
    public String getConnectorApp() {
        return connectorApp;
    }

    @JsonProperty("connector_app")
    public void setConnectorApp(String connectorApp) {
        this.connectorApp = connectorApp;
    }

    public ConnectorStatus withConnectorApp(String connectorApp) {
        this.connectorApp = connectorApp;
        return this;
    }

    @JsonProperty("job_id")
    public String getJobId() {
        return jobId;
    }

    @JsonProperty("job_id")
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public ConnectorStatus withJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public ConnectorStatus withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("output")
    public String getOutput() {
        return output;
    }

    @JsonProperty("output")
    public void setOutput(String output) {
        this.output = output;
    }

    public ConnectorStatus withOutput(String output) {
        this.output = output;
        return this;
    }

    @JsonProperty("new_re_nodes")
    public Long getNewReNodes() {
        return newReNodes;
    }

    @JsonProperty("new_re_nodes")
    public void setNewReNodes(Long newReNodes) {
        this.newReNodes = newReNodes;
    }

    public ConnectorStatus withNewReNodes(Long newReNodes) {
        this.newReNodes = newReNodes;
        return this;
    }

    @JsonProperty("updated_re_nodes")
    public Long getUpdatedReNodes() {
        return updatedReNodes;
    }

    @JsonProperty("updated_re_nodes")
    public void setUpdatedReNodes(Long updatedReNodes) {
        this.updatedReNodes = updatedReNodes;
    }

    public ConnectorStatus withUpdatedReNodes(Long updatedReNodes) {
        this.updatedReNodes = updatedReNodes;
        return this;
    }

    @JsonProperty("new_re_links")
    public Long getNewReLinks() {
        return newReLinks;
    }

    @JsonProperty("new_re_links")
    public void setNewReLinks(Long newReLinks) {
        this.newReLinks = newReLinks;
    }

    public ConnectorStatus withNewReLinks(Long newReLinks) {
        this.newReLinks = newReLinks;
        return this;
    }

    @JsonProperty("queued_epoch_ms")
    public Long getQueuedEpochMs() {
        return queuedEpochMs;
    }

    @JsonProperty("queued_epoch_ms")
    public void setQueuedEpochMs(Long queuedEpochMs) {
        this.queuedEpochMs = queuedEpochMs;
    }

    public ConnectorStatus withQueuedEpochMs(Long queuedEpochMs) {
        this.queuedEpochMs = queuedEpochMs;
        return this;
    }

    @JsonProperty("started_epoch_ms")
    public Long getStartedEpochMs() {
        return startedEpochMs;
    }

    @JsonProperty("started_epoch_ms")
    public void setStartedEpochMs(Long startedEpochMs) {
        this.startedEpochMs = startedEpochMs;
    }

    public ConnectorStatus withStartedEpochMs(Long startedEpochMs) {
        this.startedEpochMs = startedEpochMs;
        return this;
    }

    @JsonProperty("finished_epoch_ms")
    public Long getFinishedEpochMs() {
        return finishedEpochMs;
    }

    @JsonProperty("finished_epoch_ms")
    public void setFinishedEpochMs(Long finishedEpochMs) {
        this.finishedEpochMs = finishedEpochMs;
    }

    public ConnectorStatus withFinishedEpochMs(Long finishedEpochMs) {
        this.finishedEpochMs = finishedEpochMs;
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
        return ((((((((((((((((((((((((((((("ConnectorStatus"+" [user=")+ user)+", objRef=")+ objRef)+", objType=")+ objType)+", connectorApp=")+ connectorApp)+", jobId=")+ jobId)+", state=")+ state)+", output=")+ output)+", newReNodes=")+ newReNodes)+", updatedReNodes=")+ updatedReNodes)+", newReLinks=")+ newReLinks)+", queuedEpochMs=")+ queuedEpochMs)+", startedEpochMs=")+ startedEpochMs)+", finishedEpochMs=")+ finishedEpochMs)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
