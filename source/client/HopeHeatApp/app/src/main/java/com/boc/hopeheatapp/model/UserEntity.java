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
    @Expose
    @SerializedName("user_id")
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
}
