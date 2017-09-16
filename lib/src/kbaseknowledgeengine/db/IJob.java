package kbaseknowledgeengine.db;

public interface IJob {
    public String getJobId();
    public String getState();
    public void setState(String state);
    public String getMessage();
    public void setMessage(String message);
    public Integer getNewReNodes();
    public void setNewReNodes(Integer newReNodes);
    public Integer getUpdatedReNodes();
    public void setUpdatedReNodes(Integer updatedReNodes);
    public Integer getNewReLinks();
    public void setNewReLinks(Integer newReLinks);
    public Long getStartedEpochMs();
    public void setStartedEpochMs(Long startedEpochMs);
    public Long getFinishedEpochMs();
    public void setFinishedEpochMs(Long finishedEpochMs);
}
