package com.boc.hopeheatapp.model;

/**
 * Created by Mao Jiqing on 2016/10/15.
 */



public class ChatMessageBean {
    private Long id;
    private String UserContent;
    private String time;
    private int type;


    public ChatMessageBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserContent() {
        return UserContent;
    }

    public void setUserContent(String userContent) {
        UserContent = userContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
