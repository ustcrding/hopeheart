package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.VoidResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.HotQuestionListEntity;
import com.boc.hopeheatapp.model.NaviEntity;
import com.boc.hopeheatapp.model.VersionEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qsy on 2018/1/19.
 */
public interface RobotService {
    //@FormUrlEncoded
    @POST("ask")
    Observable<BaseResponse<AnswerEntity>> ask(@Body RequestBody body);

    @POST("mark")
    Observable<VoidResponse> mark(@Body RequestBody body);

    @POST("select")
    Observable<VoidResponse> select(@Body RequestBody body);

    @POST("navi")
    Observable<BaseResponse<NaviEntity>> navi(@Body RequestBody body);

    /**
     * 请求热门问题
     *
     * @return
     */
    @POST("hotQuestion")
    Observable<BaseResponse<HotQuestionListEntity>> hotQuestion(@Body RequestBody body);

    /**
     * 获取最新版本信息
     *
     * @param curVersionCode
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConfig.UPDATE + "A03!getLastVersion")
    Observable<BaseResponse<VersionEntity>> getLastVersion(@Field("curVersionCode") int curVersionCode);
}
