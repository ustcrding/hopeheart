package com.boc.hopeheatapp.vocal;

import android.content.Context;
import android.os.Bundle;

import com.boc.hopeheatapp.util.log.Logger;
import com.iflytek.cloud.IdentityListener;
import com.iflytek.cloud.IdentityResult;
import com.iflytek.cloud.IdentityVerifier;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.record.PcmRecorder;
import com.iflytek.cloud.util.VerifierUtil;

/**
 * 抽象的声纹识别接口
 *
 * @author dwl
 * @date 2018/2/13.
 */
public abstract class AbsVocalVerifier {

    protected static final int PWD_TYPE_NUM = 3;
    private static final String TAG = "AbsVocalVerifier";

    // 密码类型
    // 默认为数字密码
    protected int mPwdType = PWD_TYPE_NUM;
    // 数字密码类型为3，其他类型暂未开放

    // 身份验证对象
    protected IdentityVerifier mIdVerifier;

    protected Context mContext;
    // pcm录音机
    protected PcmRecorder mPcmRecorder;
    // 录音采样率
    protected final int SAMPLE_RATE = 16000;

    protected boolean mIsWorking = false;
    protected boolean mCanIdentify = false;
    protected String mVerifyNum;

    protected VocalVerifyListener mVocalVerifyListener;


    protected AbsVocalVerifier(Context context) {
        mContext = context;
        mVerifyNum = genVerifyNum();
        init();
    }

    private void init() {
        mIdVerifier = IdentityVerifier.createVerifier(mContext, new InitListener() {

            @Override
            public void onInit(int errorCode) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "init | errorCode = " + errorCode);
                }
            }
        });
    }

    public String genVerifyNum() {
        mVerifyNum = VerifierUtil.generateNumberPassword(8);
        return mVerifyNum;
    }

    public String getVerifyNum() {
        return mVerifyNum;
    }

    public void startSpeech(String groupId, String verifyNum, VocalVerifyListener vocalVerifyListener) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "startSpeech | groupId = " + groupId + ", verifyNum = " + verifyNum);
        }

        cancle();
        mVerifyNum = verifyNum;
        mVocalVerifyListener = vocalVerifyListener;

        // 根据业务类型调用服务
        vocalVerify(groupId, verifyNum, vocalVerifyListener);
        mIsWorking = true;
        mCanIdentify = true;

        try {
            mPcmRecorder = new PcmRecorder(SAMPLE_RATE, 40);
            mPcmRecorder.startRecording(mPcmRecordListener);
        } catch (SpeechError e) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "startSpeech error", e);
            }
        }
    }

    public void cancle() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "cancle");
        }

        if (mIsWorking) {
            mIdVerifier.cancel();
            mIsWorking = false;
        }

        if (null != mPcmRecorder) {
            mPcmRecorder.stopRecord(true);
        }
    }

    public void stopSpeech() {
        mIdVerifier.stopWrite("ivp");
        if (null != mPcmRecorder) {
            mPcmRecorder.stopRecord(true);
            mIsWorking = false;
        }
    }

    /**
     * 录音机监听器
     */
    private PcmRecorder.PcmRecordListener mPcmRecordListener = new PcmRecorder.PcmRecordListener() {

        @Override
        public void onRecordStarted(boolean success) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "PcmRecorder.onRecordStarted | success = " + success);
            }
        }

        @Override
        public void onRecordReleased() {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "PcmRecorder.onRecordReleased");
            }
        }

        @Override
        public void onRecordBuffer(byte[] data, int offset, int length) {
            handleRecordBuffer(data, offset, length);
        }

        @Override
        public void onError(SpeechError e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "PcmRecorder.onRecordBuffer ", e);
            }
        }
    };

    /**
     * 声纹鉴别监听器
     */
    protected IdentityListener mIdentityListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, result.getResultString());
            }

            mIsWorking = false;

            handleResult(result);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
//            if (Logger.isDebugable()) {
//                Logger.d(TAG, "IdentityListener.onEvent | eventType = " + eventType + ", arg1 = " + arg1 + ", arg2 = " + arg2);
//            }

            if (SpeechEvent.EVENT_VOLUME == eventType) {
                if (mVocalVerifyListener != null) {
                    mVocalVerifyListener.onVocalVerifyVolumn(arg1);
                }
            } else if (SpeechEvent.EVENT_VAD_EOS == eventType) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "IdentityListener.onEvent | 录音结束");
                }
            }
        }

        @Override
        public void onError(SpeechError error) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "IdentityListener.onError | " + error.getPlainDescription(true));
            }
            mCanIdentify = false;
            mIsWorking = false;

            if (mVocalVerifyListener != null) {
                mVocalVerifyListener.onVocalVerifyFailed(error.getErrorCode(), error.getErrorDescription());
            }
        }
    };

    /**
     * 处理录音结果
     *
     * @param data
     * @param offset
     * @param length
     */
    protected abstract void handleRecordBuffer(byte[] data, int offset, int length);

    protected abstract void vocalVerify(String groupId, String verifyNum, VocalVerifyListener vocalVerifyListener);

    /**
     * 处理识别结果
     *
     * @param result
     */
    protected abstract void handleResult(IdentityResult result);
}

