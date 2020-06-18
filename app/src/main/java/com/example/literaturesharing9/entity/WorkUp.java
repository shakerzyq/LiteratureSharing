package com.example.literaturesharing9.entity;

/**
 * work实体类的升级版,加入了作者名
 */
public class WorkUp {
    private String workid;
    private String workname;
    private String worktime;
    private String type;
    private String workcontent;
    private int pushstatus;
    private int auditstatus;
    private int workzan;
    private String userid;
    private String writername;

    public String getWritername() {
        return writername;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkcontent() {
        return workcontent;
    }

    public void setWorkcontent(String workcontent) {
        this.workcontent = workcontent;
    }

    public int getPushstatus() {
        return pushstatus;
    }

    public void setPushstatus(int pushstatus) {
        this.pushstatus = pushstatus;
    }

    public int getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(int auditstatus) {
        this.auditstatus = auditstatus;
    }

    public int getWorkzan() {
        return workzan;
    }

    public void setWorkzan(int workzan) {
        this.workzan = workzan;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
