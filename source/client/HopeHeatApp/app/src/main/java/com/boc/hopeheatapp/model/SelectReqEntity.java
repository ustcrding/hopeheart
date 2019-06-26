package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 多条选择的entity
 *
 * @author dwl
 * @date 2018/10/29.
 */
public class SelectReqEntity extends BaseReqEntity {

    @Expose
    @SerializedName("sessionId")
    private String sessionId;

    @Expose
    @SerializedName("itemId")
    private String itemId;

    @Expose
    @SerializedName("questionId")
    private String questionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
