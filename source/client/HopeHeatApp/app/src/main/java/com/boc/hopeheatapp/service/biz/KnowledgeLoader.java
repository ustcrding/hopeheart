package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.PsychologyEntity;
import com.boc.hopeheatapp.model.RescueEntity;
import com.boc.hopeheatapp.service.api.KnowledgeService;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class KnowledgeLoader extends BaseLoader {
    private KnowledgeService service;

    public KnowledgeLoader() {
        service = UpdateServiceManager.getInstance().create(KnowledgeService.class);
    }

    public Observable<RescueEntity> queryRescueInfo(String type,
                                                    String subType) {
        return observe(service.queryRescueInfo(type, subType)).map(new Func1<BaseResponse<RescueEntity>, RescueEntity>() {
            @Override
            public RescueEntity call(BaseResponse<RescueEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }

    public Observable<PsychologyEntity> queryPsychologyInfo(String type,
                                                            String subType) {
        return observe(service.queryPsychologyInfo(type, subType)).map(new Func1<BaseResponse<PsychologyEntity>, PsychologyEntity>() {
            @Override
            public PsychologyEntity call(BaseResponse<PsychologyEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }
}
