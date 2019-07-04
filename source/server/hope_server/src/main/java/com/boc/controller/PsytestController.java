package com.boc.controller;

import com.boc.model.Doctorinfo;
import com.boc.model.Psytest;
import com.boc.model.Psytestinfo;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

public class PsytestController extends Controller {
	public void index() {
		
	}
	public void evaluation() {
		try {
			Psytestinfo psytestinfo = new Psytestinfo().setVictimId(getPara("victimId"))
					.setPsytestsId(getPara("psytestsId")).setTestsLevel(getPara("testsLevel"))
					.setTestioinDate(getPara("testioinDate")).setTestjoinTime(getPara("testioinTime"))
					.setAddressCode(getPara("addressCode"));
					psytestinfo.save();
			renderJson(Ret.by("code", "0").set("data", Kv.by("victimtestId", psytestinfo.getVictimtestId())));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.by("code", "1").set("msg", "数据入库失败"));
		}
	}
	@ActionKey("/psytest/recommend/doctor")
	public void recommend() {
		//心理测评知识库号
		String psytestsId = getPara("psytestsId","1");
		//心理测评号
		String victimtestId = getPara("victimtestId");
		//1、根据传来的心理测评知识库号去查询心理测评大类、心理测评小类
		Psytest psytest = Psytest.dao.findFirst("select psytests_type,psytests_subtype from psytest where psytests_id=?",psytestsId);
		//2、
		//a、心理测评小类匹配第一个医生，否则执行b
		//b、心理测评大类匹配第一个医生，否则执行c
		//c、匹配当前没有参与辅导第一个医生，否则返回医生表第一个医生
		Doctorinfo doctorinfo = Doctorinfo.dao.findFirst("select * from doctorinfo where psytests_subtype=? limit 0,1",psytest.getPsytestsSubtype());
		if(doctorinfo==null) {
			doctorinfo = Doctorinfo.dao.findFirst("select * from doctorinfo where psytests_type=? limit 0,1",psytest.getPsytestsType());
			if(doctorinfo==null) {
				doctorinfo = Doctorinfo.dao.findFirst("select * from doctorinfo where counseling='N' limit 0,1");
				if(doctorinfo==null) {
					doctorinfo = Doctorinfo.dao.findFirst("select * from doctorinfo limit 0,1");
				}
			}
		}
		//更新psytestinfo表的医生ID
		Psytestinfo psytestinfo = Psytestinfo.dao.findFirst("select * from psytestinfo where victimtest_id=?",victimtestId);
		psytestinfo.setPsychologistId(doctorinfo.getDoctorId()).update();
		renderJson(Ret.by("code", "0").set("data", Kv.by("doctorId", doctorinfo.getDoctorId())
				.set("doctorName", doctorinfo.getDoctorName())
				.set("doctorResume", doctorinfo.getAffiliate()+"|"+doctorinfo.getCertificate())));
	}
}
