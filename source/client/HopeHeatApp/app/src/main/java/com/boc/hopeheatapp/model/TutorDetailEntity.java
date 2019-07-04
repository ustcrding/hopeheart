package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class TutorDetailEntity {
    @Expose
    @SerializedName("addressCode")
    private String addressCode;

    @Expose
    @SerializedName("victimTestId")
    private String victimtestId;

    @Expose
    @SerializedName("victimPhone")
    private String victimPhone;

    @Expose
    @SerializedName("satisficing")
    private String satisficing;

    @Expose
    @SerializedName("victimGender")
    private String victimGender;

    @Expose
    @SerializedName("testJoinDate")
    private String testJoinDate;

    @Expose
    @SerializedName("testJoinTime")
    private String testJoinTime;

    @Expose
    @SerializedName("addressDetail")
    private String addressDetail;

    @Expose
    @SerializedName("victimName")
    private String victimName;

    @Expose
    @SerializedName("victimCerType")
    private String victimCerType;

    @Expose
    @SerializedName("victimAddressCode")
    private String victimAddressCode;

    @Expose
    @SerializedName("testsLevel")
    private String testsLevel;

    @Expose
    @SerializedName("victimCerNo")
    private String victimCerNo;


    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getVictimtestId() {
        return victimtestId;
    }

    public void setVictimtestId(String victimtestId) {
        this.victimtestId = victimtestId;
    }

    public String getVictimPhone() {
        return victimPhone;
    }

    public void setVictimPhone(String victimPhone) {
        this.victimPhone = victimPhone;
    }

    public String getSatisficing() {
        return satisficing;
    }

    public void setSatisficing(String satisficing) {
        this.satisficing = satisficing;
    }

    public String getVictimGender() {
        return victimGender;
    }

    public void setVictimGender(String victimGender) {
        this.victimGender = victimGender;
    }

    public String getTestJoinTime() {
        return testJoinTime;
    }

    public void setTestJoinTime(String testJoinTime) {
        this.testJoinTime = testJoinTime;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getVictimCerType() {
        return victimCerType;
    }

    public void setVictimCerType(String victimCerType) {
        this.victimCerType = victimCerType;
    }

    public String getVictimAddressCode() {
        return victimAddressCode;
    }

    public void setVictimAddressCode(String victimAddressCode) {
        this.victimAddressCode = victimAddressCode;
    }

    public String getTestsLevel() {
        return testsLevel;
    }

    public void setTestsLevel(String testsLevel) {
        this.testsLevel = testsLevel;
    }

    public String getVictimCerNo() {
        return victimCerNo;
    }

    public void setVictimCerNo(String victimCerNo) {
        this.victimCerNo = victimCerNo;
    }

    public String getTestJoinDate() {
        return testJoinDate;
    }

    public void setTestJoinDate(String testJoinDate) {
        this.testJoinDate = testJoinDate;
    }
}
