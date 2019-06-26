package com.boc.hopeheatapp.conversation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.navi.NaviInterface;
import com.boc.hopeheatapp.util.JsonParser;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.AnswerView;
import com.boc.hopeheatapp.widget.AskView;
import com.boc.hopeheatapp.widget.ChannelBarView;
import com.boc.hopeheatapp.widget.VoiceGuideView;
import com.boc.hopeheatapp.widget.audio.AudioRecoderDialog;
import com.boc.hopeheatapp.widget.channel.GridItemClickListener;
import com.iflytek.aipsdk.asr.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会话view
 *
 * @author dwl
 * @date 2018/2/11.
 */
public abstract class ConversationView extends LinearLayout implements VoiceGuideView.GuideItemClickedListener {

    private static final String TAG = "ConversationView";

    private LinearLayout btnSpeechVoiceMic;
    private TextView btnSpeechVoiceToText;

    //语音输入框
    private View voiceInputContainer;
    //文本输入框
    private View textInputContainer;
    // 文本输入框中的麦克风按钮
    private Button btnSpeechTextMic;
    //文本输入框
    private EditText etSpeechTextInput;
    //文本输入框中发送按钮
    private Button btnSpeechTextSend;

    private ScrollView scrollView;
    private LinearLayout conversationContainer;
    private VoiceGuideView voiceGuideContainer;

    /**
     * 频道网格视图
     */
    private ChannelBarView channelBarView;

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private StringBuffer mIatResultBuffer = new StringBuffer();

    private boolean isStart = false;

    protected Map<String, NaviInterface> navigator;

    /**
     * 频道信息
     */
    protected ChannelEntity channelEntity;

    private AudioRecoderDialog audioRecoderDialog;

    private Handler handler = new Handler();

    public ConversationView(Context context) {
        super(context);

        initView();
        initData();
    }

