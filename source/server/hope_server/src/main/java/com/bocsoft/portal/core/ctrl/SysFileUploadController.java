package com.bocsoft.portal.core.ctrl;


import com.bocsoft.common.base.BaseController;
import com.bocsoft.common.routes.ControllerBind;
import com.bocsoft.common.vo.Feedback;
import com.bocsoft.portal.core.service.SysFileUploadService;
import com.jfinal.aop.Inject;

/**
 *
 * @author bocsoft
 *
 */
@ControllerBind(path="/portal/core/sysFileUpload")
public class SysFileUploadController extends BaseController {

    @Inject
    SysFileUploadService fileUploadService;


    public void index() {

    }


    public void list() {

    }

    public void verify() {
        String operId = getPara("operId");
        String id = getPara("id");
        String authorId = getPara("authorId");
        String authorState = getPara("authorState");
        String reason = getPara("reason");
        fileUploadService.verify(operId, id, authorId, authorState, reason);
        renderJson(Feedback.success());
    }

}