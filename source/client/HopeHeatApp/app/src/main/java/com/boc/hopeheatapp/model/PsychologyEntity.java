package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class PsychologyEntity {
    @Expose
    @SerializedName("psyknowledgeId")
    private String psyknowledgeId;

    @Expose
    @SerializedName("psytestsId")
    private String pystestsId;

    @Expose
    @SerializedName("psyledgeDetail")
    private String rescueKnowledge;

    @Expose
    @SerializedName("psyledgeType")
    private String rescueType;

    @Expose
    @SerializedName("psyledgeSubtype")
    private String rescueSubType;



    public String getPsyknowledgeId() {
        return psyknowledgeId;
    }

    public void setPsyknowledgeId(String psyknowledgeId) {
        this.psyknowledgeId = psyknowledgeId;
    }

    public String getPystestsId() {
        return pystestsId;
    }

    public void setPystestsId(String pystestsId) {
        this.pystestsId = pystestsId;
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

}
