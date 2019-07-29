package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.RetrofitServiceManager;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.AiAnswerEntity;
import com.boc.hopeheatapp.model.ConsultEntity;
import com.boc.hopeheatapp.model.DoctorEntity;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.api.AiService;
import com.boc.hopeheatapp.service.api.UserService;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author dwl
 * @date 2018/2/23.
 */
public class AiLoader extends BaseLoader {
    private AiService service;

    public AiLoader() {
        service = RetrofitServiceManager.getInstance().create(AiService.class);
    }


    public Observable<AiAnswerEntity> queryAnswer(String query, String sessionId) {
        return observe(service.queryAnswer(query, sessionId)).map(new Func1<BaseResponse<AiAnswerEntity>, AiAnswerEntity>() {
            @Override
            public AiAnswerEntity call(BaseResponse<AiAnswerEntity> baseResponse) {
                baseResponse.throwExceptionIfError();
                return baseResponse.getData();
            }
        });
    }
}