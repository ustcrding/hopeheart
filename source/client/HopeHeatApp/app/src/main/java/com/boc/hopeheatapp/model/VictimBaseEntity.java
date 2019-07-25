package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zpwu3 on 2019-07-24.
 */
public class VictimBaseEntity {
    @Expose
    @SerializedName("pageNumber")
    private int numId;

    @Expose
    @SerializedName("totalPage")
    private int totalPages;

    @Expose
    @SerializedName("lastPage")
    private boolean endFlag;

    @Expose
    @SerializedName("totalRow")
    private int totalRow;

    @Expose
    @SerializedName("list")
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

    public boolean isEndFlag() {
        return endFlag;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }

    public List<VictimEntity> getVictims() {
        return victims;
    }

    public void setVictims(List<VictimEntity> victims) {
        this.victims = victims;
    }


    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }
}
