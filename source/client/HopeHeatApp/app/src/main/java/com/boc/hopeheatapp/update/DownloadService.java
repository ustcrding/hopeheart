package com.boc.hopeheatapp.update;

import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.VersionEntity;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by qsy on 2018/1/23.
 */

public interface DownloadService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
