package com.boc.controller;

import com.boc.model.Psyknowledge;
import com.boc.model.Rescueknowledge;
import com.boc.service.KnowledgeService;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

public class KnowledgeController extends Controller {
	@Inject
	private KnowledgeService knowledgeSrv;
	public void index(){
		
	}
	/**
	 * 救援知识查询
	 */
	@ActionKey("/knowledge/rescue/query")
	public void rescue(){
		String rescueType = getPara("rescueType");
		String rescueSubType = getPara("rescueSubType");
		Rescueknowledge rescueKnowledge = knowledgeSrv.queryRescueKnowledge(rescueType,rescueSubType);
		if(rescueKnowledge!=null)
			renderJson(Ret.by("code", "0").set("data", rescueKnowledge));
		else
			renderJson(Ret.by("code", "1").set("msg", "查无数据"));
	}
	/**
	 * 心理健康知识查询
	 */
	@ActionKey("/knowledge/psy/query")
	public void psy(){
		String psyledgeType = getPara("psyledgeType");
		String psyledgeSubtype = getPara("psyledgeSubtype");
		Psyknowledge psyKnowledge = knowledgeSrv.queryPsyKnowledge(psyledgeType,psyledgeSubtype);
		if(psyKnowledge!=null)
			renderJson(Ret.by("code", "0").set("data", psyKnowledge));
		else
			renderJson(Ret.by("code", "1").set("msg", "查无数据"));
	}
}
