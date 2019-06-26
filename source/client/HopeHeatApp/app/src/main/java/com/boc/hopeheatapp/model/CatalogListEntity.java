package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author dwl
 * @date 2018/5/28.
 */
public class CatalogListEntity {
    @Expose
    @SerializedName("totalCount")
    private int totalCount;

    @Expose
    @SerializedName("list")
    private List<ChannelEntity> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ChannelEntity> getList() {
        return list;
    }

    public void setList(List<ChannelEntity> list) {
        this.list = list;
    }
}

