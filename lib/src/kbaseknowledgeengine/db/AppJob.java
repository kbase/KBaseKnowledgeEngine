package kbaseknowledgeengine.db;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppJob {
    @JsonProperty("job_id")
    private String jobId;
    @JsonProperty("app")
    private String app;
    @JsonProperty("user")
    private String user;
    @JsonProperty("state")
    private String state;
    @JsonProperty("new_re_nodes")
    Integer newReNodes;
    @JsonProperty("updated_re_nodes")
    Integer updatedReNodes;
    @JsonProperty("new_re_links")
    Integer newReLinks;
    @JsonProperty("queued_epoch_ms")
    Long queuedEpochMs;
    @JsonProperty("started_epoch_ms")
    Long startedEpochMs;
    @JsonProperty("finished_epoch_ms")
    Long finishedEpochMs;
    
    @JsonProperty("job_id")
    public String getJobId() {
        return jobId;
    }
    
    @JsonProperty("job_id")
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    
    @JsonProperty("app")
    public String getApp() {
        return app;
    }
    
    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
    }
    
    @JsonProperty("user")
    public String getUser() {
        return user;
    }
    
    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }
    
    @JsonProperty("state")
    public String getState() {
        return state;
    }
    
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }
    
    @JsonProperty("new_re_nodes")
    public Integer getNewReNodes() {
        return newReNodes;
    }
    
    @JsonProperty("new_re_nodes")
    public void setNewReNodes(Integer newReNodes) {
        this.newReNodes = newReNodes;
    }
    
    @JsonProperty("updated_re_nodes")
    public Integer getUpdatedReNodes() {
        return updatedReNodes;
    }
    
    @JsonProperty("updated_re_nodes")
    public void setUpdatedReNodes(Integer updatedReNodes) {
        this.updatedReNodes = updatedReNodes;
    }
    
    @JsonProperty("new_re_links")
    public Integer getNewReLinks() {
        return newReLinks;
    }
    
    @JsonProperty("new_re_links")
    public void setNewReLinks(Integer newReLinks) {
        this.newReLinks = newReLinks;
    }
    
    @JsonProperty("queued_epoch_ms")
    public Long getQueuedEpochMs() {
        return queuedEpochMs;
    }
    
    @JsonProperty("queued_epoch_ms")
    public void setQueuedEpochMs(Long queuedEpochMs) {
        this.queuedEpochMs = queuedEpochMs;
    }
    
    @JsonProperty("started_epoch_ms")
    public Long getStartedEpochMs() {
        return startedEpochMs;
    }
    
    @JsonProperty("started_epoch_ms")
    public void setStartedEpochMs(Long startedEpochMs) {
        this.startedEpochMs = startedEpochMs;
    }
    
    @JsonProperty("finished_epoch_ms")
    public Long getFinishedEpochMs() {
        return finishedEpochMs;
    }
    
    @JsonProperty("finished_epoch_ms")
    public void setFinishedEpochMs(Long finishedEpochMs) {
        this.finishedEpochMs = finishedEpochMs;
    }
}
