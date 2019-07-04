package com.boc.service;

import com.boc.model.Psytestinfo;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PsyQueryService {
    public Ret feedback(String victimId, String victimTestId, String doctorId, String satisficing) {
        Psytestinfo psytestinfo = new Psytestinfo();
        psytestinfo.set("victim_id", victimId);
        psytestinfo.set("victimtest_id", victimTestId);
        psytestinfo.set("psychologist_id", doctorId);
        psytestinfo.set("satisficing", satisficing);
        boolean ret = psytestinfo.update();
        if (!ret) {
            return Ret.by("code", "1").set("message", "提交失败");
        }
        return Ret.by("code", "0").set("message", "提交成功");
    }

    public Page<Psytestinfo> historyListQuery(String victimId, String testStartDate, String testEndDate, String addressCode, int startNo) {
        int pageNum = startNo/10+1;
    	return Psytestinfo.dao.paginate(pageNum, 10,
    			Psytestinfo.dao.getSqlPara("getPsytestInfo", 
    					Kv.by("victimId", victimId).set("testStartDate", testStartDate).set("testEndDate", testEndDate).set("addressCode", addressCode)));
    }

    public Record historyDetailQuery(String victimId, String victimTestId) {
//        StringBuffer sql = new StringBuffer();
//        sql.append("select victimtest_id victimtestId,ifnull(tests_level,'') testsLevel,ifnull(a.counseling,'') counseling," +
//                "ifnull(testioin_date,'') testioinDate,ifnull(testjoin_time,'') testjoinTime," +
//                "ifnull(a.address_code,'') addressCode,ifnull(a.satisficing,'')satisficing,ifnull(doctor_name,'') doctorName," +
//                "ifnull(certificate,'') certificate,ifnull(affiliate,'') affiliate,ifnull(volunteer_name,'') volunteerName,ifnull(platform,'') platform ");
//        sql.append("FROM PSYTESTINFO a LEFT JOIN DOCTORINFO b ON a.PSYCHOLOGIST_ID=b.DOCTOR_ID  ");
//        sql.append("LEFT JOIN VOLUNTEERINFO c ON a.VOLUNTEER_ID=c.VOLUNTEER_ID  ");
//        sql.append("WHERE a.VICTIM_ID=? AND a.VICTIMTEST_ID=?");
//        Record record = Db.findFirst(sql.toString(), victimId, victimTestId);
        Record record = Db.findFirst(Db.getSqlPara("getPsytestDetail",Kv.by("victimId",victimId).set("victimTestId",victimTestId)));
//        System.out.println(record);
        return record;
    }

}
