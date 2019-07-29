package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AiAnswerEntity;
import com.boc.hopeheatapp.model.ConsultEntity;
import com.boc.hopeheatapp.model.DoctorEntity;
import com.boc.hopeheatapp.model.UserEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 用户操作接口
 *
 * @author dwl
 * @date 2018/2/23.
 */
public interface AiService {

    @FormUrlEncoded
    @POST("/ai-doctor/cure")
    Observable<BaseResponse<AiAnswerEntity>> queryAnswer(@Field("query") String query,
                                                         @Field("session_id") String sessionId);
}
