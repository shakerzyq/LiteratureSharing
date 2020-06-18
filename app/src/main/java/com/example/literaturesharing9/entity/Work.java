package com.example.literaturesharing9.entity;


import java.io.Serializable;

public class Work implements Serializable {
    private String workId;
    private String workName;
    private String workTime;
    private String type;
    private String workContent;
    private int pushStatus;
    private int auditStatus;
    private int workZan;
    private String userId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getWorkZan() {
        return workZan;
    }

    public void setWorkZan(int workZan) {
        this.workZan = workZan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workId=" + workId +
                ", workName='" + workName + '\'' +
                ", workTime='" + workTime + '\'' +
                ", type='" + type + '\'' +
                ", workContent='" + workContent + '\'' +
                ", pushStatus=" + pushStatus +
                ", auditStatus=" + auditStatus +
                ", workZan=" + workZan +
                ", userId='" + userId + '\'' +
                '}';
    }
}
