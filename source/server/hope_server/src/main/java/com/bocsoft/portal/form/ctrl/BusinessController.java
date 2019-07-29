/**
 * Copyright Bocsoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.bocsoft.portal.form.ctrl;

import java.util.List;

import com.jfinal.aop.Inject;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.portal.form.service.FormViewService;
import com.bocsoft.portal.form.service.BusinessService;

/**
 * 处理在线表单开发的业务模块
 * @author bocsoft
 * @date 2019年3月14日  
 */
@ControllerBind(path="/portal/form/business")
public class BusinessController extends BaseController{
	/**
	 * 表前缀，只能处理这个前缀的表，降低风险
	 */
	private final String tableNameSpace="w_";

	@Inject
	BusinessService service;
	@Inject
	FormViewService formViewService;
	
	/**
	 * 业务模块首页
	 * 
	 * @author bocsoft
	 * @date 2019年3月21日
	 */
	public void index() {
		Record record=formViewService.findPk("code",getPara(0));		
		if("DEPLOYED".equals(record.get("status"))) {
			writeToHtml("index.html",record.get("template_view","")+"");		
		}else {
			writeToHtml("index.html","<h1><font color='red'>表单未发布，不能使用</font></h1>");					
		}
		render("index.html");
	}
	
	public void add() {
		Record record=formViewService.findPk("code",getPara("viewCode"));	
		if("DEPLOYED".equals(record.get("status"))) {
			writeToHtml("add.html",record.get("template_view","")+"");		
		}else {
			writeToHtml("add.html","<h1><font color='red'>表单未发布，不能使用</font></h1>");					
		}
		render("add.html");
	}
	
	public void edit() {
		Record record=formViewService.findPk("code",getPara("viewCode"));	;					
		if("DEPLOYED".equals(record.get("status"))) {
			writeToHtml("edit.html",record.get("template_view","")+"");		
		}else {
			writeToHtml("edit.html","<h1><font color='red'>表单未发布，不能使用</font></h1>");					
		}
		render("edit.html");
	}
	
	public void list() {
		Kv kv=getKv();
		kv.remove("pageNumber");
		kv.remove("pageSize");
		kv.remove("object");
		kv.remove("primaryKey");
		renderJson(service.queryPage(getParaToInt("pageNumber"), getParaToInt("pageSize"),tableNameSpace+getPara("object"),kv));
	}
	
	@SuppressWarnings("unchecked")
	public void save() {
		Record record=new Record().setColumns(getKv());
		//获取表对象和主键
		String table=record.get("object");
		if(table==null){
			renderJson(fail("object的值不能为空！"));
			return;
		}
		
		String primaryKey=record.get("primaryKey","id");
		record.set(primaryKey, createUUID());
		
		//移除不是表字段的参数
		record.remove("object","primaryKey");	
		boolean b=service.save(tableNameSpace+table,primaryKey, record);
		
		if(!b) {
			renderJson(fail());
			return;
		}
		
		renderJson(ok());
	}
	
	public void getModel() {
		List<Record> list=service.find(tableNameSpace+getPara("object"), Kv.by(getPara("primaryKey")+"=", getPara("id")));
		if(list.size()==0) {
			renderJson(fail());
			return;
		}
		renderJson(ok(list.get(0)));
	}
	
	@SuppressWarnings("unchecked")
	public void update() {
		Record record=new Record().setColumns(getKv());
		//获取表对象和主键
		String table=record.get("object");
		String primaryKey=record.get("primaryKey","id");
		//移除不是表字段的参数
		record.remove("object","primaryKey");	
		boolean b=service.update(tableNameSpace+table, primaryKey, record);
		if(!b) {
			renderJson(fail());
			return;
		}
		renderJson(ok());
	}
	
	public void delete() {		
		boolean b=service.deleteByIds(tableNameSpace+getPara("object"), getPara("primaryKey","id"), getIds());
		if(!b) {
			renderJson(err());
			return;
		}
		renderJson(suc());
	}
}
