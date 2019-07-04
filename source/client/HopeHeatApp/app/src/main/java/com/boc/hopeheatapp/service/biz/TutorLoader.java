package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.ConsultDetailEntity;
import com.boc.hopeheatapp.model.ConsultHistoryEntity;
import com.boc.hopeheatapp.model.TutorDetailEntity;
import com.boc.hopeheatapp.model.TutorHistoryEntity;
import com.boc.hopeheatapp.service.api.ConsultService;
import com.boc.hopeheatapp.service.api.TutorService;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class TutorLoader extends BaseLoader {
    private TutorService service;

    public TutorLoader() {
        service = UpdateServiceManager.getInstance().create(TutorService.class);
    }

    public Observable<TutorHistoryEntity> queryTutorHistory(String id,
                                                              String startNo) {
        return observe(service.queryTutorHistory(id, startNo)).map(new Func1<BaseResponse<TutorHistoryEntity>, TutorHistoryEntity>() {
            @Override
            public TutorHistoryEntity call(BaseResponse<TutorHistoryEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }

    public Observable<TutorDetailEntity> queryTutorDetail(String id,
                                                          String testId) {
        return observe(service.queryTutorDetail(id, testId)).map(new Func1<BaseResponse<TutorDetailEntity>, TutorDetailEntity>() {
            @Override
            public TutorDetailEntity call(BaseResponse<TutorDetailEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }
}
