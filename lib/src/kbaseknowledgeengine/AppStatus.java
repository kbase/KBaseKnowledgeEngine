
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
 * <p>Original spec-file type: AppStatus</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "user",
    "app",
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
public class AppStatus {

    @JsonProperty("user")
    private String user;
    @JsonProperty("app")
    private String app;
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

    public AppStatus withUser(String user) {
        this.user = user;
        return this;
    }

    @JsonProperty("app")
    public String getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
    }

    public AppStatus withApp(String app) {
        this.app = app;
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

    public AppStatus withJobId(String jobId) {
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

    public AppStatus withState(String state) {
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

    public AppStatus withOutput(String output) {
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

    public AppStatus withNewReNodes(Long newReNodes) {
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

    public AppStatus withUpdatedReNodes(Long updatedReNodes) {
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

    public AppStatus withNewReLinks(Long newReLinks) {
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

    public AppStatus withQueuedEpochMs(Long queuedEpochMs) {
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

    public AppStatus withStartedEpochMs(Long startedEpochMs) {
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

    public AppStatus withFinishedEpochMs(Long finishedEpochMs) {
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
        return ((((((((((((((((((((((((("AppStatus"+" [user=")+ user)+", app=")+ app)+", jobId=")+ jobId)+", state=")+ state)+", output=")+ output)+", newReNodes=")+ newReNodes)+", updatedReNodes=")+ updatedReNodes)+", newReLinks=")+ newReLinks)+", queuedEpochMs=")+ queuedEpochMs)+", startedEpochMs=")+ startedEpochMs)+", finishedEpochMs=")+ finishedEpochMs)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
