package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-04.
 */
public class ConsultEntity {
    @Expose
    @SerializedName("victimtestId")
    private String victimtestId;

    public String getVictimtestId() {
        return victimtestId;
    }

    public void setVictimtestId(String victimtestId) {
        this.victimtestId = victimtestId;
    }
}
