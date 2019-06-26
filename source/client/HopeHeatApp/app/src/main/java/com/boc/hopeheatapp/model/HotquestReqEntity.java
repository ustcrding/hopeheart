package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 热门问题请求类
 *
 * @author dwl
 * @date 2018/12/14.
 */
public class HotquestReqEntity extends BaseReqEntity {

    @Expose
    @SerializedName("catalogId")
    private String catalogId;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
}
