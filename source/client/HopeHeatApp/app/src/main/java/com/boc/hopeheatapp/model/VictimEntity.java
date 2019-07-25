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
    @SerializedName("testioinDate")
    private String victimDate;

    @Expose
    @SerializedName("victimCertype")
    private String victimCerType;

    @Expose
    @SerializedName("victimPhone")
    private String victimPhone;

    @Expose
    @SerializedName("addressCode")
    private String addressCode;

    @Expose
    @SerializedName("addressDetail")
    private String addressDetail;

    @Expose
    @SerializedName("added")
    private String added;

    private boolean isChecked;

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

    public String getVictimDate() {
        return victimDate;
    }

    public void setVictimDate(String victimDate) {
        this.victimDate = victimDate;
    }

    public String getVictimCerType() {
        return victimCerType;
    }

    public void setVictimCerType(String victimCerType) {
        this.victimCerType = victimCerType;
    }

    public String getVictimPhone() {
        return victimPhone;
    }

    public void setVictimPhone(String victimPhone) {
        this.victimPhone = victimPhone;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
