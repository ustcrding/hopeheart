package com.bocsoft.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUploadInfo <M extends BaseUploadInfo<M>> extends Model<M> implements IBean {
    public M setUploadNo(String uploadNo) {
        set("uploadno", uploadNo);
        return (M)this;
    }

    public String getUploadNo() {
        return getStr("uploadno");
    }
    public M setUploadTime(String uploadTime) {
        set("uploadtime", uploadTime);
        return (M)this;
    }

    public String getUploadTime() {
        return getStr("uploadtime");
    }

    public M setOperId(String operId) {
        set("operid", operId);
        return (M)this;
    }

    public String getOperId() {
        return getStr("operid");
    }

    public M setInfoType(String infoType) {
        set("infotype", infoType);
        return (M)this;
    }

    public String getInfoType() {
        return getStr("infotype");
    }

    public M setAuthorId(String authorId) {
        set("authorid", authorId);
        return (M)this;
    }

    public String getAuthorId() {
        return getStr("authorid");
    }

    public M setAuthorState(String authorState) {
        set("authorstate", authorState);
        return (M)this;
    }

    public String getAuthorState() {
        return getStr("authorstate");
    }

    public M setReason(String reason) {
        set("reason", reason);
        return (M)this;
    }

    public String getReason() {
        return getStr("reason");
    }

    public M setReserved2(String reserved2) {
        set("reserved2", reserved2);
        return (M)this;
    }

    public String getReserved2() {
        return getStr("reserved2");
    }

    public M setReserved3(String reserved3) {
        set("reserved3", reserved3);
        return (M)this;
    }

    public String getReserved3() {
        return getStr("reserved3");
    }
    public M setReserved4(String reserved4) {
        set("reserved4", reserved4);
        return (M)this;
    }

    public String getReserved4() {
        return getStr("reserved4");
    }
    public M setReserved1(String reserved1) {
        set("reserved1", reserved1);
        return (M)this;
    }

    public String getReserved1() {
        return getStr("reserved1");
    }

    public M setTotalPage(int totalPage) {
        set("totalPage", totalPage);
        return (M)this;
    }

    public int getTotalPage() {
        return getInt("totalPage");
    }

    public M setId(String id) {
        set("id", id);
        return (M)this;
    }

    public String getId() {
        return getStr("id");
    }

    public M setName(String name) {
        set("name", name);
        return (M)this;
    }

    public String getName() {
        return getStr("name");
    }

    public M setCertType(String certType) {
        set("certType", certType);
        return (M)this;
    }

    public String getCertType() {
        return getStr("certType");
    }

    public M setCertNo(String certNo) {
        set("certNo", certNo);
        return (M)this;
    }

    public String getCertNo() {
        return getStr("certNo");
    }

    public M setAddress(String address) {
        set("address", address);
        return (M)this;
    }

    public String getAddress() {
        return getStr("address");
    }

    public M setPlatform(String platform) {
        set("platform", platform);
        return (M)this;
    }

    public String getPlatform() {
        return getStr("platform");
    }
}
