package com.boc.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseVolunteerinfo<M extends BaseVolunteerinfo<M>> extends Model<M> implements IBean {

	/**
	 * 志愿者ID
	 */
	public M setVolunteerId(java.lang.String volunteerId) {
		set("volunteer_id", volunteerId);
		return (M)this;
	}
	
	/**
	 * 志愿者ID
	 */
	public java.lang.String getVolunteerId() {
		return getStr("volunteer_id");
	}

	/**
	 * 志愿者姓名
	 */
	public M setVolunteerName(java.lang.String volunteerName) {
		set("volunteer_name", volunteerName);
		return (M)this;
	}
	
	/**
	 * 志愿者姓名
	 */
	public java.lang.String getVolunteerName() {
		return getStr("volunteer_name");
	}

	/**
	 * 志愿者证件类型
	 */
	public M setVolunCerttype(java.lang.String volunCerttype) {
		set("volun_certtype", volunCerttype);
		return (M)this;
	}
	
	/**
	 * 志愿者证件类型
	 */
	public java.lang.String getVolunCerttype() {
		return getStr("volun_certtype");
	}

	/**
	 * 志愿者证件号码
	 */
	public M setVolunCertno(java.lang.String volunCertno) {
		set("volun_certno", volunCertno);
		return (M)this;
	}
	
	/**
	 * 志愿者证件号码
	 */
	public java.lang.String getVolunCertno() {
		return getStr("volun_certno");
	}

	/**
	 * 志愿者所在地区
	 */
	public M setVolunAddress(java.lang.String volunAddress) {
		set("volun_address", volunAddress);
		return (M)this;
	}
	
	/**
	 * 志愿者所在地区
	 */
	public java.lang.String getVolunAddress() {
		return getStr("volun_address");
	}

	/**
	 * 志愿者所在单位
	 */
	public M setPlatform(java.lang.String platform) {
		set("platform", platform);
		return (M)this;
	}
	
	/**
	 * 志愿者所在单位
	 */
	public java.lang.String getPlatform() {
		return getStr("platform");
	}

	/**
	 * 志愿者掌握心理知识大类
	 */
	public M setPsyledgeType(java.lang.String psyledgeType) {
		set("psyledge_type", psyledgeType);
		return (M)this;
	}
	
	/**
	 * 志愿者掌握心理知识大类
	 */
	public java.lang.String getPsyledgeType() {
		return getStr("psyledge_type");
	}

	/**
	 * 志愿者掌握心理知识小类
	 */
	public M setPsyledgeSubtype(java.lang.String psyledgeSubtype) {
		set("psyledge_subtype", psyledgeSubtype);
		return (M)this;
	}
	
	/**
	 * 志愿者掌握心理知识小类
	 */
	public java.lang.String getPsyledgeSubtype() {
		return getStr("psyledge_subtype");
	}

	/**
	 * 志愿者掌握心理测评知识大类
	 */
	public M setPsytestsType(java.lang.String psytestsType) {
		set("psytests_type", psytestsType);
		return (M)this;
	}
	
	/**
	 * 志愿者掌握心理测评知识大类
	 */
	public java.lang.String getPsytestsType() {
		return getStr("psytests_type");
	}

	/**
	 * 志愿者掌握心理知识小类
	 */
	public M setPsytestsSubtype(java.lang.String psytestsSubtype) {
		set("psytests_subtype", psytestsSubtype);
		return (M)this;
	}
	
	/**
	 * 志愿者掌握心理知识小类
	 */
	public java.lang.String getPsytestsSubtype() {
		return getStr("psytests_subtype");
	}

	/**
	 * "志愿辅导类型O-线上D-线下A-线上线下"
	 */
	public M setVoluntaryType(java.lang.String voluntaryType) {
		set("voluntary_type", voluntaryType);
		return (M)this;
	}
	
	/**
	 * "志愿辅导类型O-线上D-线下A-线上线下"
	 */
	public java.lang.String getVoluntaryType() {
		return getStr("voluntary_type");
	}

	/**
	 * "当前是否参与辅导Y-是N-否"
	 */
	public M setCounseling(java.lang.String counseling) {
		set("counseling", counseling);
		return (M)this;
	}
	
	/**
	 * "当前是否参与辅导Y-是N-否"
	 */
	public java.lang.String getCounseling() {
		return getStr("counseling");
	}

	/**
	 * 当前辅导数（线上用）
	 */
	public M setCounselingnum(java.lang.String counselingnum) {
		set("counselingnum", counselingnum);
		return (M)this;
	}
	
	/**
	 * 当前辅导数（线上用）
	 */
	public java.lang.String getCounselingnum() {
		return getStr("counselingnum");
	}

	/**
	 * 标记医师ID
	 */
	public M setDoctorId(java.lang.String doctorId) {
		set("doctor_id", doctorId);
		return (M)this;
	}
	
	/**
	 * 标记医师ID
	 */
	public java.lang.String getDoctorId() {
		return getStr("doctor_id");
	}

	/**
	 * 备注1
	 */
	public M setReserved1(java.lang.String reserved1) {
		set("reserved1", reserved1);
		return (M)this;
	}
	
	/**
	 * 备注1
	 */
	public java.lang.String getReserved1() {
		return getStr("reserved1");
	}

	/**
	 * 备注2
	 */
	public M setReserved2(java.lang.String reserved2) {
		set("reserved2", reserved2);
		return (M)this;
	}
	
	/**
	 * 备注2
	 */
	public java.lang.String getReserved2() {
		return getStr("reserved2");
	}

	/**
	 * 备注3
	 */
	public M setReserved3(java.lang.String reserved3) {
		set("reserved3", reserved3);
		return (M)this;
	}
	
	/**
	 * 备注3
	 */
	public java.lang.String getReserved3() {
		return getStr("reserved3");
	}

	/**
	 * 备注4
	 */
	public M setReserved4(java.lang.String reserved4) {
		set("reserved4", reserved4);
		return (M)this;
	}
	
	/**
	 * 备注4
	 */
	public java.lang.String getReserved4() {
		return getStr("reserved4");
	}

}
