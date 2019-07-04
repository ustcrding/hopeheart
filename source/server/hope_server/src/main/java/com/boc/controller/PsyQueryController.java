package com.boc.controller;

import com.boc.model.Psytestinfo;
import com.boc.service.PsyQueryService;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.StringUtils;

public class PsyQueryController extends Controller {
    @Inject
    private PsyQueryService psyQueryService;

    /**
     * 咨询结果反馈
     */
    public void feedback() {
        String victimId = getPara("victimId");
        String victimTestId = getPara("victimTestId");
        String doctorId = getPara("doctorId");
        String satisficing = getPara("satisficing");
        Ret ret = psyQueryService.feedback(victimId, victimTestId, doctorId, satisficing);
        renderJson(ret);
    }

    /**
     * 咨询历史列表查询
     */
    public void historyListQuery() {
        String victimId = getPara("victimId");
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
        Page<Psytestinfo> page = psyQueryService.historyListQuery(victimId, testStartDate, testEndDate, addressCode, startNo);
        if (page == null || page.getList().size() == 0) {
            renderJson(Ret.by("code", "1").set("message", "查询无记录"));
            return;
        }
        renderJson(Ret.by("code", "0").set("data", page));
    }

    /**
     * 咨询历史详情查询
     */
    public void historyDetailQuery() {
        String victimId = getPara("victimId");
        String victimTestId = getPara("victimTestId");
        Record record = psyQueryService.historyDetailQuery(victimId, victimTestId);
        if (record == null) {
            renderJson(Ret.by("code", "1").set("message", "查询无记录"));
            return;
        }
        renderJson(Ret.by("code", "0").set("data", record));
    }
}
