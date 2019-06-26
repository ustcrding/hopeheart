package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author dwl
 * @date 2018/10/29.
 */
public class MarkReqEntity extends BaseReqEntity {

    @Expose
    @SerializedName("sessionId")
    private String sessionId;

    @Expose
    @SerializedName("itemId")
    private String itemId;

    @Expose
    @SerializedName("flag")
    private int flag;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
