package com.boc.hopeheatapp.update;

import com.boc.hopeheatapp.http.BaseLoader;
import com.boc.hopeheatapp.http.DownloadServiceManager;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by qsy on 2018/1/23.
 */

public class DownloadLoader extends BaseLoader {
    private DownloadService service;

    public DownloadLoader(){
        service = DownloadServiceManager.getInstance().create(DownloadService.class);
    }

    /**
     * 下载APK
     * @param url
     * @return
     */
    public void download(String url, final FileCallBack<ResponseBody> callBack){
        service.download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        callBack.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(null,callBack));
    }
}
