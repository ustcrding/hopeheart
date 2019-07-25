package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AreaEntity;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.VictimBaseEntity;
import com.boc.hopeheatapp.model.VictimEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zpwu3 on 2019-07-24.
 */
public interface VictimService {
    @FormUrlEncoded
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/coach/getCoachedList")
    Observable<BaseResponse<VictimBaseEntity>> getCoachedLis(@Field("doctorId") String id,
                                                        @Field("addressCode") String address,
                                                             @Field("startNo") int startNo);

    @FormUrlEncoded
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/coach/getCoachedDetail")
    Observable<BaseResponse<VictimEntity>> getCoachedDetail(@Field("victimId") String id);

    @FormUrlEncoded
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/coach/markCoached")
    Observable<BaseResponse<Void>> markCoached(@Field("doctorId") String doctorId,
                                               @Field("volunteerId") String volunteerId,
                                               @Field("markType") String markType,
                                               @Field("markNum") int num,
                                               @Field("victimList") String ids);


    @FormUrlEncoded
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/coach/getAddr")
    Observable<BaseResponse<AreaEntity>> getAresInfo(@Field("doctorId") String doctorId,
                                                     @Field("volunteerId") String volunteerId);


}
