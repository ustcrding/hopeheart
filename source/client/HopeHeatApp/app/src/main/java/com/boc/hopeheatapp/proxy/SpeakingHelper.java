package com.boc.hopeheatapp.proxy;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.boc.hopeheatapp.setting.BocSettings;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

public class SpeakingHelper {
    private static final int MAX_NUM = 1000;

    private SpeechSynthesizer mTts;
    private Context mContext;
    private String mText;
    private int mCurrentIndex;

    public SpeakingHelper(Context context) {
        mContext = context;
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null || error.getErrorCode() == 0) {
                if (!TextUtils.isEmpty(mText) && mText.length() > mCurrentIndex) {
                    if (mCurrentIndex + MAX_NUM > mText.length()) {
                        mTts.startSpeaking(mText.substring(mCurrentIndex), mTtsListener);
                        mCurrentIndex = mText.length();
                    } else {
                        mTts.startSpeaking(mText.substring(mCurrentIndex, mCurrentIndex + MAX_NUM), mTtsListener);
                        mCurrentIndex += MAX_NUM;
                    }
                }
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    public void start(String msg) {
        if (!BocSettings.getInstance().getBoolean(BocSettings.KEY_TTS_SWITH)) {
            return;
        }
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);

            mTts.setParameter(SpeechConstant.PARAMS, null);
            // 根据合成引擎设置相应参数
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
            //设置播放器音频流类型
            mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
            // 设置播放合成音频打断音乐播放，默认为true
            mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
            mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        }

        mText = msg;
        mCurrentIndex = 0;
        if (!TextUtils.isEmpty(mText)) {
            if (mText.length() <= MAX_NUM) {
                int code = mTts.startSpeaking(msg, mTtsListener);
                mCurrentIndex = mText.length();
            } else {
                int code = mTts.startSpeaking(mText.substring(mCurrentIndex, mCurrentIndex + MAX_NUM), mTtsListener);
                mCurrentIndex += MAX_NUM;
            }
        }
    }

    public void stop() {
        if (mTts != null) {
            mTts.stopSpeaking();
        }
    }

    public void pause() {
        if (mTts != null) {
            mTts.pauseSpeaking();
        }
    }

    public void resume() {
        if (mTts != null) {
            mTts.resumeSpeaking();
        }
    }
}
