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

package com.bocsoft.portal.core.ctrl;

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.portal.core.service.SysLogService;

/**
 * 系统日志
 * @author bocsoft
 *
 */
@ControllerBind(path="/portal/core/sysLog")
public class SysLogController extends BaseController {

	@Inject
	SysLogService service;
	public void index(){
		render("index.html");
	}
	
	public void list(){
		Record record = new Record();
		record.set("user_code", getPara("userCode"));
		record.set("method_name", getPara("methodName"));
		record.set("create_time", getPara("createTime"));
		record.set("remark", getPara("remark"));
		renderJson(service.queryForList(getParaToInt("pageNumber",1), getParaToInt("pageSize",10),record,"order by create_time desc"));
	}
	
	public void detail(){
		setAttr("sysLog",service.findById(getPara()));
		render("detail.html");
	}
	
	public void delete(){
		service.deleteByIds(getIds());
		renderJson(suc());
	}
}
