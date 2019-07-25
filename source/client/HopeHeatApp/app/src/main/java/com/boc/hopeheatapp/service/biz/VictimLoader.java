package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.AreaEntity;
import com.boc.hopeheatapp.model.VictimBaseEntity;
import com.boc.hopeheatapp.model.VictimEntity;
import com.boc.hopeheatapp.service.api.VictimService;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zpwu3 on 2019-07-24.
 */
public class VictimLoader extends BaseLoader {
    private VictimService service;

    public VictimLoader() {
        service = UpdateServiceManager.getInstance().create(VictimService.class);
    }

    public Observable<VictimBaseEntity> getCoachedLis(String doctorId, String address, int startNo) {
        return observe(service.getCoachedLis(doctorId, address, startNo)).map(new Func1<BaseResponse<VictimBaseEntity>, VictimBaseEntity>() {
            @Override
            public VictimBaseEntity call(BaseResponse<VictimBaseEntity> victimBaseEntityBaseResponse) {
                victimBaseEntityBaseResponse.throwExceptionIfError();
                return victimBaseEntityBaseResponse.getData();
            }
        });
    }

    public Observable<VictimEntity> getCoachedDetail(String id) {
        return observe(service.getCoachedDetail(id)).map(new Func1<BaseResponse<VictimEntity>, VictimEntity>() {
            @Override
            public VictimEntity call(BaseResponse<VictimEntity> victimBaseEntityBaseResponse) {
                victimBaseEntityBaseResponse.throwExceptionIfError();
                return victimBaseEntityBaseResponse.getData();
            }
        });
    };

    public Observable<Void> markCoached(String doctorId, String volunteerId, String markType, int num, String ids) {
        return observe(service.markCoached(doctorId, volunteerId, markType, num, ids)).map(new Func1<BaseResponse<Void>, Void>() {
            @Override
            public Void call(BaseResponse<Void> victimBaseEntityBaseResponse) {
                victimBaseEntityBaseResponse.throwExceptionIfError();
                return victimBaseEntityBaseResponse.getData();
            }
        });
    };


    public Observable<AreaEntity> getAresInfo(String doctorId, String volunteerId) {
        return observe(service.getAresInfo(doctorId, volunteerId)).map(new Func1<BaseResponse<AreaEntity>, AreaEntity>() {
            @Override
            public AreaEntity call(BaseResponse<AreaEntity> victimBaseEntityBaseResponse) {
                victimBaseEntityBaseResponse.throwExceptionIfError();
                return victimBaseEntityBaseResponse.getData();
            }
        });
    };

}
