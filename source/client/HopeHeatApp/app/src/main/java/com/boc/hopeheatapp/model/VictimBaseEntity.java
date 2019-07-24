package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zpwu3 on 2019-07-24.
 */
public class VictimBaseEntity {
    @Expose
    @SerializedName("numId")
    private int numId;

    @Expose
    @SerializedName("totalPages")
    private int totalPages;

    @Expose
    @SerializedName("endFlag")
    private String endFlag;

    @Expose
    @SerializedName("victim")
    private List<VictimEntity> victims;

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(String endFlag) {
        this.endFlag = endFlag;
    }

    public List<VictimEntity> getVictims() {
        return victims;
    }

    public void setVictims(List<VictimEntity> victims) {
        this.victims = victims;
    }
}
