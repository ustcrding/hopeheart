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

package com.bocsoft.index;

import java.util.Collection;


import com.jfinal.aop.Inject;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.kit.Md5Kit;
import com.bocsoft.common.model.SysFunction;
import com.bocsoft.common.model.SysUser;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.common.visit.Visitor;
import com.bocsoft.common.visit.VisitorUtil;
import com.bocsoft.common.vo.TreeNode;
import com.bocsoft.portal.core.service.SysFuncService;
import com.bocsoft.portal.core.service.SysUserService;

/**
 * 系统首页
 * 
 * @author bocsoft
 * @date 2018年7月19日
 */
@ControllerBind(path="/")
public class IndexController extends BaseController {

	@Inject
	SysFuncService sysFuncService;
	@Inject
	SysUserService sysUserService;

	/**
	 * 主页方法
	 * 
	 * @author bocsoft
	 * @date 2018年11月15日
	 */
	public void index() {
		//屏蔽无效的url地址
		if(getPara()!=null){
			getResponse().setStatus(404);
			renderError(404);
			return;
		}
		
		Visitor vs = VisitorUtil.getVisitor(getSession());
		// 锁屏未解锁,刷新浏览器强制移除登录身份信息
		String userName = (String) getSession().getAttribute(vs.getName());

		if (userName != null) {
			getSession().removeAttribute(userName);
			VisitorUtil.removeVisitor(getSession());
			this.logout();
			return;
		}

		// 缓存
		Collection<TreeNode> funcList = CacheKit.get("userFunc", "funcList"+vs.getCode(), new IDataLoader() {
			@Override
			public Object load() {
				return sysFuncService.getUserFunctionTree(vs.getCode(), "sys");
			}
		});
		
		SysFunction sf = CacheKit.get("userFunc", "frameMainView", new IDataLoader() {
			@Override
			public Object load() {
				return sysFuncService.findById("frame_main_view");
			}
		    });

		if (sf != null) {
			setAttr("frameMainView", sf.getLinkPage());
		}
		
		setAttr("funcList", JsonKit.toJson(funcList));
		setAttr("vs", vs);
		
		render("index.html");
	}


	/**
	 * 退出登录
	 * 
	 * @author bocsoft
	 * @date 2018年11月15日
	 */
	public void logout() {    
		VisitorUtil.removeVisitor(getSession());
		redirect("/pub/login");
	}

	/***
	 * 锁屏
	 * @param userName
	 * @param session
	 * @return
	 * @author bocsoft
	 * @date 2018年3月6日下午11:48:34
	 */
	public void lock() {
		Visitor vs = VisitorUtil.getVisitor(getSession());
		if(vs==null){
			renderJson(err("登录信息已失效，请刷新浏览器(F5)重新登录"));
			return;
		}
		getSession().setAttribute(getPara("userName","userName"), getPara("userName","userName"));
		renderJson(suc());
	}

	/**
	 * 解屏
	 *
	 * @param userName
	 * @param userPasswd
	 * @param session
	 * @return
	 * @author bocsoft
	 * @date 2018年3月6日下午11:48:34
	 */
	public void unLock() {
		Visitor vs = VisitorUtil.getVisitor(getSession());
		if(vs==null){
			renderJson(err("登录信息已失效，请刷新浏览器(F5)重新登录"));
			return;
		}
		String passwd = Md5Kit.md5(getPara("password"));
		SysUser user = sysUserService.findByUserCode(vs.getCode());
		if (passwd.equals(user.getPasswd())) {
			getSession().removeAttribute(getPara("userName"));
			renderJson(suc());
		} else {
			renderJson(err("密码错误，请重新输入..."));
		}
	}
}
