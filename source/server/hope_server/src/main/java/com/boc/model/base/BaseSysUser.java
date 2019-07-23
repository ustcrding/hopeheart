package com.boc.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysUser<M extends BaseSysUser<M>> extends Model<M> implements IBean {

	/**
	 * 主键
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * 主键
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUsername(java.lang.String username) {
		set("username", username);
		return (M)this;
	}
	
	public java.lang.String getUsername() {
		return getStr("username");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}
	
	public java.lang.String getPassword() {
		return getStr("password");
	}

	public M setAvatar(java.lang.String avatar) {
		set("avatar", avatar);
		return (M)this;
	}
	
	public java.lang.String getAvatar() {
		return getStr("avatar");
	}

	public M setProvince(java.lang.String province) {
		set("province", province);
		return (M)this;
	}
	
	public java.lang.String getProvince() {
		return getStr("province");
	}

	public M setCity(java.lang.String city) {
		set("city", city);
		return (M)this;
	}
	
	public java.lang.String getCity() {
		return getStr("city");
	}

	public M setAddress(java.lang.String address) {
		set("address", address);
		return (M)this;
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

	public M setSex(java.lang.String sex) {
		set("sex", sex);
		return (M)this;
	}
	
	public java.lang.String getSex() {
		return getStr("sex");
	}

	public M setIdentityType(java.lang.String identityType) {
		set("identity_type", identityType);
		return (M)this;
	}
	
	public java.lang.String getIdentityType() {
		return getStr("identity_type");
	}

	public M setIdentityCode(java.lang.String identityCode) {
		set("identity_code", identityCode);
		return (M)this;
	}
	
	public java.lang.String getIdentityCode() {
		return getStr("identity_code");
	}

	public M setPhone(java.lang.String phone) {
		set("phone", phone);
		return (M)this;
	}
	
	public java.lang.String getPhone() {
		return getStr("phone");
	}

	public M setRecordDate(java.util.Date recordDate) {
		set("record_date", recordDate);
		return (M)this;
	}
	
	public java.util.Date getRecordDate() {
		return get("record_date");
	}

	public M setCompany(java.lang.String company) {
		set("company", company);
		return (M)this;
	}
	
	public java.lang.String getCompany() {
		return getStr("company");
	}

	public M setCertificateCode(java.lang.String certificateCode) {
		set("certificate_code", certificateCode);
		return (M)this;
	}
	
	public java.lang.String getCertificateCode() {
		return getStr("certificate_code");
	}

	public M setDeviceCode(java.lang.String deviceCode) {
		set("device_code", deviceCode);
		return (M)this;
	}
	
	public java.lang.String getDeviceCode() {
		return getStr("device_code");
	}

	public M setRoleType(java.lang.String roleType) {
		set("role_type", roleType);
		return (M)this;
	}
	
	public java.lang.String getRoleType() {
		return getStr("role_type");
	}
	
	public M setRoleId(java.lang.String roleId) {
		set("role_id", roleId);
		return (M)this;
	}
	
	public java.lang.String getRoleId() {
		return getStr("role_id");
	}
	
	public M setPreserve(java.lang.String preserve) {
		set("preserve", preserve);
		return (M)this;
	}
	
	public java.lang.String getPreserve() {
		return getStr("preserve");
	}

}