package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 热门问题实体类
 *
 * @author dwl
 * @date 2018/2/26.
 */
public class HotQuestionEntity {
    /**
     * itemId : 2176146
     * questionId : 2176146
     * question : 我如何进行信用卡挂失？
     * crtDate : 20180224
     * freq : 12
     */
    @Expose
    @SerializedName("itemId")
    private String itemId;

    @Expose
    @SerializedName("questionId")
    private String questionId;

    @Expose
    @SerializedName("question")
    private String question;

    @Expose
    @SerializedName("crtDate")
    private String crtDate;

    @Expose
    @SerializedName("freq")
    private String freq;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(String crtDate) {
        this.crtDate = crtDate;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }
}
