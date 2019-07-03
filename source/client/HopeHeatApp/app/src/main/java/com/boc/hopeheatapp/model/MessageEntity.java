package com.boc.hopeheatapp.model;

/**
 * @author dwl
 * @date 2019/7/3.
 */
public class MessageEntity {
    private String messsageId;

    private String msgFrom;

    private String msg;

    private String time;

    public String getMesssageId() {
        return messsageId;
    }

    public void setMesssageId(String messsageId) {
        this.messsageId = messsageId;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
