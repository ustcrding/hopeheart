package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 热门问题列表实体类
 *
 * @author dwl
 * @date 2018/2/26.
 */
public class HotQuestionListEntity {
    @Expose
    @SerializedName("totalCount")
    private int totalCount;

    @Expose
    @SerializedName("list")
    private List<HotQuestionEntity> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<HotQuestionEntity> getList() {
        return list;
    }

    public void setList(List<HotQuestionEntity> list) {
        this.list = list;
    }
}
