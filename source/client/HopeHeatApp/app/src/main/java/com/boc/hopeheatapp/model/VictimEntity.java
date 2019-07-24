package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by zpwu3 on 2019-07-24.
 */
public class VictimEntity {
    @Expose
    @SerializedName("victimName")
    private String victimName;

    @Expose
    @SerializedName("victimGender")
    private String victimGender;

    @Expose
    @SerializedName("victimId")
    private String victimId;

    @Expose
    @SerializedName("victimAge")
    private int victimAge;

    @Expose
    @SerializedName("victimDate")
    private int victimDate;

    @Expose
    @SerializedName("victimCerType")
    private int victimCerType;

    @Expose
    @SerializedName("victimPhone")
    private int victimPhone;

    @Expose
    @SerializedName("addressCode")
    private int addressCode;

    @Expose
    @SerializedName("addressDetail")
    private int addressDetail;

    @Expose
    @SerializedName("added")
    private int added;

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getVictimGender() {
        return victimGender;
    }

    public void setVictimGender(String victimGender) {
        this.victimGender = victimGender;
    }

    public String getVictimId() {
        return victimId;
    }

    public void setVictimId(String victimId) {
        this.victimId = victimId;
    }

    public int getVictimAge() {
        return victimAge;
    }

    public void setVictimAge(int victimAge) {
        this.victimAge = victimAge;
    }

    public int getVictimDate() {
        return victimDate;
    }

    public void setVictimDate(int victimDate) {
        this.victimDate = victimDate;
    }

    public int getVictimCerType() {
        return victimCerType;
    }

    public void setVictimCerType(int victimCerType) {
        this.victimCerType = victimCerType;
    }

    public int getVictimPhone() {
        return victimPhone;
    }

    public void setVictimPhone(int victimPhone) {
        this.victimPhone = victimPhone;
    }

    public int getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(int addressCode) {
        this.addressCode = addressCode;
    }

    public int getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(int addressDetail) {
        this.addressDetail = addressDetail;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }
}
