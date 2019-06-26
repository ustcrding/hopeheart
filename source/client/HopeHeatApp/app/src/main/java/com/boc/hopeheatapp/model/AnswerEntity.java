package com.boc.hopeheatapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 答案数据实体类
 *
 * @author qsy
 * @date 2018/02/01.
 */
public class AnswerEntity implements Parcelable {

    // 内容的类型
    // 寒暄
    public static final int CONTENT_TYPE_GREET = 1;

    // 知识库
    public static final int CONTENT_TYPE_KNOWLEDGE = 2;

    // 语音导航
    public static final int CONTENT_TYPE_VOICE_NAVE = 3;


    @Expose
    @SerializedName("keywords")
    private KeywordEntity keywords;

    @Expose
    @SerializedName("list")
    private List<AnswerItem> list;

    @Expose
    @SerializedName("sessionId")
    private String sessionId;

    @Expose
    @SerializedName("totalCount")
    private int totalCount;


    // 内容类型， 1: 寒暄，2: 知识库
    @Expose
    @SerializedName("contentType")
    private int contentType;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.keywords);
        dest.writeString(this.sessionId);
        dest.writeTypedList(this.list);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.contentType);
    }

    public AnswerEntity() {
    }

    protected AnswerEntity(Parcel in) {
        this.keywords = in.readParcelable(KeywordEntity.class.getClass().getClassLoader());
        this.sessionId = in.readString();
        List<AnswerItem> list1 = new ArrayList<AnswerItem>();
        in.readTypedList(list1, AnswerItem.CREATOR);
        this.list = list1;
        this.totalCount = in.readInt();
        contentType = in.readInt();
    }

    public static final Parcelable.Creator<AnswerEntity> CREATOR = new Parcelable.Creator<AnswerEntity>() {
        @Override
        public AnswerEntity createFromParcel(Parcel source) {
            return new AnswerEntity(source);
        }

        @Override
        public AnswerEntity[] newArray(int size) {
            return new AnswerEntity[size];
        }
    };

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public KeywordEntity getKeywords() {
        return keywords;
    }

    public void setKeywords(KeywordEntity keywords) {
        this.keywords = keywords;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<AnswerItem> getList() {
        return list;
    }

    public void setList(List<AnswerItem> list) {
        this.list = list;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
