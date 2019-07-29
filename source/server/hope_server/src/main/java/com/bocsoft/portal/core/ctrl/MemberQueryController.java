package com.bocsoft.portal.core.ctrl;

import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.common.vo.Grid;
import com.bocsoft.portal.core.service.MemberQueryService;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;

@ControllerBind(path = "/portal/core/memberquery")
public class MemberQueryController extends Controller {
    private MemberQueryService service = MemberQueryService.me;

    public void index() {
        render("index.html");
    }

    public void queryMemList() {
        int pageNum = getInt("pageNumber");
        int pageSize = getInt("pageSize");
        String uploadNo = getPara("uploadNo");
        String authorState = getPara("authorState");
        if (uploadNo == null || "".equals(uploadNo)) {
            renderJson(Ret.by("code", "0").set("message", "请求参数错误"));
            return;
        }
//        MemberList memberList = ;
////        System.out.println(memberList.toString());
//        if (memberList == null || memberList.getMemberList() == null) {
//            renderJson(Ret.by("code", "0").set("message", "查询无记录"));
//            return;
//        }
       service.queryMemberList(pageNum,pageSize, uploadNo, authorState);
        System.out.println("查询结束");
        renderJson(service.queryMemberList(pageNum,pageSize, uploadNo, authorState));
    }

    public void queryMemDetail() {
        String id = getPara("id");
        String infoType = getPara("infoType");
        if (id == null || "".equals(id)) {
            renderJson(Ret.by("code", "0").set("message", "请求参数错误"));
            return;
        }
        if (!"V".equals(infoType) && !"D".equals(infoType) && !"T".equals(infoType)) {
            renderJson(Ret.by("code", "0").set("message", "查询请求数据错误"));
            return;
        }
        Record record = service.queryMemberDetail(id, infoType);
        if (record == null) {
            renderJson(Ret.by("code", "0").set("message", "查询无记录"));
            return;
        }
        System.out.println(record);
        if("T".equals(infoType)){
            setAttr("doctorInfo", record);
            render("detail_volunteer.html");
        }else if("V".equals(infoType)){
            setAttr("doctorInfo", record);
            render("detail_victim.html");
        }else if("D".equals(infoType)){
            setAttr("doctorInfo", record);
            render("detail_doctor.html");
        }

//      renderJson(Ret.by("code", "0").set("data", record));
    }
}