    public ConversationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
        initData();
    }

    public ConversationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        initData();
    }

    private void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_conversation, this, true);

        voiceInputContainer = view.findViewById(R.id.rl_voice_container);
        btnSpeechVoiceMic = (LinearLayout) view.findViewById(R.id.btn_speech_voice_mic);
        btnSpeechVoiceToText = (TextView) view.findViewById(R.id.btn_speech_voice_to_text);

        textInputContainer = view.findViewById(R.id.rl_text_container);
        btnSpeechTextMic = (Button) view.findViewById(R.id.btn_speech_text_mic);
        etSpeechTextInput = (EditText) view.findViewById(R.id.tv_speech_text_input);
        btnSpeechTextSend = (Button) findViewById(R.id.btn_speech_text_send);

        conversationContainer = (LinearLayout) view.findViewById(R.id.ll_conversation_container);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        voiceGuideContainer = (VoiceGuideView) view.findViewById(R.id.ll_voice_guide);

        channelBarView = (ChannelBarView) view.findViewById(R.id.ll_channel_bar_view);
    }

    private void initData() {
        initVoiceGuide();

        initChannel();

        initSpeechRecognize();

        addListener();
    }

    /**
     * 初始化语音识别sdk
     */
    private void initSpeechRecognize() {
        audioRecoderDialog = new AudioRecoderDialog(getContext());
        audioRecoderDialog.setShowAlpha(0.98f);
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

    /**
     * 听写监听器。
     */
    private RecognizerListener recognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            //showTip("开始说话");
        }

        @Override
        public void onError(com.iflytek.aipsdk.util.SpeechError error) {
            if (null != error) {
                showTip(error.getPlainDescription(true));
                Logger.d(TAG, "[" + Thread.currentThread().getName() + "][" + TAG + "][onError] " + " error:" + error);
            }
            isStart = false;
            if (audioRecoderDialog != null) {
                audioRecoderDialog.dismiss();
            }
        }

        @Override
        public void onEndOfSpeech() {
            //showTip("结束说话");
            isStart = false;

            if (Logger.isDebugable()) {
                Logger.d(TAG, "onEndOfSpeech");
            }

            cancleTip();
            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }

            String content = resultBuffer.toString();
            etSpeechTextInput.setText(content);
            startRequest(content, MsgEntity.SOURCE_VOICE);

            audioRecoderDialog.dismiss();
        }

        @Override
        public void onResult(com.iflytek.aipsdk.asr.RecognizerResult results, boolean isLast) {
            String text = results.getResultString();
            if (Logger.isDebugable()) {
                Logger.d(TAG, "onResult | result = " + text + ", isLast = " + isLast);
            }
            if (!StringUtil.isEmpty(text)) {
                handleRecognizeResult2(results.getResultString(), isLast);
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }

        @Override
        public void onWakeUp(final String str, final int code) {
            isStart = false;
        }
    };

    /**
     * 添加监听器
     */
    private void addListener() {
//        btnSpeechVoiceMic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickedStartRecognize();
//            }
//        });

        btnSpeechTextMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpeechVoiceView();
                onClickedStartRecognize();
            }
        });

        btnSpeechVoiceToText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showSpeechTextView();
            }
        });

        btnSpeechTextSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickedSend();
            }
        });

        voiceGuideContainer.setGuideItemClickedListener(this);

        btnSpeechVoiceMic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return onTouchVoiceMic(motionEvent);
            }
        });
    }

    public static final int MSG_CHECK_LONGPRESS = 1000;
    public static final int MSG_START_RECORD = 1001;
    private static final int MSG_CHECK_ANIMATION = 1002;
    private boolean mBeginToTestLongPress;
    private boolean mHasLongPress;

    private boolean onTouchVoiceMic(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "MotionEvent.ACTION_DOWN");
                }
                btnSpeechVoiceMic.setBackgroundResource(R.drawable.sharp_mic_pressed);
                mBeginToTestLongPress = true;
                mHandler.sendEmptyMessage(MSG_START_RECORD);
                mHandler.sendEmptyMessageDelayed(MSG_CHECK_LONGPRESS, ViewConfiguration.getLongPressTimeout());
                break;
            case MotionEvent.ACTION_UP:
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "MotionEvent.ACTION_UP");
                }
                btnSpeechVoiceMic.setBackgroundResource(R.drawable.sharp_mic_normal);
                if (mHasLongPress) {
                    if (Logger.isDebugable()) {
                        Logger.d(TAG, "MotionEvent.ACTION_UP | mHasLongPress = true");
                    }
                    mHasLongPress = false;
                    stopSpeech();
                } else {
                    mBeginToTestLongPress = false;
                    mHandler.removeMessages(MSG_CHECK_LONGPRESS);
                    //stopSpeech();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "MotionEvent.ACTION_UP");
                }
                btnSpeechVoiceMic.setBackgroundResource(R.drawable.sharp_mic_normal);
                mBeginToTestLongPress = false;
                mHandler.removeMessages(MSG_CHECK_LONGPRESS);
                if (mHasLongPress) {
                    stopSpeech();
                    mHasLongPress = false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CHECK_LONGPRESS:
                    if (mBeginToTestLongPress) {
                        mHasLongPress = true;
                        //sendEmptyMessage(MSG_START_RECORD);
                    }
                    break;
                case MSG_START_RECORD:
                    startSpeech();
                    break;
//                case MSG_CHECK_ANIMATION:
//                    ivVolume.setBackgroundResource(R.drawable.volume);
//                    ((AnimationDrawable) ivVolume.getBackground()).start();
                default:
                    break;
            }
        }
    };

    private void startSpeech() {
        showSpeechVoiceView();
        onClickedStartRecognize();
    }

    private void stopSpeech() {
        if (audioRecoderDialog != null) {
            audioRecoderDialog.stopListening();
        }
    }

    private void showSpeechTextView() {
        voiceInputContainer.setVisibility(View.GONE);
        textInputContainer.setVisibility(View.VISIBLE);
    }

    private void showSpeechVoiceView() {
        voiceInputContainer.setVisibility(View.VISIBLE);
        textInputContainer.setVisibility(View.GONE);
    }

    private void onClickedSend() {
        String text = etSpeechTextInput.getText().toString();
        if (StringUtil.isEmpty(text)) {
            Toast.makeText(getContext(), R.string.public_tip_please_input_content, Toast.LENGTH_LONG).show();
            return;
        }

        startRequest(text, MsgEntity.SOURCE_TEXT);
        etSpeechTextInput.setText("");
    }

    /**
     * 点击开始识别按钮
     */
    private void onClickedStartRecognize() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "onClickedStartRecognize");
        }

        mIatResults.clear();
        mIatResultBuffer.delete(0, mIatResultBuffer.length());
