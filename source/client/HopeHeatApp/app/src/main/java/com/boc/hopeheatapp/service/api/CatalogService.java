package com.boc.hopeheatapp.service.api;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.CatalogListEntity;

import retrofit2.http.POST;
import rx.Observable;

/**
 * 知识库目录结构请求接口
 *
 * @author dwl
 * @date 2018/5/28.
 */
public interface CatalogService {

    @POST(ApiConfig.KB_BASE_URL + "catalog")
    Observable<BaseResponse<CatalogListEntity>> get();
}
