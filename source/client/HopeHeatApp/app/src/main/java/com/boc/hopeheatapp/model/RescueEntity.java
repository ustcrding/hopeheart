package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class RescueEntity {
    @Expose
    @SerializedName("psyknowledgeId")
    private String psyknowledgeId;

    @Expose
    @SerializedName("psytestsId")
    private String psytestsId;

    @Expose
    @SerializedName("rescueKnowledge")
    private String rescueKnowledge;

    @Expose
    @SerializedName("rescueType")
    private String rescueType;

    @Expose
    @SerializedName("rescueSubtype")
    private String rescueSubType;

    @Expose
    @SerializedName("rescueknowledgeId")
    private String rescueknowledgeId;


    public String getPsyknowledgeId() {
        return psyknowledgeId;
    }

    public void setPsyknowledgeId(String psyknowledgeId) {
        this.psyknowledgeId = psyknowledgeId;
    }

    public String getPystestsId() {
        return psytestsId;
    }

    public void setPystestsId(String pystestsId) {
        this.psytestsId = pystestsId;
    }

    public String getRescueKnowledge() {
        return rescueKnowledge;
    }

    public void setRescueKnowledge(String rescueKnowledge) {
        this.rescueKnowledge = rescueKnowledge;
    }

    public String getRescueType() {
        return rescueType;
    }

    public void setRescueType(String rescueType) {
        this.rescueType = rescueType;
    }

    public String getRescueSubType() {
        return rescueSubType;
    }

    public void setRescueSubType(String rescueSubType) {
        this.rescueSubType = rescueSubType;
    }

    public String getRescueknowledgeId() {
        return rescueknowledgeId;
    }

    public void setRescueknowledgeId(String rescueknowledgeId) {
        this.rescueknowledgeId = rescueknowledgeId;
    }
}
