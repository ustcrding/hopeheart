package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.ConsultDetailEntity;
import com.boc.hopeheatapp.model.ConsultHistoryEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public interface ConsultService {

    @FormUrlEncoded
    @POST( "/psyQuery/historyListQuery")
    Observable<BaseResponse<ConsultHistoryEntity>> queryConsultHistory(@Field("victimId") String id,
                                                                       @Field("startNo") String startNo);

    @FormUrlEncoded
    @POST("/psyQuery/historyDetailQuery")
    Observable<BaseResponse<ConsultDetailEntity>> queryConsultDetail(@Field("victimId") String id,
                                                                     @Field("victimTestId") String testId);
}
