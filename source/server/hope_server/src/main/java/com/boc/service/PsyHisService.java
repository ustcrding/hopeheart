package com.boc.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class PsyHisService {
	
	public Page<Record> historyGuidenceListQuery(String doctorId, String testStartDate, String testEndDate, String addressCode, int startNo) {
        int pageNum = startNo/10+1;
    	return Db.paginate(pageNum, 10,
    			Db.getSqlPara("getPsyGuidenceInfo", 
    					Kv.by("doctorId", doctorId).set("testStartDate", testStartDate).set("testEndDate", testEndDate).set("addressCode", addressCode)));
    }
	
	public JSONObject queryPsyList(String victimId, String startDate, String endDate, String addr, int startNo) {
		int pageSize = 10;
		int pageNumber = startNo / 10 + 1;

		String select = "select t2.victim_name,t2.victim_gender,t1.victimtest_id,t1.tests_level,t1.testioin_date,t1.address_code";
		String sql = "from psytestinfo t1,victiminfo t2" +
				" where t1.victim_id=t2.victim_id" +
				" and t1.victim_id=?" +
				" and t1.testioin_date>=ifnull(?,t1.testioin_date)" +
				" and t1.testioin_date<=ifnull(?,t1.testioin_date)" +
				" and t1.address_code=ifnull(?,t1.address_code)";
		Page<Record> paginate = Db.paginate(pageNumber, pageSize, select, sql, victimId, startDate, endDate, addr);

		JSONObject ret = new JSONObject();
		ret.put("numId", paginate.getList().size());
		ret.put("totalPages", paginate.getTotalPage());
		ret.put("endFlag", "N");
		JSONArray arr = new JSONArray();
		for (Record r : paginate.getList()) {
			JSONObject o = new JSONObject();
			o.put("victimName", r.get("victim_name"));
			o.put("victimGender", r.get("victim_gender"));
			o.put("victimTestId", r.get("victimtest_id"));
			o.put("testsLevel", r.get("tests_level"));
			o.put("testJoinDate", r.get("testioin_date"));
			o.put("address", r.get("address_code"));
			arr.add(o);
		}
		ret.put("LIST", arr);
		return ret;
	}

	public Record queryPsyDetail(String victimId, String victimTestId) {
		String sql = "select t1.victimtest_id victimTestId,t1.tests_level testsLevel,t2.victim_name victimName,t2.victim_gender victimGender," +
				"t2.victim_certype victimCerType,t2.victim_certno victimCerNo,t2.victim_phone victimPhone,t2.address_code victimAddressCode,t2.address_detail addressDetail," +
				"t1.testioin_date testJoinDate,t1.testjoin_time testJoinTime,t1.address_code  addressCode,t1.satisficing" +
				" from psytestinfo t1, victiminfo t2" +
				" where t1.victim_id=t2.victim_id and t1.victimtest_id=? and t1.victim_id=?";
		Record rec = Db.findFirst(sql, victimTestId, victimId);
//		JSONObject ret = new JSONObject();
//		ret.put("victimTestId", rec.get("victimtest_id"));
//		ret.put("testsLevel", rec.get("tests_level"));
//		ret.put("victimName", rec.get("victim_name"));
//		ret.put("victimGender", rec.get("victim_gender"));
//		ret.put("victimCerType", rec.get("victim_certype"));
//		ret.put("victimCerNo", rec.get("victim_certno"));
//		ret.put("victimPhone", rec.get("victim_phone"));
//		ret.put("victimAddressCode", rec.get("address_code"));
//		ret.put("addressDetail", rec.get("address_detail"));
//		ret.put("testJoinDate", rec.get("testioin_date"));
//		ret.put("testJoinTime", rec.get("testjoin_time"));
//		ret.put("addressCode", rec.get("address_code2"));
//		ret.put("satisficing", rec.get("satisficing"));
		return rec;
	}
}
