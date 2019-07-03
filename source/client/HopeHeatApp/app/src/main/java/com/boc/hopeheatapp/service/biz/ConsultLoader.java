package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.ConsultDetailEntity;
import com.boc.hopeheatapp.model.ConsultHistoryEntity;
import com.boc.hopeheatapp.model.PsychologyEntity;
import com.boc.hopeheatapp.model.RescueEntity;
import com.boc.hopeheatapp.service.api.ConsultService;
import com.boc.hopeheatapp.service.api.KnowledgeService;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public class ConsultLoader extends BaseLoader {
    private ConsultService service;

    public ConsultLoader() {
        service = UpdateServiceManager.getInstance().create(ConsultService.class);
    }

    public Observable<ConsultHistoryEntity> queryConsultHistory(String id,
                                                                String startNo) {
        return observe(service.queryConsultHistory(id, startNo)).map(new Func1<BaseResponse<ConsultHistoryEntity>, ConsultHistoryEntity>() {
            @Override
            public ConsultHistoryEntity call(BaseResponse<ConsultHistoryEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }

    public Observable<ConsultDetailEntity> queryConsultDetail(String id,
                                                              String testId) {
        return observe(service.queryConsultDetail(id, testId)).map(new Func1<BaseResponse<ConsultDetailEntity>, ConsultDetailEntity>() {
            @Override
            public ConsultDetailEntity call(BaseResponse<ConsultDetailEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }
}
