package com.bocsoft.portal.core.service;

import com.bocsoft.common.base.service.BaseService;
import com.bocsoft.common.model.DoctorInfo;
import com.bocsoft.common.model.FileUploaded;
import com.bocsoft.common.model.Victiminfo;
import com.bocsoft.common.model.Volunteerinfo;
import com.bocsoft.common.vo.Grid;
import com.bocsoft.portal.memberQuery.dto.MemberCycle;
import com.bocsoft.portal.memberQuery.dto.MemberList;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

public class MemberQueryService extends BaseService {
    private DoctorInfo dao = new DoctorInfo().dao();
    public static final MemberQueryService me = new MemberQueryService();

    public Grid queryMemberList(int pageNumber, int pageSize, String uploadNo, String authorState) {
        Grid grid = null;
        MemberList memberList = new MemberList();
        memberList.setUploadNo(uploadNo);
        String sql_infoType = "select info_type from file_uploaded where id='" + uploadNo + "' and author_state='"+authorState+"'";
        FileUploaded infoType = FileUploaded.dao.findFirst(sql_infoType);
        if (infoType == null) {
//            System.out.println("null");
            return grid;
        }
//        System.out.println("继续查询");
        memberList.setInfoType(infoType.getInfoType());
        //查询受灾群众表
        StringBuffer sql_from_victim = new StringBuffer();
        sql_from_victim.append("select a.victim_id from victiminfo a,file_uploaded b where ");
        sql_from_victim.append("a.reserved2='"+authorState+"' and a.reserved3=b.id and a.reserved3='" + uploadNo + "' ");
//        if (authorState != null || !"".equals(authorState)) {
//            sql_from_victim.append("and b.author_state='" + authorState + "' ");
//        }
        Victiminfo victim = Victiminfo.dao.findFirst(sql_from_victim.toString());
        if (null != victim && !"".equals(victim.getVictimId())) {
            String sql_totalPage = "select ceil(count(*)/20) totalPage from victiminfo a,file_uploaded b where ";
            sql_totalPage = sql_totalPage + "a.reserved2='"+authorState+"' and a.reserved3=b.id and a.reserved3='" + uploadNo + "'";
            FileUploaded totalPage = FileUploaded.dao.findFirst(sql_totalPage);
            memberList.setTotalPage(totalPage.getTotalPage());
            String sql_list = "select a.victim_id roleid,a.victim_name name,a.victim_certype certType,a.victim_certno certNo, ";
            sql_list = sql_list + "a.address_code address,'' platform,b.info_type from victiminfo a,file_uploaded b where a.reserved3=b.id ";
            sql_list = sql_list + "and a.reserved2='"+authorState+"' and b.id=? ";
            List<Object> paras = new ArrayList<>();
            paras.add(uploadNo);
//            if (authorState != null || !"".equals(authorState)) {
//                sql_list = sql_list + "and b.author_state=? ";
//                paras.add(authorState);
//            }
//            List<FileUploaded> list = FileUploaded.dao.find(sql_list);
//            setList(list, memberList);

            return getGrid(pageNumber, pageSize, sql_list, paras.toArray());
        }

        //查询医师表
        StringBuffer sql_from_doctor = new StringBuffer();
        sql_from_doctor.append("select a.doctor_id from doctorinfo a,file_uploaded b where ");
        sql_from_doctor.append("a.reserved2='"+authorState+"' and a.reserved3=b.id and a.reserved3='" + uploadNo + "' ");
//        if (authorState != null || !"".equals(authorState)) {
//            sql_from_doctor.append("and b.author_state='" + authorState + "' ");
//        }
        DoctorInfo doctorInfo = DoctorInfo.dao.findFirst(sql_from_doctor.toString());
        if (null != doctorInfo && !"".equals(doctorInfo.getDoctorId())) {
            String sql_totalPage = "select ceil(count(*)/20) totalPage from doctorinfo a,file_uploaded b where ";
            sql_totalPage = sql_totalPage + "a.reserved2='N' and a.reserved3=b.id and a.reserved3='" + uploadNo + "'";
            FileUploaded totalPage = FileUploaded.dao.findFirst(sql_totalPage);
            memberList.setTotalPage(totalPage.getTotalPage());
            String sql_list = "select a.doctor_id roleid,a.doctor_name name,a.doctor_certype certType,a.doctor_certno certNo, ";
            sql_list = sql_list + "a.address_code address,a.affiliate platform,b.info_type from doctorinfo a,file_uploaded b where a.reserved3=b.id ";
            sql_list = sql_list + "and a.reserved2='"+authorState+"' and b.id=? ";
            List<Object> paras = new ArrayList<>();
            paras.add(uploadNo);
//            if (authorState != null || !"".equals(authorState)) {
//                sql_list = sql_list + "and b.author_state=? ";
//                paras.add(authorState);
//            }
//            List<FileUploaded> list = FileUploaded.dao.find(sql_list);
//            setList(list, memberList);
            System.out.println("组装医生信息sql" + sql_list);
            return getGrid(pageNumber, pageSize, sql_list, paras.toArray());
        }

        //查询志愿者表
        StringBuffer sql_from_volunteer = new StringBuffer();
        sql_from_volunteer.append("select a.volunteer_id from volunteerinfo a,file_uploaded b where ");
        sql_from_volunteer.append("a.reserved2='"+authorState+"' and a.reserved3=b.id and a.reserved3='" + uploadNo + "' ");
//        if (authorState != null || !"".equals(authorState)) {
//            sql_from_volunteer.append("and b.author_state='" + authorState + "' ");
//        }
        Volunteerinfo volunteerinfo = Volunteerinfo.dao.findFirst(sql_from_volunteer.toString());
        if (null != volunteerinfo && !"".equals(volunteerinfo.getVolunteerId())) {
            String sql_totalPage = "select ceil(count(*)/20) totalPage from volunteerinfo a,file_uploaded b where ";
            sql_totalPage = sql_totalPage + "a.reserved2='"+authorState+"' and a.reserved3=b.id and a.reserved3='" + uploadNo + "'";
            FileUploaded totalPage = FileUploaded.dao.findFirst(sql_totalPage);
            memberList.setTotalPage(totalPage.getTotalPage());
            String sql_list = "select a.volunteer_id roleid,a.volunteer_name name,a.volun_certtype certType,a.volun_certno certNo, ";
            sql_list = sql_list + "a.volun_address address,a.platform,b.info_type from volunteerinfo a,file_uploaded b where a.reserved3=b.id ";
            sql_list = sql_list + "and a.reserved2='"+authorState+"' and b.id=? ";
            List<Object> paras = new ArrayList<>();
            paras.add(uploadNo);
//            if (authorState != null || !"".equals(authorState)) {
//                sql_list = sql_list + "and b.author_state=? ";
//                paras.add(authorState);
//            }
//            List<FileUploaded> list = FileUploaded.dao.find(sql_list);
//            setList(list, memberList);
            return getGrid(pageNumber, pageSize, sql_list, paras.toArray());
        }
        return null;
    }

