package com.boc.hopeheatapp.http;

import android.text.TextUtils;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.api.ApiFiled;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.log.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qsy on 2018/1/19.
 */
public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 30;//超时时间 30s
    private static final int DEFAULT_READ_TIME_OUT = 30;
    private static final String TAG = "RetrofitServiceManager";
    private Retrofit mRetrofit;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        // 添加公共参数拦截器
        HttpCommonInterceptor.Builder commonBuilder = new HttpCommonInterceptor.Builder()
                .addHeaderParams(ApiFiled.PALTFORM, ApiFiled.ANDROID);

        HttpCommonInterceptor commonInterceptor = commonBuilder.build();
        builder.addInterceptor(commonInterceptor);
        builder.addInterceptor(requestInterceptor);

        if (ApiConfig.DEBUG) {
            // 添加日志拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (Logger.isDebugable()) {
                        Logger.d(TAG, "OkHttp: " + message);
                    }
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        String baseUrl = ApiConfig.KB_BASE_URL;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(android.os.Environment.getExternalStorageDirectory() + "/hopeheart.txt")));
            String url = br.readLine();
            if (!TextUtils.isEmpty(url) && (url.startsWith("http"))) {
                baseUrl = url + ":9090";
            }
        } catch (FileNotFoundException e) {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                }
            }
        } catch (IOException e) {
        }

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
            /*authorizedUrlBuilder.setQueryParameter(ApiFiled.APP_ID, ApiConfig.getAppId());
            if (UserManager.getInstance() != null) {
                UserEntity userEntity = UserManager.getInstance().getUser();
                if (userEntity != null) {
                    int userId = userEntity.getUserId();
                    String username = userEntity.getUsername();
                    String token = "" + userId;
                    String roleId = userEntity.getRoleId();

                    authorizedUrlBuilder.setQueryParameter(ApiFiled.USER_ID, "" + userId);
                    if (StringUtil.isNotEmpty(token)) {
                        authorizedUrlBuilder.setQueryParameter(ApiFiled.USER_TOKEN, token);
                    }
                    if (StringUtil.isNotEmpty(username)) {
                        authorizedUrlBuilder.setQueryParameter(ApiFiled.USER_NAME, username);
                    }
                    if (StringUtil.isNotEmpty(roleId)) {
                        authorizedUrlBuilder.setQueryParameter(ApiFiled.ROLE_ID, roleId);
                    }
                }
            }*/
            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .header("accept", "application/json")
                    .url(authorizedUrlBuilder.build())
                    .build();

            return chain.proceed(newRequest);
        }
    };

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
