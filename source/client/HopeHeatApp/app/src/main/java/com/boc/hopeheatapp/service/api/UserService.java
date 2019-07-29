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
public interface UserService {

    /**
     * 用户名密码登录
     */
    public static final int LOGIN_BY_PWD = 1;

    /**
     * 声纹登录
     */
    public static final int LOGIN_BY_VOCAL = 2;

    /***
     * 用户注册接口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("A04!register")
    Observable<BaseResponse<UserEntity>> register(@Field("username") String username,
                                                  @Field("password") String password);

    /***
     * 用户注册接口
     * @param authId 密码
     * @param groupId
     * @return
     */
    @FormUrlEncoded
    @POST("A04!registerVocal")
    Observable<BaseResponse<Void>> registerVocal(@Field("authId") String authId,
                                                 @Field("groupId") String groupId);

    /***
     * 用户登录接口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("/login")
    Observable<BaseResponse<UserEntity>> login(@Field("username") String username,
                                               @Field("password") String password);


    @FormUrlEncoded
    @POST("/victim/collect")
    Observable<BaseResponse<Void>> uploadUserInfo(@Field("id") String userId,
                                                        @Field("name") String userName,
                                                        @Field("sex") String sex,
                                                        @Field("identityType") String identityType,
                                                        @Field("certificateCode") String certificateCode,
                                                        @Field("phone") String phone,
                                                        @Field("province") String province,
                                                        @Field("city") String city,
                                                        @Field("address") String address,
                                                        @Field("memo") String memo);
    /***
     * 通过物联网设备绑定用户信息
     * @param id 用户id
     * @return
     */
    @FormUrlEncoded
    @POST("/verify")
    Observable<BaseResponse<UserEntity>> userBind(@Field("userId") String id);

    /***
     * 上传评测结果
     * @return
     */
    @FormUrlEncoded
    @POST("/psytest/evaluation")
    Observable<BaseResponse<ConsultEntity>> uploadEvaluationResult(@Field("victimId") String victimId,
                                                                   @Field("psytestsId") String testiId,
                                                                   @Field("testsLevel") String testLevel,
                                                                   @Field("testioinDate") String date,
                                                                   @Field("testioinTime") String time,
                                                                   @Field("addressCode") String address);
    /***
     * 上传评测结果
     * @return
     */
    @FormUrlEncoded
    @POST("/psytest/recommend/doctor")
    Observable<BaseResponse<DoctorEntity>> queryDoctor(@Field("victimId") String victimId,
                                                       @Field("psytestsId") String testiId,
                                                       @Field("victimtestId") String victimtestId,
                                                       @Field("testsLevel") String testLevel,
                                                       @Field("testionDate") String date,
                                                       @Field("testionTime") String time,
                                                       @Field("addressCode") String address);
    /***
     * 反馈满意度
     * @return
     */
    @FormUrlEncoded
    @POST("/psyQuery/feedback")
    Observable<BaseResponse<Void>> uploadComment(@Field("victimId") String victimId,
                                                 @Field("victimTestId") String testId,
                                                 @Field("doctorId") String doctorId,
                                                 @Field("satisficing") String satisficing);

}
