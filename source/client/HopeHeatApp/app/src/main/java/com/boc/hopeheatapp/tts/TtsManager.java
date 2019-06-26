package com.boc.hopeheatapp.tts;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.util.log.Logger;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * tts管理类，用于控制tts播报
 *
 * @author dwl
 * @date 2018/1/25.
 */
public class TtsManager {

    private static final String TAG = "TtsManager";
    private Context context;
    private static TtsManager instance;
    // 引擎类型
    private String engineType = SpeechConstant.TYPE_CLOUD;
    // 语音合成对象
    private SpeechSynthesizer speechSynthesizer;
    // 默认发音人
    private String voicer = "xiaoyan";

    private TtsManager(Context context) {
        this.context = context;
        init();
    }

    public static TtsManager getInstance(Context context) {
        if (instance == null) {
            synchronized (TtsManager.class) {
                if (instance == null) {
                    instance = new TtsManager(context);
                }
            }
        }
        return instance;
    }

    private void init() {
        // 初始化合成对象
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);

        setParam();
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "InitListener init() code = " + code);
            }
            if (code != ErrorCode.SUCCESS) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "初始化失败,错误码：" + code);
                }
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        speechSynthesizer.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (engineType.equals(SpeechConstant.TYPE_CLOUD)) {
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        speechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        speechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        speechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener ttsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "开始播放");
            }
        }

        @Override
        public void onSpeakPaused() {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "暂停播放");
            }
        }

        @Override
        public void onSpeakResumed() {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "继续播放");
            }
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
//            // 合成进度
//            percentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    percentForBuffering, percentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
//            percentForPlaying = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    percentForBuffering, percentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "播放完成");
                }
            } else if (error != null) {
                Logger.d(TAG, error.getPlainDescription(true));
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

    public int speak(String text, SynthesizerListener listener) {
        if (BocSettings.getInstance().getBoolean(BocSettings.KEY_TTS_SWITH)) {
            int code = speechSynthesizer.startSpeaking(text, listener);
            return code;
        } else {
            return -1;
        }
    }

    public void stop() {
        if (speechSynthesizer != null && speechSynthesizer.isSpeaking()) {
            speechSynthesizer.stopSpeaking();
        }
    }

}
