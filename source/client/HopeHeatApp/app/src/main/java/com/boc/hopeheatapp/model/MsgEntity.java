package com.boc.hopeheatapp.model;

/**
 * @author dwl
 * @date 2018/1/16.
 */
public class MsgEntity {
    // 消息类型， 1： 发送，2：接收
    public static final int TYPE_SEND = 1;
    public static final int TYPE_RECEIVER = 2;

    //消息来源， 1：语音消息， 2：文本消息， 3：链接消息
    public static final int SOURCE_VOICE = 1;
    public static final int SOURCE_TEXT = 2;
    public static final int SOURCE_LINK = 3;

    public static final int UNKNOWN = 0;
    public static final int LIKE = 1;
    public static final int UNLIKE = 2;

    private Object content;
    private String voicePath;
    //消息类型
    private int msgType;
    //消息来源
    private int msgSource;

    private int liked;

    public MsgEntity() {
    }

    public MsgEntity(int msgType) {
        this.msgType = msgType;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgSource() {
        return msgSource;
    }

    public void setMsgSource(int msgSource) {
        this.msgSource = msgSource;
    }

    public static MsgEntity genAskMsg() {
        return new MsgEntity(MsgEntity.TYPE_SEND);
    }

    public static MsgEntity genAnswerMsg() {
        return new MsgEntity(MsgEntity.TYPE_RECEIVER);
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
