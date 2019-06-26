package com.boc.hopeheatapp.vocal;

/**
 * @author dwl
 * @date 2018/2/22.
 */
public interface VocalRegistListener {

    /**
     * 注册成功
     */
    void onRegisterSuccess();

    /**
     * 注册失败
     *
     * @param code
     * @param msg
     */
    void onRegisterFailed(int code, String msg);

    /**
     * 显示下一个声纹密码
     *
     * @param nowTimes
     * @param leftTimes
     * @param numPwdSeg
     */
    void onNextSegs(int nowTimes, int leftTimes, String numPwdSeg);

    /**
     * 注册时的音量回调
     *
     * @param volume
     */
    void onRegisterVoloum(int volume);
}
