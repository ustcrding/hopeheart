package com.boc.hopeheatapp.service.biz;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.UpdateServiceManager;
import com.boc.hopeheatapp.service.api.UploadFileService;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;


/**
 * 文件上传的loader
 *
 * @author dwl
 * @date 2018/2/28.
 */
public class UploadFileLoader extends BaseLoader {

    private final String KEY_UPLOADS = "uploads";
    private final String KEY_VOLS_CONTENT = "vols_content";
    private UploadFileService service;

    public UploadFileLoader() {
        service = UpdateServiceManager.getInstance().create(UploadFileService.class);
    }

    /**
     * 文件上传
     *
     * @param filapath
     * @return
     */
    public Observable<String> upload(String content, String filapath) {
        File file = new File(filapath);
        RequestBody photoBody = RequestBody.create(MultipartBody.FORM, file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart(KEY_UPLOADS, file.getName(), photoBody)
                .addFormDataPart(KEY_VOLS_CONTENT, content)
                .build();

        return observe(service.upload(multipartBody)).map(new Func1<BaseResponse<String>, String>() {
            @Override
            public String call(BaseResponse<String> baseResponse) {
                baseResponse.throwExceptionIfError();
                return baseResponse.getData();
            }
        });
    }

}
