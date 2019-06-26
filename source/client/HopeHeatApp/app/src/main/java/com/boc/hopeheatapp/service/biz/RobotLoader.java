package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.RetrofitServiceManager;
import com.boc.hopeheatapp.http.VoidResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AskReqEntity;
import com.boc.hopeheatapp.model.BaseReqEntity;
import com.boc.hopeheatapp.model.HotQuestionListEntity;
import com.boc.hopeheatapp.model.HotquestReqEntity;
import com.boc.hopeheatapp.model.MarkReqEntity;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.model.NaviEntity;
import com.boc.hopeheatapp.model.NaviReqEntity;
import com.boc.hopeheatapp.model.SelectReqEntity;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.VersionEntity;
import com.boc.hopeheatapp.service.api.RobotService;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by qsy on 2018/1/19.
 */

public class RobotLoader extends BaseLoader {
    private RobotService service;

    public RobotLoader() {
        service = RetrofitServiceManager.getInstance().create(RobotService.class);
    }

    /**
     * 提问
     *
     * @param question
     * @return
     */
    public Observable<BaseResponse<AnswerEntity>> ask(String question) {
        return ask(question, MsgEntity.SOURCE_VOICE, null, null);
    }

    /**
     * 提问
     *
     * @param question
     * @return
     */
    public Observable<BaseResponse<AnswerEntity>> ask(String question, int type, String questionId, String catalogId) {
        AskReqEntity askEntity = new AskReqEntity();
        askEntity.setQuestion(question);
        askEntity.setType(type);
        askEntity.setQuestionId(questionId);
        askEntity.setCatalogId(catalogId);
        packRequest(askEntity);

        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtils.toJson(askEntity));

        return observe(service.ask(body));
    }

    private void packRequest(BaseReqEntity baseEntity) {
        baseEntity.setAppId(ApiConfig.getAppId());
        baseEntity.setPlatform("android");
        if (UserManager.getInstance() != null) {
            UserEntity userEntity = UserManager.getInstance().getUser();
            if (userEntity != null) {
                int userId = userEntity.getUserId();
                String username = userEntity.getUsername();
                String token = "" + userId;
                String roleId = userEntity.getRoleId();
                baseEntity.setUserId("" + userId);
                baseEntity.setUserToken(token);
                baseEntity.setRoleId(roleId);
                baseEntity.setUserName(username);
            }
        }
    }

    /**
     * 赞踩
     *
     * @param sessionId
     * @param itemId
     * @param flag
     * @return
     */
    public Observable<VoidResponse> mark(String sessionId, String itemId, int flag) {
        MarkReqEntity markEntity = new MarkReqEntity();
        markEntity.setSessionId(sessionId);
        markEntity.setItemId(itemId);
        markEntity.setFlag(flag);
        packRequest(markEntity);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtils.toJson(markEntity));
        return observe(service.mark(body));
    }

    /**
     * 选择记录
     *
     * @param sessionId
     * @param itemId
     * @param questionId
     * @return
     */
    public Observable<VoidResponse> select(String sessionId, String itemId, String questionId) {
        SelectReqEntity entity = new SelectReqEntity();
        entity.setSessionId(sessionId);
        entity.setItemId(itemId);
        entity.setQuestionId(questionId);
        packRequest(entity);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtils.toJson(entity));

        return observe(service.select(body));
    }

    /**
     * 导航
     *
     * @param question
     * @return
     */
    public Observable<BaseResponse<NaviEntity>> navi(String question) {
        NaviReqEntity req = new NaviReqEntity();
        req.setQuestion(question);
        packRequest(req);

        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtils.toJson(req));
        return observe(service.navi(body));
    }

    /**
     * 请求热门问题
     *
     * @return
     */
    public Observable<HotQuestionListEntity> hotQuestion(String catalogId) {
//        return observe(service.hotQuestion(catalogId)).map(new Func1<BaseResponse<HotQuestionListEntity>, HotQuestionListEntity>() {
//            @Override
//            public HotQuestionListEntity call(BaseResponse<HotQuestionListEntity> hotQuestionListEntityBaseResponse) {
//                hotQuestionListEntityBaseResponse.throwExceptionIfError();
//                return hotQuestionListEntityBaseResponse.getData();
//            }
//        });

        HotquestReqEntity hotquestReqEntity = new HotquestReqEntity();
        hotquestReqEntity.setCatalogId(catalogId);

        packRequest(hotquestReqEntity);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtils.toJson(hotquestReqEntity));
        return observe(service.hotQuestion(body)).map(new Func1<BaseResponse<HotQuestionListEntity>, HotQuestionListEntity>() {
            @Override
            public HotQuestionListEntity call(BaseResponse<HotQuestionListEntity> hotQuestionListEntityBaseResponse) {
                hotQuestionListEntityBaseResponse.throwExceptionIfError();
                return hotQuestionListEntityBaseResponse.getData();
            }
        });
    }

    /**
     * 获取最新版本信息
     *
     * @param curVersionCode
     * @return
     */
    public Observable<BaseResponse<VersionEntity>> getLastVersion(int curVersionCode) {
        return observe(service.getLastVersion(curVersionCode));
    }
}