    public String getDocType(String id) {
        String sqlStr = Db.getSql("core.getDocType");
        return dao.findFirst(sqlStr, id, id, id).get("doctype");
    }

    public Record queryMemberDetail(String id, String infoType) {
        Record record = null;
        String sql = null;
        if ("V".equals(infoType)) {
            sql = "select victim_id,victim_name,victim_gender,victim_certype,victim_certno,victim_phone,address_code,address_detail,";
            sql = sql + "register_date,added from victiminfo where victim_id='" + id + "'";

        } else if ("D".equals(infoType)) {
            sql = "select doctor_id,doctor_name,doctor_gender,doctor_certype,doctor_certno,certificate,address_code,affiliate,";
            sql = sql + "psyledge_type,psyledge_subtype,psytests_type,psytests_subtype from doctorinfo where doctor_id='" + id + "'";
        } else if ("T".equals(infoType)) {
            sql = "select volunteer_id,volunteer_name,volun_certno,volun_certtype,volun_address,platform,psyledge_type,";
            sql = sql + "psyledge_subtype,psytests_type,psytests_subtype,voluntary_type from volunteerinfo where volunteer_id='" + id + "'";
        }
        record = Db.findFirst(sql);
        return record;
    }

    private void setList(List<FileUploaded> list, MemberList memberList) {
        List<MemberCycle> memberCycleList = new ArrayList<>();
        for (FileUploaded info : list) {
            MemberCycle cycle = new MemberCycle();
            cycle.setId(info.getRoleId());
            cycle.setName(info.getName());
            cycle.setAddress(info.getAddress());
            cycle.setCertType(info.getCertType());
            cycle.setCertNo(info.getCertNo());
            cycle.setPlatform(info.getPlatform());
            memberCycleList.add(cycle);
        }
        memberList.setMemberList(memberCycleList);
    }

    @Override
    public Model<?> getDao() {
        return null;
    }

    public void check(String uploadNo) {
        String update_fileUpload = "update file_uploaded set author_state='Y',author_id='admin' where id='" + uploadNo + "' ";
        Record record = Db.findFirst("select info_type from file_uploaded where id='" + uploadNo + "' ");
        if(record == null){
            return;
        }
        String infoType = record.getStr("info_type");
        String update_obj;
        if ("V".equals(infoType)) {
            update_obj = "update victiminfo set reserved2='Y' where reserved3='"+ uploadNo + "' ";
        } else if ("T".equals(infoType)) {
            update_obj = "update volunteerinfo set reserved2='Y' where reserved3='"+ uploadNo + "' ";
        } else if ("D".equals(infoType)) {
            update_obj = "update doctorinfo set reserved2='Y' where reserved3='"+ uploadNo + "' ";
        }else{
            return;
        }
        Db.update(update_fileUpload);
        Db.update(update_obj);
    }
}
