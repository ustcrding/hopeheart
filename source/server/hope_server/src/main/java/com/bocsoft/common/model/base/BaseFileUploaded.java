package com.bocsoft.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseFileUploaded<M extends BaseFileUploaded<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setFileName(java.lang.String fileName) {
		set("file_name", fileName);
		return (M)this;
	}
	
	public java.lang.String getFileName() {
		return getStr("file_name");
	}

	public M setFileSize(java.lang.Long fileSize) {
		set("file_size", fileSize);
		return (M)this;
	}
	
	public java.lang.Long getFileSize() {
		return getLong("file_size");
	}

	public M setSavePath(java.lang.String savePath) {
		set("save_path", savePath);
		return (M)this;
	}
	
	public java.lang.String getSavePath() {
		return getStr("save_path");
	}

	public M setObjectId(java.lang.String objectId) {
		set("objectId", objectId);
		return (M)this;
	}
	
	public java.lang.String getObjectId() {
		return getStr("objectId");
	}



	public M setUrl(java.lang.String url) {
		set("url", url);
		return (M)this;
	}

	public java.lang.String getUrl() {
		return getStr("url");
	}

	public M setOperId(java.lang.String operId) {
		set("oper_id", operId);
		return (M)this;
	}

	public java.lang.String getOperId() {
		return getStr("oper_id");
	}

	public M setAuthorId(java.lang.String authorId) {
		set("author_id", authorId);
		return (M)this;
	}

	public java.lang.String getAuthorId() {
		return getStr("author_id");
	}

	public M setAuthorState(java.lang.String authorState) {
		set("author_state", authorState);
		return (M)this;
	}

	public java.lang.String getAuthorState() {
		return getStr("author_state");
	}

	public M setReason(java.lang.String reason) {
		set("reason", reason);
		return (M)this;
	}

	public java.lang.String getReason() {
		return getStr("reason");
	}


	/**
	 * 备用1
	 */
	public M setReserved1(java.lang.String reserved1) {
		set("reserved1", reserved1);
		return (M) this;
	}

	/**
	 * 备用1
	 */
	public java.lang.String getReserved1() {
		return getStr("reserved1");
	}

	/**
	 * 备用2
	 */
	public M setReserved2(java.lang.String reserved2) {
		set("reserved2", reserved2);
		return (M) this;
	}

	/**
	 * 备用2
	 */
	public java.lang.String getReserved2() {
		return getStr("reserved2");
	}

	/**
	 * 备用3
	 */
	public M setReserved3(java.lang.String reserved3) {
		set("reserved3", reserved3);
		return (M) this;
	}

	/**
	 * 备用3
	 */
	public java.lang.String getReserved3() {
		return getStr("reserved3");
	}

	/**
	 * 备用4
	 */
	public M setReserved4(java.lang.String reserved4) {
		set("reserved4", reserved4);
		return (M) this;
	}

	/**
	 * 备用4
	 */
	public java.lang.String getReserved4() {
		return getStr("reserved4");
	}

	public M setInfoType(String infoType) {
		set("info_type", infoType);
		return (M)this;
	}

	public String getInfoType() {
		return getStr("info_type");
	}

	public M setTotalPage(int totalPage) {
		set("totalPage", totalPage);
		return (M)this;
	}

	public int getTotalPage() {
		return getInt("totalPage");
	}

	public M setRoleId(String roleId) {
		set("roleid", roleId);
		return (M)this;
	}

	public String getRoleId() {
		return getStr("roleid");
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
