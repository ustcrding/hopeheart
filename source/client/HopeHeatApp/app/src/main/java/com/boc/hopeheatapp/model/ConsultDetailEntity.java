package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class ConsultDetailEntity {
    @Expose
    @SerializedName("addressCode")
    private String addressCode;

    @Expose
    @SerializedName("counseling")
    private String counseling;

    @Expose
    @SerializedName("testjoinTime")
    private String testjoinTime;

    @Expose
    @SerializedName("victimtestId")
    private String victimtestId;

    @Expose
    @SerializedName("volunteerName")
    private String volunteerName;

    @Expose
    @SerializedName("satisficing")
    private String satisficing;

    @Expose
    @SerializedName("testsLevel")
    private String testsLevel;

    @Expose
    @SerializedName("platform")
    private String platform;

    @Expose
    @SerializedName("testioinDate")
    private String testioinDate;

    @Expose
    @SerializedName("doctorName")
    private String doctorName;

    @Expose
    @SerializedName("certificate")
    private String certificate;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getCounseling() {
        return counseling;
    }

    public void setCounseling(String counseling) {
        this.counseling = counseling;
    }

    public String getTestjoinTime() {
        return testjoinTime;
    }

    public void setTestjoinTime(String testjoinTime) {
        this.testjoinTime = testjoinTime;
    }

    public String getVictimtestId() {
        return victimtestId;
    }

    public void setVictimtestId(String victimtestId) {
        this.victimtestId = victimtestId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getSatisficing() {
        return satisficing;
    }

    public void setSatisficing(String satisficing) {
        this.satisficing = satisficing;
    }

    public String getTestsLevel() {
        return testsLevel;
    }

    public void setTestsLevel(String testsLevel) {
        this.testsLevel = testsLevel;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTestioinDate() {
        return testioinDate;
    }

    public void setTestioinDate(String testioinDate) {
        this.testioinDate = testioinDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
