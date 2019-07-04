package com.boc.controller;

import com.boc.model.Victiminfo;
import com.boc.service.VictimService;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

public class VictimController extends Controller {
	@Inject
	private VictimService victimSrv;
	public void index(){
		
	}
	/**
	 * 受灾群众信息录入（PSY001）
	 */
	public void collect(){
		Ret ret = victimSrv.collect(getPara("id"),getPara("name"),getPara("sex")
				,getPara("identityType"),getPara("certificateCode"),getPara("phone")
				,getPara("province"),getPara("city"),getPara("address"),getPara("demo"));
		renderJson(ret);
	}
	
	/**
	 * 查询受灾群众信息（PSY002）
	 */
	public void query(){
		String victimId = getPara("id");
		Victiminfo victiminfo = Victiminfo.dao.findFirst("select * from victiminfo where victim_id=?",victimId);
		if(victiminfo!=null)
			renderJson(Ret.by("code", "0").set("data", victiminfo));
		else
			renderJson(Ret.by("code", "1").set("msg", "查无数据"));
	}
}
