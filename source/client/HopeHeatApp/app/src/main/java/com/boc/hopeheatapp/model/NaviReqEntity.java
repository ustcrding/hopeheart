package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2018/10/29.
 */
public class NaviReqEntity extends BaseReqEntity {

    @Expose
    @SerializedName("question")
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
