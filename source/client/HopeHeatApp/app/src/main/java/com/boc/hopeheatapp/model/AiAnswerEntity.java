package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2018/1/16.
 */
public class AiAnswerEntity {

    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("answer")
    private String answer;
    @Expose
    @SerializedName("manuFlag")
    private boolean manuFlag;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isManuFlag() {
        return manuFlag;
    }

    public void setManuFlag(boolean manuFlag) {
        this.manuFlag = manuFlag;
    }
}
