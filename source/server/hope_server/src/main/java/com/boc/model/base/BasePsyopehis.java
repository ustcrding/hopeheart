package com.boc.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePsyopehis<M extends BasePsyopehis<M>> extends Model<M> implements IBean {

	/**
	 * 操作流水号
	 */
	public M setOperTraceno(java.lang.String operTraceno) {
		set("oper_traceno", operTraceno);
		return (M)this;
	}
	
	/**
	 * 操作流水号
	 */
	public java.lang.String getOperTraceno() {
		return getStr("oper_traceno");
	}

	/**
	 * 操作时间
	 */
	public M setOperaTime(java.lang.String operaTime) {
		set("opera_time", operaTime);
		return (M)this;
	}
	
	/**
	 * 操作时间
	 */
	public java.lang.String getOperaTime() {
		return getStr("opera_time");
	}

	/**
	 * "操作类型1－受灾群众登记2－救援知识查询3-心理知识查询4-心理测评5-心理咨询（受灾群众）6-心理辅导（医师/志愿者）7-登录8-注销"
	 */
	public M setOperaFlag(java.lang.String operaFlag) {
		set("opera_flag", operaFlag);
		return (M)this;
	}
	
	/**
	 * "操作类型1－受灾群众登记2－救援知识查询3-心理知识查询4-心理测评5-心理咨询（受灾群众）6-心理辅导（医师/志愿者）7-登录8-注销"
	 */
	public java.lang.String getOperaFlag() {
		return getStr("opera_flag");
	}

	/**
	 * "操作发起方V-受灾群众D-医师G-志愿者"
	 */
	public M setOperaType(java.lang.String operaType) {
		set("opera_type", operaType);
		return (M)this;
	}
	
	/**
	 * "操作发起方V-受灾群众D-医师G-志愿者"
	 */
	public java.lang.String getOperaType() {
		return getStr("opera_type");
	}

	/**
	 * "操作结果0-交易成功1-交易失败"
	 */
	public M setOperaEsult(java.lang.String operaEsult) {
		set("opera_esult", operaEsult);
		return (M)this;
	}
	
	/**
	 * "操作结果0-交易成功1-交易失败"
	 */
	public java.lang.String getOperaEsult() {
		return getStr("opera_esult");
	}

	/**
	 * 操作备注
	 */
	public M setOperaRemark(java.lang.String operaRemark) {
		set("opera_remark", operaRemark);
		return (M)this;
	}
	
	/**
	 * 操作备注
	 */
	public java.lang.String getOperaRemark() {
		return getStr("opera_remark");
	}

	/**
	 * 备用1
	 */
	public M setReserved1(java.lang.String reserved1) {
		set("reserved1", reserved1);
		return (M)this;
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
		return (M)this;
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
		return (M)this;
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
		return (M)this;
	}
	
	/**
	 * 备用4
	 */
	public java.lang.String getReserved4() {
		return getStr("reserved4");
	}

}
