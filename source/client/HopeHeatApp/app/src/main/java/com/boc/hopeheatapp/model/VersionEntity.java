package com.boc.hopeheatapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 版本信息实体类
 *
 * @author dwl
 * @date 2018/1/23.
 */
public class VersionEntity implements Parcelable {

    @Expose
    @SerializedName("version_code")
    private int versionCode;

    @Expose
    @SerializedName("version_name")
    private String versionName;

    @Expose
    @SerializedName("version_comment")
    private String versionComment;

    @Expose
    @SerializedName("update_date")
    private String updateDate;

    @Expose
    @SerializedName("version_url")
    private String versionUrl;

    @Expose
    @SerializedName("force_update")
    private int forceUpdate;

    @Expose
    @SerializedName("totalSize")
    private int totalSize;

    private static final int UPDATE_FORCE = 1;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public boolean isForceUpdate() {
        return forceUpdate == UPDATE_FORCE;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeString(this.versionComment);
        dest.writeString(this.updateDate);
        dest.writeString(this.versionUrl);
        dest.writeInt(this.forceUpdate);
        dest.writeInt(this.totalSize);
    }

    public VersionEntity() {
    }

    protected VersionEntity(Parcel in) {
        this.versionCode = in.readInt();
        this.versionName = in.readString();
        this.versionComment = in.readString();
        this.updateDate = in.readString();
        this.versionUrl = in.readString();
        this.forceUpdate = in.readInt();
        this.totalSize = in.readInt();
    }

    public static final Parcelable.Creator<VersionEntity> CREATOR = new Parcelable.Creator<VersionEntity>() {
        @Override
        public VersionEntity createFromParcel(Parcel source) {
            return new VersionEntity(source);
        }

        @Override
        public VersionEntity[] newArray(int size) {
            return new VersionEntity[size];
        }
    };
}
