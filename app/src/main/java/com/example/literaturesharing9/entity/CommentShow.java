package com.example.literaturesharing9.entity;

/**
 * @author 周杨清
 * @time 2020/6/17
 * 用于显示评论的实体类
 */
public class CommentShow {

    private String viewerid;
    private String content;
    private String  time;
    private String username;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
