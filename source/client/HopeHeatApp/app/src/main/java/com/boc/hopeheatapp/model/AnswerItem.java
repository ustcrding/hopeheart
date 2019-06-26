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
public class AnswerItem implements Parcelable {

    public static final String SEPRATOR = "\\|";

    // 文本
    public static final String TYPE_TEXT = "0";

    // 图片
    public static final String TYPE_PICTURE = "1";

    // 网页
    public static final String TYPE_HTML = "2";

    // 富文本
    public static final String TYPE_RICHTEXT = "3";

    @Expose
    @SerializedName("question")
    private String question;

    @Expose
    @SerializedName("questionId")
    private String questionId;

    @Expose
    @SerializedName("answer")
    private String answer;

    @Expose
    @SerializedName("itemId")
    private String itemId;

    @Expose
    @SerializedName("answerUrl")
    private String answerUrl;

    /**
     * 0:文本, 1:图片, 2:网页, 3:富文本
     */
    @Expose
    @SerializedName("answerType")
    private String answerType;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeString(this.questionId);
        dest.writeString(this.answer);
        dest.writeString(this.itemId);
        dest.writeString(this.answerUrl);
        dest.writeString(this.answerType);
    }

    public AnswerItem() {
    }

    protected AnswerItem(Parcel in) {
        this.question = in.readString();
        this.questionId = in.readString();
        this.answer = in.readString();
        this.itemId = in.readString();
        this.answerUrl = in.readString();
        this.answerType = in.readString();
    }

    public static final Parcelable.Creator<AnswerItem> CREATOR = new Parcelable.Creator<AnswerItem>() {
        @Override
        public AnswerItem createFromParcel(Parcel source) {
            return new AnswerItem(source);
        }

        @Override
        public AnswerItem[] newArray(int size) {
            return new AnswerItem[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAnswerUrl() {
        return answerUrl;
    }

    public void setAnswerUrl(String answerUrl) {
        this.answerUrl = answerUrl;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }
}
