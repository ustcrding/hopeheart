package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.ApiConfig;
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
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/knowledge/rescue/query")
    Observable<BaseResponse<RescueEntity>> queryRescueInfo(@Field("rescueType") String type,
                                                           @Field("rescueSubType") String subType);

    @FormUrlEncoded
    @POST(ApiConfig.HOPE_HEAT_BASE_URL + "/knowledge/psy/query")
    Observable<BaseResponse<PsychologyEntity>> queryPsychologyInfo(@Field("psyledgeType") String type,
                                                                   @Field("psyledgeSubtype") String subType);
}
