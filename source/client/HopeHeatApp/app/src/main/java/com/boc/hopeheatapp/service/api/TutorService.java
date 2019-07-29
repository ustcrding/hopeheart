package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.TutorDetailEntity;
import com.boc.hopeheatapp.model.TutorHistoryEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public interface TutorService {
    @FormUrlEncoded
    @POST("/psyHis/list")
    Observable<BaseResponse<TutorHistoryEntity>> queryTutorHistory(@Field("victimId") String id,
                                                                     @Field("startNo") String startNo);

    @FormUrlEncoded
    @POST("/psyHis/detail")
    Observable<BaseResponse<TutorDetailEntity>> queryTutorDetail(@Field("victimId") String id,
                                                                 @Field("victimTestId") String testId);
}
