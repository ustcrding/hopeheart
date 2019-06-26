package com.boc.hopeheatapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 答案项实体类
 *
 * @author dwl
 * @date 2018/1/23.
 */
public class KeywordEntity implements Parcelable {

    @Expose
    @SerializedName("list")
    private List<KeywordItem> list;

    @Expose
    @SerializedName("totalCount")
    private int totalCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeInt(this.totalCount);
    }

    public KeywordEntity() {
    }

    protected KeywordEntity(Parcel in) {
        List<KeywordItem> list1 = new ArrayList<KeywordItem>();
        in.readTypedList(list1, KeywordItem.CREATOR);
        this.list = list1;
        this.totalCount = in.readInt();
    }

    public static final Parcelable.Creator<KeywordEntity> CREATOR = new Parcelable.Creator<KeywordEntity>() {
        @Override
        public KeywordEntity createFromParcel(Parcel source) {
            return new KeywordEntity(source);
        }

        @Override
        public KeywordEntity[] newArray(int size) {
            return new KeywordEntity[size];
        }
    };

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<KeywordItem> getList() {
        return list;
    }

    public void setList(List<KeywordItem> list) {
        this.list = list;
    }
}
