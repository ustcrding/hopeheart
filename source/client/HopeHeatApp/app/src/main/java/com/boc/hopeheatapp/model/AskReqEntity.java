package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2018/10/25.
 */
public class AskReqEntity extends BaseReqEntity {
    @Expose
    @SerializedName("question")
    private String question;

    @Expose
    @SerializedName("type")
    private int type;

    @Expose
    @SerializedName("questionId")
    private String questionId;

    @Expose
    @SerializedName("catalogId")
    private String catalogId;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
}
