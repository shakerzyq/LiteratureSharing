package com.example.literaturesharing9.entity;

import java.util.Date;

/**
 * @author 周杨清
 * @time 2020/6/17
 */
public class Comment {
    private String workid;
    private String viewerid;
    private String content;
    private String reply;
    private String  date;
    private int zan;


    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getViewerid() {
        return viewerid;
    }

    public void setViewerid(String viewerid) {
        this.viewerid = viewerid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public Comment(String workid, String viewerid, String content, String reply, String date, int zan) {
        this.workid = workid;
        this.viewerid = viewerid;
        this.content = content;
        this.reply = reply;
        this.date = date;
        this.zan = zan;
    }
}
