package com.boc.hopeheatapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 答案项实体类
 *
 * @author dwl
 * @date 2018/1/23.
 */
public class KeywordItem implements Parcelable {

    @Expose
    @SerializedName("freq")
    private int freq;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("score")
    private double score;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.freq);
        dest.writeString(this.name);
        dest.writeDouble(this.score);
    }

    public KeywordItem() {
    }

    protected KeywordItem(Parcel in) {
        this.freq = in.readInt();
        this.name = in.readString();
        this.score = in.readDouble();
    }

    public static final Parcelable.Creator<KeywordItem> CREATOR = new Parcelable.Creator<KeywordItem>() {
        @Override
        public KeywordItem createFromParcel(Parcel source) {
            return new KeywordItem(source);
        }

        @Override
        public KeywordItem[] newArray(int size) {
            return new KeywordItem[size];
        }
    };

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
