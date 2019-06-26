package com.boc.hopeheatapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2018/5/19.
 */
public class ChannelEntity implements Parcelable {

    /**
     * 频道id
     */
    @Expose
    @SerializedName("catalogId")
    private String id;

    /**
     * 频道名称
     */
    @Expose
    @SerializedName("catalogName")
    private String name;

    /**
     * 频道图片的url
     */
    @Expose
    @SerializedName("icon")
    private String iconUrl;

    /**
     * 频道类型，待定
     */
    @Expose
    @SerializedName("type")
    private String type;

    /**
     * 打开的url
     */
    @Expose
    @SerializedName("openUrl")
    private String openUrl;

    /**
     * 知识库目录
     */
    public static final String TYPE_CKB_CATE = "1";

    /**
     * 打开url
     */
    public static final String TYPE_OPEN_URL = "2";

    /**
     * 本地功能
     */
    public static final String TYPE_NATIVE = "3";

    /**
     * 查看更多
     */
    public static final String TYPE_MORE = "-1";


    /**
     * 空channel
     */
    public static final String TYPE_EMPTY = "-2";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.iconUrl);
        dest.writeString(this.type);
        dest.writeString(this.openUrl);
    }

    public ChannelEntity() {
    }

    protected ChannelEntity(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.iconUrl = in.readString();
        this.type = in.readString();
        this.openUrl = in.readString();
    }

    public static final Parcelable.Creator<ChannelEntity> CREATOR = new Parcelable.Creator<ChannelEntity>() {
        @Override
        public ChannelEntity createFromParcel(Parcel source) {
            return new ChannelEntity(source);
        }

        @Override
        public ChannelEntity[] newArray(int size) {
            return new ChannelEntity[size];
        }
    };
}
