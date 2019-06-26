package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.RetrofitServiceManager;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.CatalogListEntity;
import com.boc.hopeheatapp.service.api.CatalogService;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author dwl
 * @date 2018/5/28.
 */
public class CatalogLoader extends BaseLoader {
    private CatalogService service;

    public CatalogLoader() {
        service = UpdateServiceManager.getInstance().create(CatalogService.class);
    }

    /**
     * 获取目录列表
     *
     * @return
     */
    public Observable<CatalogListEntity> get() {
        return observe(service.get()).map(new Func1<BaseResponse<CatalogListEntity>, CatalogListEntity>() {
            @Override
            public CatalogListEntity call(BaseResponse<CatalogListEntity> catalogListEntityBaseResponse) {
                catalogListEntityBaseResponse.throwExceptionIfError();
                return catalogListEntityBaseResponse.getData();
            }
        });
    }
}
