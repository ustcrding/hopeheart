package com.boc.hopeheatapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 导航数据实体类
 *
 * @author qsy
 * @date 2018/02/08.
 */
public class NaviEntity implements Parcelable {

    @Expose
    @SerializedName("action")
    private String action;

    @Expose
    @SerializedName("menu")
    private String menu;

    @Expose
    @SerializedName("person")
    private String person;

    @Expose
    @SerializedName("time")
    private String time;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.action);
        dest.writeString(this.menu);
        dest.writeString(this.person);
        dest.writeString(this.time);
    }

    public NaviEntity() {
    }

    protected NaviEntity(Parcel in) {
        this.action = in.readString();
        this.menu = in.readString();
        this.person = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<NaviEntity> CREATOR = new Parcelable.Creator<NaviEntity>() {
        @Override
        public NaviEntity createFromParcel(Parcel source) {
            return new NaviEntity(source);
        }

        @Override
        public NaviEntity[] newArray(int size) {
            return new NaviEntity[size];
        }
    };

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
