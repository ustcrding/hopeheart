package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class EvaluationEntity {
    @Expose
    @SerializedName("scores")
    private int scores;

    @Expose
    @SerializedName("level")
    private String result;


    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
