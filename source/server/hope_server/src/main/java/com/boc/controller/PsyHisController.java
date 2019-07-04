package com.boc.controller;

import org.apache.commons.lang3.StringUtils;

import com.boc.service.PsyHisService;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PsyHisController extends Controller {
	@Inject
	private PsyHisService psyHisService;
	public void index(){
		
	}
	/**
	 * 辅导历史列表查询
	 */
	@ActionKey("/psyHis/list")
	public void list(){
		String doctorId = getPara("doctorId");
        String testStartDate = getPara("testStartDate");
        String testEndDate = getPara("testEndDate");
        String addressCode = getPara("addressCode");
        String sStartNo = getPara("startNo");
        if (StringUtils.isEmpty(sStartNo)) {
            Ret ret = Ret.by("code", "1").set("message", "查询参数错误");
            renderJson(ret);
            return;
        }
        int startNo = getParaToInt("startNo");
        if (startNo % 10 != 1) {
            Ret ret = Ret.by("code", "1").set("message", "查询参数错误");
            renderJson(ret);
            return;
        }
        Page<Record> page = psyHisService.historyGuidenceListQuery(doctorId, testStartDate, testEndDate, addressCode, startNo);
        if (page == null || page.getList().size() == 0) {
            renderJson(Ret.by("code", "1").set("message", "查询无记录"));
            return;
        }
        renderJson(Ret.by("code", "0").set("data", page));
    }
	/**
	 * 辅导历史详情查询
	 */
	@ActionKey("/psyHis/detail")
	public void detail(){
		String victimId = getPara("victimId");
		String victimTestId = getPara("victimTestId");
		Record record = psyHisService.queryPsyDetail(victimId, victimTestId);
		if (record == null) {
            renderJson(Ret.by("code", "1").set("message", "查询无记录"));
            return;
        }
        renderJson(Ret.by("code", "0").set("data", record));
	}
}
