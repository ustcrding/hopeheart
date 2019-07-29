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

package com.bocsoft.pub.login.ctrl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.kit.RSAKit;
import com.bocsoft.common.model.SysUser;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.portal.core.service.SysUserService;
import com.jfinal.aop.Inject;
import com.bocsoft.pub.login.service.LoginService;

/**
 * 登陆
 * 
 * @author bocsoft
 * @date 2018-10-15
 */
@ControllerBind(path="/pub/login")
public class LoginController extends BaseController {

	@Inject
	LoginService loginService;
	@Inject
	SysUserService sysUserService;

	public void index() {
		try {
			setAttr("returnUrl",URLEncoder.encode(getPara("returnUrl",""),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		render("login.html");
	}

	/**
	 * 登录认证
	 */
	public void submit() {
		String userCode = getPara("userCode");
		String password = getPara("password");
		String returnUrl=getPara("returnUrl");

		//匹配中文
		Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher matcher=pattern.matcher(returnUrl);		
		//中文转码
		while(matcher.find()){
			try {
				returnUrl=returnUrl.replace(matcher.group(), URLEncoder.encode(matcher.group(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		try {
			password = RSAKit.decryptionToString(password);
			loginService.aopLogin(userCode, password, getRequest());
			redirect(!returnUrl.equals("")?returnUrl:"/");
		} catch (Exception e) {
			handerException(e);
			SysUser user = sysUserService.findByUserCode(userCode);
			if (user != null) {
				isShowVerifyCode(user);
			}
			setAttr("returnUrl", returnUrl);
			setAttr("msg", e.getCause()!=null?e.getCause().getMessage():e.getMessage());
			render("login.html");
		} 

	}
	
	/**
	 * 显示验证码
	 * @param user
	 */
	private void isShowVerifyCode(SysUser user){
		Date allowLogTime = user.getAllowLoginTime();
		if (allowLogTime != null) {
			Date nowDate = new Date();
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal.setTime(allowLogTime);
			cal2.setTime(nowDate);
			float f = cal.getTimeInMillis() - cal2.getTimeInMillis();
			if (f > 0) {
				setAttr("vc", "verifyCode");
			}
		}
	}

}
