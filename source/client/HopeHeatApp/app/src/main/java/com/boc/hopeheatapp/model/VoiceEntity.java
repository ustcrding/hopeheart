package com.boc.hopeheatapp.model;

/**
 * @author dwl
 * @date 2018/1/16.
 */
public class VoiceEntity {


    private String content;
    private int flag;

    public VoiceEntity(String content, int flag) {
        setContent(content);
        setFlag(flag);
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
}
