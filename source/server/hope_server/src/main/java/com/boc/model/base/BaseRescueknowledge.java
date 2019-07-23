package com.boc.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRescueknowledge<M extends BaseRescueknowledge<M>> extends Model<M> implements IBean {

	/**
	 * 救援知识库号
	 */
	public M setRescueknowledgeId(java.lang.String rescueknowledgeId) {
		set("rescueknowledge_id", rescueknowledgeId);
		return (M)this;
	}
	
	/**
	 * 救援知识库号
	 */
	public java.lang.String getRescueknowledgeId() {
		return getStr("rescueknowledge_id");
	}

	/**
	 * 救援知识大类
	 */
	public M setRescueType(java.lang.String rescueType) {
		set("rescue_type", rescueType);
		return (M)this;
	}
	
	/**
	 * 救援知识大类
	 */
	public java.lang.String getRescueType() {
		return getStr("rescue_type");
	}

	/**
	 * 救援知识小类
	 */
	public M setRescueSubtype(java.lang.String rescueSubtype) {
		set("rescue_subtype", rescueSubtype);
		return (M)this;
	}
	
	/**
	 * 救援知识小类
	 */
	public java.lang.String getRescueSubtype() {
		return getStr("rescue_subtype");
	}

	/**
	 * 救援知识详情
	 */
	public M setRescueKnowledge(java.lang.String rescueKnowledge) {
		set("rescue_knowledge", rescueKnowledge);
		return (M)this;
	}
	
	/**
	 * 救援知识详情
	 */
	public java.lang.String getRescueKnowledge() {
		return getStr("rescue_knowledge");
	}

	/**
	 * 可关联心理知识库号
	 */
	public M setPsyknowledgeId(java.lang.String psyknowledgeId) {
		set("psyknowledge_id", psyknowledgeId);
		return (M)this;
	}
	
	/**
	 * 可关联心理知识库号
	 */
	public java.lang.String getPsyknowledgeId() {
		return getStr("psyknowledge_id");
	}

	/**
	 * 可关联心理测评知识库号
	 */
	public M setPsytestsId(java.lang.String psytestsId) {
		set("psytests_id", psytestsId);
		return (M)this;
	}
	
	/**
	 * 可关联心理测评知识库号
	 */
	public java.lang.String getPsytestsId() {
		return getStr("psytests_id");
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