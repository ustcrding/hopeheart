package com.bocsoft.portal.core.service;

import com.bocsoft.common.base.service.BaseService;
import com.bocsoft.common.model.FileUploaded;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class SysFileUploadService extends BaseService {

    private FileUploaded dao=new FileUploaded().dao();

    /* (non-Javadoc)
     * @see com.bocsoft.common.base.service.BaseService#getDao()
     */
    @Override
    public Model<?> getDao() {
        return dao;
    }

    /**
     * 铺地信息审核
     * @param operId
     * @param id
     * @param authorId
     * @param authorState
     * @param reason
     */
    public void verify(String operId, String id, String authorId, String authorState, String reason) {
        String sql = "update file_uploaded set oper_id=?,author_id=?,author_state=?,reason=? where id=?";
        Db.update(sql, operId, authorId, authorState, reason, id);
    }

}