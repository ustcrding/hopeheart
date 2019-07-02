package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.api.UserService;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author dwl
 * @date 2018/2/23.
 */
public class UserLoader extends BaseLoader {
    private UserService service;

    public UserLoader() {
        service = UpdateServiceManager.getInstance().create(UserService.class);
    }

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @return
     */
    public Observable<UserEntity> register(String username, String password) {

        return observe(service.register(username, StringUtil.MD5(password))).map(new Func1<BaseResponse<UserEntity>, UserEntity>() {
            @Override
            public UserEntity call(BaseResponse<UserEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }

    /**
     * 用户注册
     *
     * @param authId
     * @param groudId
     * @return
     */
    public Observable<Void> registerVocal(String authId, String groudId) {
        return observe(service.registerVocal(authId, groudId)).map(new Func1<BaseResponse<Void>, Void>() {
            @Override
            public Void call(BaseResponse<Void> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }

    /**
     * 使用用户名密码登录
     *
     * @param username
     * @param password
     * @return
     */
    public Observable<UserEntity> login(String username, String password) {
        return observe(service.login(username, StringUtil.MD5(password)/*, null, UserService.LOGIN_BY_PWD*/)).map(new Func1<BaseResponse<UserEntity>, UserEntity>() {
            @Override
            public UserEntity call(BaseResponse<UserEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }

    /**
     * 使用声纹登录
     *
     * @param authId
     * @return
     */
    public Observable<UserEntity> loginByVocal(String authId) {
        return observe(service.login(null, null/*, authId, UserService.LOGIN_BY_VOCAL*/)).map(new Func1<BaseResponse<UserEntity>, UserEntity>() {
            @Override
            public UserEntity call(BaseResponse<UserEntity> userEntityBaseResponse) {
                userEntityBaseResponse.throwExceptionIfError();
                return userEntityBaseResponse.getData();
            }
        });
    }
}