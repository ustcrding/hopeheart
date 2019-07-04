package com.boc.hopeheatapp.model;

import com.boc.hopeheatapp.util.string.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 用户信息实体类
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class UserEntity {
    public static final int PATIENT = 0;
    public static final int DOCTOR = 1;

    @Expose
    @SerializedName("id")
    private int userId;

    @Expose
    @SerializedName("user_name")
    private String username;

    @Expose
    @SerializedName("user_password")
    private String pwd;

    @Expose
    @SerializedName("auth_id")
    private String authId;

    @Expose
    @SerializedName("group_id")
    private String groupId;

    @Expose
    @SerializedName("role_id")
    private String roleId;

    @Expose
    @SerializedName("recordDate")
    private String recordDate;

    @Expose
    @SerializedName("certificateCode")
    private String certificateCode;

    @Expose
    @SerializedName("city")
    private String city;

    @Expose
    @SerializedName("sex")
    private String sex;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("identityCode")
    private String identityCode;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("province")
    private String province;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("deviceCode")
    private String deviceCode;

    @Expose
    @SerializedName("company")
    private String company;

    @Expose
    @SerializedName("identityType")
    private String identityType;

    // 1:需要晚上个人信息，0：已完善
    @Expose
    @SerializedName("preserve")
    private String preserve;

    public static final String TYPE_DOCTOR = "1";
    // 1:受灾群众，0：医生
    @Expose
    @SerializedName("roleType")
    private String roleType;

    public UserEntity() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean hasAuthId() {
        return StringUtil.isNotBlank(authId);
    }

    public String getRoleId() {
//        if (StringUtil.equals(username, "宗宇")) {
//            roleId = "1";
//        } else if (StringUtil.equals(username, "ding")) {
//            roleId = "5";
//        } else {
//            roleId = "2";
//        }

        // 防止免登陆用户没有roleId
        if (StringUtil.isEmpty(roleId)) {
            roleId = "2";
        }
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String device_code) {
        this.deviceCode = device_code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getPreserve() {
        return preserve;
    }

    public void setPreserve(String preserve) {
        this.preserve = preserve;
    }
}
