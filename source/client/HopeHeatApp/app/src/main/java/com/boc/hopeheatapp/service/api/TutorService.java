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
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/psyHis/list")
    Observable<BaseResponse<TutorHistoryEntity>> queryTutorHistory(@Field("victimId") String id,
                                                                     @Field("startNo") String startNo);

    @FormUrlEncoded
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/psyHis/detail")
    Observable<BaseResponse<TutorDetailEntity>> queryTutorDetail(@Field("victimId") String id,
                                                                 @Field("victimTestId") String testId);
}
