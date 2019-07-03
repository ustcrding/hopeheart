package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.PsychologyEntity;
import com.boc.hopeheatapp.model.RescueEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zpwu3 on 2019-07-03.
 */
public interface KnowledgeService {

    @FormUrlEncoded
    @POST("http://172.20.10.4/knowledge/rescue/query")
    Observable<BaseResponse<RescueEntity>> queryRescueInfo(@Field("rescueType") String type,
                                                           @Field("rescueSubType") String subType);

    @FormUrlEncoded
    @POST("http://172.20.10.4/knowledge/psy/query")
    Observable<BaseResponse<PsychologyEntity>> queryPsychologyInfo(@Field("psyledgeType") String type,
                                                                   @Field("psyledgeSubtype") String subType);
}
