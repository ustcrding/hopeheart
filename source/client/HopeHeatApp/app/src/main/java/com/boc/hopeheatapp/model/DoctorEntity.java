package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-04.
 */
public class DoctorEntity {
    @Expose
    @SerializedName("doctorName")
    private String doctorName;

    @Expose
    @SerializedName("doctorId")
    private String doctorId;

    @Expose
    @SerializedName("doctorResume")
    private String doctorResume;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorResume() {
        return doctorResume;
    }

    public void setDoctorResume(String doctorResume) {
        this.doctorResume = doctorResume;
    }
}
