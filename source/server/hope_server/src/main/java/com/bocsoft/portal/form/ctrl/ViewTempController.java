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

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.portal.form.service.FormViewService;

/**
 * 在线表单页面模板
 * @author bocsoft
 * @date 2019年3月14日  
 */
@ControllerBind(path="/portal/form/viewTemp")
public class ViewTempController extends BaseController{

	@Inject
	FormViewService formViewService;
	
	/**
	 * 设计表单首页
	 * 
	 * @author bocsoft
	 * @date 2019年3月19日
	 */
	public void index() {
		String temp=getPara("temp","index");
		render(temp+".html");
	}
	
	/**
	 * 缓存表单数据
	 * 
	 * @author bocsoft
	 * @date 2019年3月19日
	 */
	public void save() {
		setSessionAttr("formViewHtml", getPara("html"));
		renderJson(suc());
	}
	
	/**
	 * 表单预览
	 * 
	 * @author bocsoft
	 * @date 2019年3月19日
	 */
	public void review() {
		if(getPara("viewId")!=null) {
			Record record=formViewService.findById(getPara("viewId"));
			writeToHtml("review.html",record.get("template_view","")+"");
		}else {				
			writeToHtml("review.html",getSessionAttr("formViewHtml")+"");
		}	
		render("review.html");
	}
}
