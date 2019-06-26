package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.http.BaseResponse;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 文件上传的service
 *
 * @author dwl
 * @date 2018/2/28.
 */
public interface UploadFileService {

    /**
     * 用户音频文件上传接口
     * @param multipartBody
     * @return
     */
    @POST("A03!uploadAudio")
    Observable<BaseResponse<String>> upload(@Body MultipartBody multipartBody);

}
