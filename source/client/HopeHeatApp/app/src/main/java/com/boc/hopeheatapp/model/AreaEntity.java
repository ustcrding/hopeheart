package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zpwu3 on 2019-07-04.
 */
public class AreaEntity {
    @Expose
    @SerializedName("addressCode")
    private String addressCode;

    @Expose
    @SerializedName("disasterType")
    private String disasterType;

    @Expose
    @SerializedName("cityState")
    private String cityState;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public String getCityState() {
        return cityState;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }
}