//        // 设置参数
//        setParam();
//
//

//        setSrpParams();
//
//        startRecognizeWithDialog();
//
//        TtsManager.getInstance(getContext().getApplicationContext()).stop();


        audioRecoderDialog.startListening(recognizerListener);
        audioRecoderDialog.showAtLocation(this, Gravity.CENTER, 0, 0);

        //showTip(getContext().getString(R.string.text_begin));
    }

    /**
     * 开始说活，使用讯飞语音识别的对话框
     */
    private void startRecognizeWithDialog() {
        int error = 0;
        if (!isStart) {
            mIatResults.clear();
            mIatResultBuffer.delete(0, mIatResultBuffer.length());
            isStart = true;
            error = audioRecoderDialog.startListening(recognizerListener);

            Logger.d(TAG, "startRecognizeWithDialog | error = " + error);
        } else {
            showTip("已开始！");
            return;
        }

        if (error != com.iflytek.aipsdk.util.ErrorCode.SUCCESS) {
            showTip("听写失败,错误码：" + error);
        } else {
            //showTip(getContext().getString(R.string.text_begin));
        }
    }

    /**
     * 销毁语音识别sdk
     */
    private void destroyRecongizer() {
        if (audioRecoderDialog != null) {
            audioRecoderDialog.destroyRecongizer();
        }
    }

//    /**
//     * 参数设置
//     *
//     * @return
//     */
//    public void setParam() {
//        // 清空参数
//        speechRecognizer.setParameter(SpeechConstant.PARAMS, null);
//
//        // 设置听写引擎
//        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//
//        // 设置返回结果格式
//        speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
//
//        String lag = "mandarin";
//        if ("en_us".equals(lag)) {
//            // 设置语言
//            speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "en_us");
//            speechRecognizer.setParameter(SpeechConstant.ACCENT, null);
//        } else {
//            // 设置语言
//            speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//            // 设置语言区域
//            speechRecognizer.setParameter(SpeechConstant.ACCENT, lag);
//        }
//
//        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
//        speechRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
//
//        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//        speechRecognizer.setParameter(SpeechConstant.VAD_EOS, "1000");
//
//        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
//        speechRecognizer.setParameter(SpeechConstant.ASR_PTT, "1");
//
//        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        speechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        speechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
//    }

    private Toast lastToast;

    protected void showTip(String tip) {
        if (lastToast != null) {
            lastToast.setText(tip);
            lastToast.show();
        } else {
            lastToast = Toast.makeText(getContext(), tip, Toast.LENGTH_SHORT);
            lastToast.show();
        }
    }

    protected void cancleTip() {
        if (lastToast != null) {
            lastToast.cancel();
            lastToast = null;
        }
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            handleRecognizeResult(results, isLast);
        }

        /**
         * 识别回调错误.
         */
        @Override
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

    private void handleRecognizeResult(RecognizerResult results, boolean isLast) {
        String text = JsonParser.parseIatResult(results.getResultString());
        if (Logger.isDebugable()) {
            Logger.d(TAG, "handleRecognizeResult | text = " + text);
        }

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "handleRecognizeResult error", e);
            }
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        String content = resultBuffer.toString();
        if (isLast) {
            etSpeechTextInput.setText(content);
            startRequest(content, MsgEntity.SOURCE_VOICE);
        }
    }

    private void handleRecognizeResult2(String text, boolean isLast) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "handleRecognizeResult2 | text = " + text);
        }

        String sn = null;
        String result = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(text);
            result = resultJson.optString("result");
            sn = resultJson.optString("sid");
        } catch (JSONException e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "handleRecognizeResult2 error", e);
            }
        }

        mIatResults.put(sn, result);
        mIatResultBuffer.append(result);

