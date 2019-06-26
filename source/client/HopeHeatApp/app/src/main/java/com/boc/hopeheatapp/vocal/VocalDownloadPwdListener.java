package com.boc.hopeheatapp.vocal;

/**
 * 声纹注册时下面声纹密码结果回调
 *
 * @author dwl
 * @date 2018/2/22.
 */
public interface VocalDownloadPwdListener {

    /**
     * 下载成功回掉
     *
     * @param numPwd
     * @param numPwdSegs
     */
    void onDownloadSuccess(String numPwd, String[] numPwdSegs);

    /**
     * 下载失败时回调
     *
     * @param code
     * @param msg
     */
    void onDownloadError(int code, String msg);
}
