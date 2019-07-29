package com.bocsoft.portal.core.ctrl;


import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.model.DoctorInfo;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.common.vo.Feedback;
import com.bocsoft.common.vo.Grid;
import com.bocsoft.portal.core.service.MemberQueryService;
import com.bocsoft.portal.core.service.SysDocInfoService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;

/**
 * 系统日志
 * @author bocsoft
 *
 */
@ControllerBind(path="/portal/core/sysDocInfo")
public class SysDocInfoController extends BaseController {

    @Inject
    SysDocInfoService doctorInfoService;
    @Inject
    MemberQueryService memberQueryService;


    public void index() {
        setAttr("hospitalList", doctorInfoService.queryHospital());
        render("index.html");
    }


    public void list() {
        int pageNum = getInt("pageNumber");
        int pageSize = getInt("pageSize");
        String uploadNo = getPara("uploadNo");
        String authorState = getPara("authorState");
        if (uploadNo == null || "".equals(uploadNo)) {
            renderJson(Ret.by("code", "0").set("message", "请求参数错误"));
            return;
        }
        Grid grid = memberQueryService.queryMemberList(pageNum,pageSize, uploadNo, authorState);
        if(grid == null){
            renderJson(Ret.by("code", "0").set("message", "无数据"));
            return ;
        }
        renderJson(grid);
    }
    public void queryMemDetail() {
        String id = getPara();
        //String infoType = getPara("infoType");
        if (id == null || "".equals(id)) {
            renderJson(Ret.by("code", "0").set("message", "请求参数错误"));
            return;
        }
//        if (!"V".equals(infoType) && !"D".equals(infoType) && !"T".equals(infoType)) {
//            renderJson(Ret.by("code", "0").set("message", "查询请求数据错误"));
//            return;
//        }
        String infoType=memberQueryService.getDocType(id);
        Record record = memberQueryService.queryMemberDetail(id,infoType);
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

    public void listByID() {
        String docid=getPara();
        renderJson(doctorInfoService.pageByDocId(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),docid));
    }

    public void listVimByID() {
        String docid=getPara();
        renderJson(doctorInfoService.pageVimById(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),docid));
    }

    public void listVolByID() {
        String docid=getPara();
        renderJson(doctorInfoService.pageVolById(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),docid));
    }

    public void add() {
        setAttr("hospitalList", doctorInfoService.queryHospital());
        render("add.html");
    }

    public void save() {
        DoctorInfo doctorInfo = getBean(DoctorInfo.class);
        doctorInfo.setDoctorId(doctorInfo.getDoctorId());
        if (!doctorInfoService.saveEntity(doctorInfo)) {
            setException("用户编号已存在，请重新输入");
        }
        setAttr("hospitalList", doctorInfoService.queryHospital());
        render("add.html");
    }

    public void edit() {
        System.out.println(getPara());
        DoctorInfo entity = (DoctorInfo) doctorInfoService.findByDoctorId(getPara());
        System.out.println(entity.toString());

        setAttr("doctorInfo", entity);
        setAttr("hospitalList", doctorInfoService.queryHospital());
        render("edit.html");
    }

    public void check(){
//        doctorInfoService.checkInfoByIds(getIds());
//        setAttr("hospitalList", doctorInfoService.queryHospital());
        memberQueryService.check(getPara("uploadNo"));
        renderJson(Feedback.success(Feedback.MSG_CHECK_SUCCESS));
    }

    public void update() {
        DoctorInfo doctorInfo = getBean(DoctorInfo.class);
        System.out.println(doctorInfo.toString());
        doctorInfo.update();
        setAttr("doctorInfo", doctorInfo);
        setAttr("hospitalList", doctorInfoService.queryHospital());
        render("edit.html");
    }

    public void detail(){
        setAttr("doctorInfo", doctorInfoService.findByDoctorId(getPara()));
        render("detail.html");
    }

    public void delete() {
        doctorInfoService.deleteByIds(getIds());
        renderJson(Feedback.success());
    }
}