//        StringBuffer resultBuffer = new StringBuffer();
//        for (String key : mIatResults.keySet()) {
//            resultBuffer.append(mIatResults.get(key));
//        }

//        String content = resultBuffer.toString();
        if (isLast) {
            String content = mIatResultBuffer.toString();
            etSpeechTextInput.setText(content);
            startRequest(content, MsgEntity.SOURCE_VOICE);
        }
    }


    protected void startRequest(String text, int type) {
        startRequest(text, type, null);
    }

    /**
     * 开始轻轻请求问题
     *
     * @param text       具体的内容
     * @param type       消息来源， 1：语音消息， 2：文本消息， 3：链接消息
     * @param questionId 问题id，如果没有，填null
     */
    protected void startRequest(String text, int type, String questionId) {
        if (text.isEmpty()) {
            return;
        }

        showMsgView();
        addAskView(text);

        // 拦截
        requestQuestion(text, type, questionId);
    }

    /**
     * 请求问题
     *
     * @param content    具体的内容
     * @param type       消息来源， 1：语音消息， 2：文本消息， 3：链接消息
     * @param questionId 问题id，如果没有，填null
     */
    protected abstract void requestQuestion(String content, int type, String questionId);

    protected void showMsgView() {
        if (voiceGuideContainer.getVisibility() != View.GONE) {
            voiceGuideContainer.setVisibility(View.GONE);
        }
    }

    private void addAskView(String content) {
        MsgEntity msgEntity = MsgEntity.genAskMsg();
        msgEntity.setContent(content);
        AskView askView = new AskView(getContext());
        askView.setMsg(msgEntity);

        addConversationView(askView);
    }

    protected void addConversationView(AnswerView answerView) {
        conversationContainer.addView(answerView);
        scrollToBottom();
    }

    protected void addConversationView(AskView askView) {
        conversationContainer.addView(askView);
        scrollToBottom();
    }

    private void scrollToBottom() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /**
     * 初始化语音向导
     */
    protected abstract void initVoiceGuide();

    /**
     * 设置说法示例的内容
     *
     * @param list
     */
    public void setVoiceGuide(List<String> list) {
        voiceGuideContainer.setData(list);
    }

    public void destroy() {
        destroyRecongizer();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        destroy();
    }

    @Override
    public void onGuideItemClicked(View v, String content) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "onGuideItemClicked | content = " + content);
        }

        startRequest(content, MsgEntity.SOURCE_TEXT);
    }

    /**
     * 播报欢迎语
     */
    protected void speakWelcome() {
        voiceGuideContainer.speak();
    }

    /**
     * 初始化频道信息
     */
    public void initChannel() {

    }

    /**
     * 设置频道数据
     *
     * @param list
     */
    public void setChannelData(List<ChannelEntity> list) {
        channelBarView.setData(list);
    }

    /**
     * 设置频道信息的显示与隐藏
     *
     * @param shown
     */
    public void showOrHideChannel(boolean shown) {
        int visible = shown ? View.VISIBLE : View.GONE;
        channelBarView.setVisibility(visible);
    }

    /**
     * 设置频道的条目点击事件
     *
     * @param listener
     */
    public void setChannelItemClickListener(GridItemClickListener listener) {
        channelBarView.setChannelItemClickListener(listener);
    }

    /**
     * 设置频道信息
     *
     * @param channelEntity
     */
    public void setChannelEntity(ChannelEntity channelEntity) {
        this.channelEntity = channelEntity;

        updateData();
    }

    /**
     * 请求热门问题
     */
    protected void requestHotQuestion() {
    }

    private void updateData() {
        initVoiceGuide();

        requestHotQuestion();
    }

    /**
     * 获取频道id
     *
     * @return
     */
    protected String getChannelId() {
        return channelEntity == null ? null : channelEntity.getId();
    }
}
