package com.boc.controller;

import com.boc.service.IndexService;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;

import cn.hutool.crypto.SecureUtil;

public class IndexController extends Controller {
	@Inject
	private IndexService indexSrv;
	
	public void index(){
		
	}
	@Before(POST.class)
	public void login(){
		String username = getPara("username");
		String password = getPara("password");
		Ret ret = indexSrv.login(username,password);
		renderJson(ret);
	}
	public void verify(){
		renderJson(indexSrv.verify(getPara("userId")));
	}
	@NotAction
	public static void main(String[] args) {
		System.out.println(SecureUtil.md5("111111"));
	}
}
