package com.bocsoft.portal.core.service;

import com.bocsoft.common.base.service.BaseService;
import com.bocsoft.common.model.DoctorInfo;
import com.bocsoft.common.vo.Grid;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class SysDocInfoService extends BaseService {

    private DoctorInfo dao=new DoctorInfo().dao();

    /* (non-Javadoc)
     * @see com.bocsoft.common.base.service.BaseService#getDao()
     */
    @Override
    public Model<?> getDao() {
        return dao;
    }

    public List<Record> queryDocList(){
        return queryForList("select * from doctorinfo where reserved2=?");
    }


    public List<Record> queryHospital(){
        return queryForList("select affiliate text from doctorinfo");
    }

    //public List<Record> query

    public Grid page(int pageNumber, int pageSize, Record record) {
        Record rd = new Record();
        rd.set("a.doctor_certype like", record.getStr("doctor_certype"));
        rd.set("a.doctor_certno like", record.getStr("doctor_certno"));
        rd.set("a.certificate like", record.getStr("certificate"));
        rd.set("a.affiliate like", record.getStr("affiliate"));
        String sql=Db.getSql("core.getDoctorInfoList");
        //医师信息列表
        return queryForList(sql,pageNumber, pageSize, rd, null);
    }

    public Grid pageByDocId(int pageNumber, int pageSize,String docid) {
        Record rd = new Record();
        rd.set("a.reserved3=", docid);
        String sql=Db.getSql("core.getDoctorInfoList");
        //医师信息列表
        return queryForList(sql,pageNumber, pageSize, rd, null);
    }

    public Grid pageVimById(int pageNumber, int pageSize,String docid) {
        Record rd = new Record();
        rd.set("a.reserved3=", docid);
        String sql=Db.getSql("core.getVimInfoList");
        //受害者信息列表
        return queryForList(sql,pageNumber, pageSize, rd, null);
    }

    public Grid pageVolById(int pageNumber, int pageSize,String docid) {
        Record rd = new Record();
        rd.set("a.reserved3=", docid);
        String sql=Db.getSql("core.getVolInfoList");
        //志愿者信息列表
        return queryForList(sql,pageNumber, pageSize, rd, null);
    }

    public boolean saveEntity(DoctorInfo doctorInfo) {
        if(isExist(doctorInfo.getDoctorId())) {
            return false;
        }
        return doctorInfo.save();
    }

    public boolean isExist(String doctorId) {
        if (findByDoctorId(doctorId)!=null) {
            return true;
        }
        return false;
    }

    public DoctorInfo findByDoctorId(String doctorId) {
       // return dao.findById(doctorId.toLowerCase());
        return dao.findFirst("select * from doctorinfo where doctor_id=?",doctorId);

    }

    @Override
    public boolean deleteById(String doctorId) {
        doctorId=doctorId.toLowerCase();
        return dao.deleteById(doctorId);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        Object[][] paras=new Object[ids.size()][1];
        for(int i=0;i<ids.size();i++) {
            paras[i][0]=ids.get(i);
        }
        String sql="delete from doctorinfo where doctor_id=?";
        Db.batch(sql, paras, 50);
    }


    public void checkInfoByIds(List<String> ids) {
        Object[][] paras=new Object[ids.size()][1];
        for(int i=0;i<ids.size();i++) {
            paras[i][0]=ids.get(i);
        }
        String sql="update doctorinfo set reserved2='Y' where doctor_id=?";
        Db.batch(sql, paras, 50);
    }

}