package com.boc.hopeheatapp.vocal;

/**
 * 声纹识别结果回调
 *
 * @author dwl
 * @date 2018/2/13.
 */
public interface VocalVerifyListener {

    /**
     * 音量结果回调
     *
     * @param volumn
     */
    void onVocalVerifyVolumn(int volumn);

    /**
     * 声纹识别成功回掉
     *
     * @param authid
     */
    void onVocalVerifySuccess(String authid);

    /**
     * 声纹识别失败回掉
     *
     * @param code
     * @param msg
     */
    void onVocalVerifyFailed(int code, String msg);
}
