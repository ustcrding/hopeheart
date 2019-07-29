package com.bocsoft.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDataDictionaryValue<M extends BaseDataDictionaryValue<M>> extends Model<M> implements IBean {

	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.String getId() {
		return getStr("id");
	}

	public M setValue(java.lang.String value) {
		set("value", value);
		return (M)this;
	}
	
	public java.lang.String getValue() {
		return getStr("value");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setOrderNum(java.lang.Integer orderNum) {
		set("order_num", orderNum);
		return (M)this;
	}
	
	public java.lang.Integer getOrderNum() {
		return getInt("order_num");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setDictionaryCode(java.lang.String dictionaryCode) {
		set("dictionary_code", dictionaryCode);
		return (M)this;
	}
	
	public java.lang.String getDictionaryCode() {
		return getStr("dictionary_code");
	}

}
