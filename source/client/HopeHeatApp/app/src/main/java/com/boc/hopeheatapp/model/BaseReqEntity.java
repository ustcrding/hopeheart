package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2018/10/29.
 */
public class BaseReqEntity {
    @Expose
    @SerializedName("appId")
    private String appId;

    @Expose
    @SerializedName("userId")
    private String userId;

    @Expose
    @SerializedName("userToken")
    private String userToken;

    @Expose
    @SerializedName("userName")
    private String userName;

    @Expose
    @SerializedName("roleId")
    private String roleId;

    @Expose
    @SerializedName("platform")
    private String platform;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
