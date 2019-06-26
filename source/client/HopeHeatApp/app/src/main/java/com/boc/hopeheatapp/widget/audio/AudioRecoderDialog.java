package com.boc.hopeheatapp.widget.audio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.util.ThreadPoolManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.iflytek.aipsdk.asr.RecognizerListener;

/**
 * 录音页面
 *
 * @author dwl
 * @date 2018/08/14.
 */
public class AudioRecoderDialog extends BasePopupWindow {

    private static final String TAG = "AudioRecoderDialog";

    private com.iflytek.aipsdk.asr.SpeechRecognizer mIat;

    private RecognizerListener recognizerListener;

    private ImageView imageView;
    private TextView textView;
    private ProgressBar pbRecStatus;

    private boolean isStart = false;

    public AudioRecoderDialog(Context context) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_recoder_dialog, null);
        imageView = (ImageView) contentView.findViewById(R.id.iv_mic);
        textView = (TextView) contentView.findViewById(R.id.tv_status);
        pbRecStatus = (ProgressBar) contentView.findViewById(R.id.pb_recoginizing);
        setContentView(contentView);

        mIat = com.iflytek.aipsdk.asr.SpeechRecognizer.createRecognizer(getContext(), mInitListener);

        setOutsideTouchable(true);

        //showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    public void setLevel(int level) {
        Drawable drawable = imageView.getDrawable();
        drawable.setLevel(100 * level);
    }

    public void setTime(long time) {
        textView.setText(ProgressTextUtils.getProgressText(time));
    }

    /**
     * 初始化监听器。
     */
    private com.iflytek.aipsdk.common.InitListener mInitListener = new com.iflytek.aipsdk.common.InitListener() {

        @Override
        public void onInit(int code) {
            Logger.d(TAG, "[" + Thread.currentThread().getName() + "][" + TAG + "][InitListener] " + "SpeechRecognizer init() code = " + code);
            if (code != com.iflytek.aipsdk.util.ErrorCode.SUCCESS) {
                Logger.d(TAG, "初始化失败,错误码：" + code);
            }
        }
    };

    private int max_vol = 0;


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            //showTip("开始说话");
            if (Logger.isDebugable()) {
                Logger.d(TAG, "onBeginOfSpeech");
            }
            change2Listeningtatus();
            if (recognizerListener != null) {
                recognizerListener.onBeginOfSpeech();
            }
        }

        @Override
        public void onError(com.iflytek.aipsdk.util.SpeechError error) {
            if (null != error) {
                //showTip(error.getPlainDescription(true));
                Logger.d(TAG, "[" + Thread.currentThread().getName() + "][" + TAG + "][onError] " + " error:" + error);
            }
            if (recognizerListener != null) {
                recognizerListener.onError(error);
            }
            isStart = false;

            change2MicInitStatus();
        }

        @Override
        public void onEndOfSpeech() {
            //showTip("结束说话");
            isStart = false;

            if (Logger.isDebugable()) {
                Logger.d(TAG, "onEndOfSpeech");
            }

            if (recognizerListener != null) {
                recognizerListener.onEndOfSpeech();
            }

            change2MicInitStatus();
            dismiss();
        }

        @Override
        public void onResult(com.iflytek.aipsdk.asr.RecognizerResult results, boolean isLast) {
            String text = results.getResultString();
            if (Logger.isDebugable()) {
                Logger.d(TAG, "onResult | result = " + text + ", isLast = " + isLast);
            }
            if (recognizerListener != null) {
                recognizerListener.onResult(results, isLast);
            }

        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "onVolumeChanged | volume = " + volume + ", max_vol = " + max_vol + ", threadId " + Thread.currentThread().getName());
            }
            //max_vol = volume;
            if (!isStart) {
                return;
            }

            if (volume > max_vol + 5) {
                //showTip("当前正在说话，音量大小：" + volume);
                setLevel(volume);
            } else if (volume < max_vol - 5) {
                //showTip("当前正在说话，音量大小：" + volume);
                setLevel(volume);
            } else {
                // showTip("当前正在说话，音量大小：" + volume);
                setLevel(volume);
            }

            max_vol = volume;

            if (recognizerListener != null) {
                recognizerListener.onVolumeChanged(volume, data);
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            if (recognizerListener != null) {
                recognizerListener.onEvent(eventType, arg1, arg2, obj);
            }
        }

        @Override
        public void onWakeUp(final String str, final int code) {
            isStart = false;

            if (recognizerListener != null) {
                recognizerListener.onWakeUp(str, code);
            }
        }
    };

    public int startListening(RecognizerListener recognizerListener) {
        if (!isStart) {
            isStart = true;
            change2MicInitStatus();
            this.recognizerListener = recognizerListener;
            setSrpParams();
            int ret = mIat.startListening(mRecognizerListener);
            TtsManager.getInstance(getContext().getApplicationContext()).stop();
            return ret;
        } else {
            return -1;
        }
    }


    private void setSrpParams() {
        mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.PARAMS, null);

        StringBuffer params = new StringBuffer();
        params.append("extend_params={\"params\":\"eos=1000,bos=4000\"},");
        params.append("appid=pc20onli").append(",");
        params.append("url=").append(ApiConfig.SRP_URL).append(",");
        params.append("time_out=3").append(",");
        params.append("svc=iat").append(",");
        params.append("auf=audio/L16;rate=16000").append(",");
        params.append("aue=raw").append(",");
        params.append("type=1").append(",");
        params.append("uid=660Y5r").append(",");
        params.append("output=").append(Environment.getExternalStorageDirectory() + "/boc").append(",");
        //paramBuffer.append();

        mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.PARAM, params.toString());

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        //mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        //mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        //mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        //mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.AUDIO_FORMAT, "wav");
        //mIat.setParameter(com.iflytek.aipsdk.util.SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");

    }

    public void cancel() {
        if (mIat != null) {
            mIat.cancel();
        }
    }

    /**
     * 销毁语音识别sdk
     */
    public void destroyRecongizer() {
        if (mIat != null) {
            mIat.cancel();
            mIat.destroy();
            mIat = null;
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }

        if (mIat != null && mIat.isListening()) {
            mIat.stopListening();
        }
    }

    public void stopListening() {
        if (isShowing()) {
            //dismiss();
            change2RecognizingStatus();
        }

        if (mIat != null && mIat.isListening()) {
            mIat.stopListening();
        }
    }

    private void change2MicInitStatus() {
        imageView.setVisibility(View.VISIBLE);
        pbRecStatus.setVisibility(View.GONE);
        textView.setText(R.string.mic_init);
    }

    private void change2RecognizingStatus() {
        imageView.setVisibility(View.GONE);
        pbRecStatus.setVisibility(View.VISIBLE);
        textView.setText(R.string.mic_recognizing);
    }

    private void change2Listeningtatus() {
        imageView.setVisibility(View.VISIBLE);
        pbRecStatus.setVisibility(View.GONE);
        textView.setText(R.string.mic_listening);

    }
}
