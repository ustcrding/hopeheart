package com.boc.hopeheatapp.proxy;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.DynamicEntityData;
import com.boc.hopeheatapp.parser.BaseAction;
import com.boc.hopeheatapp.parser.DefaultAction;
import com.boc.hopeheatapp.parser.SemanticParser;
import com.boc.hopeheatapp.util.log.Logger;
import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.record.PcmRecorder;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.UserWords;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoiceWrapper implements PcmRecorder.PcmRecordListener {
    private static final String TAG = VoiceWrapper.class.getSimpleName();

    public static final String USER_WORD_TAG = "userword";
    public static final String CONTACT_TAG = "contact";

    private Context mContext;
    private SpeechUnderstander mSpeechUnderstander;
    private SpeechRecognizer mIat;
    private StringBuilder mIatResults = new StringBuilder();
    private IResultListener mListener;

    private int mCurrentState = AIUIConstant.STATE_IDLE;
    private AIUIAgent mAgent;
    private PcmRecorder mRecorder;
    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    private RecognizerDialog mIatDialog;

    @Override
    public void onRecordBuffer(byte[] bytes, int offset, int length) {
        final byte[] audio = Arrays.copyOfRange(bytes, offset, length);
        writeAudio(audio);
    }

    @Override
    public void onError(SpeechError speechError) {

    }

    @Override
    public void onRecordStarted(boolean b) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                sendSuccess();
            }
        });
    }

    @Override
    public void onRecordReleased() {

    }

    public interface IResultListener {
        void onSuccess();

        void onError();

        void onSemanticResult(BaseAction action);

        void onSpeechResult(String result);
    }

    public VoiceWrapper(Context context) {
        mContext = context;
    }

    public void setResultListener(IResultListener listener) {
        mListener = listener;
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            appendResult(results);

            if (isLast) {
                if (mListener != null) {
                    mListener.onSpeechResult(mIatResults.toString());
                }

                mIatResults.delete(0, mIatResults.length());
            }
        }

        /**
         * 识别回调错误.
         */
        @Override
        public void onError(SpeechError error) {
            if (mListener != null) {
                mListener.onError();
            }
        }

    };

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onBeginOfSpeech() {
        }

        @Override
        public void onError(SpeechError error) {
            if (error != null) {
                if (error.getErrorCode() == 10118) {
                    return;
                }
                sendError();
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            appendResult(results);

            if (isLast) {
//                transform(mIatResults.toString());
                if (mListener != null) {
                    mListener.onSpeechResult(mIatResults.toString());
                }

                mIatResults.delete(0, mIatResults.length());
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    /**
     * 上传联系人/词表监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
            } else {
            }
        }
    };

    /**
     * 语义理解回调。
     */
    private SpeechUnderstanderListener mSpeechUnderstanderListener = new SpeechUnderstanderListener() {
        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                // 显示
                String text = result.getResultString();
                if (!TextUtils.isEmpty(text)) {
                    transform(text);
                } else {
                    sendError();
                }
            } else {
                sendError();
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onBeginOfSpeech() {
        }

        @Override
        public void onError(SpeechError error) {
            if (error.getErrorCode() == ErrorCode.MSP_ERROR_NO_DATA) {
            } else {
            }

            sendError();
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    /**
     * 初始化监听器（语音到语义）。
     */
    private InitListener mSpeechUdrInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
            }
        }
    };

    public void startRecognizeWithUi() {
        if (mIatDialog == null) {
            createRecoginzerDialog();
        }

        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
    }


    public void startRecoginze() {
        if (mIat == null) {
            createRecoginzer();
        }

        int ret = mIat.startListening(mRecognizerListener);
    }

    public void stopRecognize() {
        if (mIat != null) {
            mIat.stopListening();
        }
    }

    public void upload(String key, UserWords datas) {
        if (mIat == null) {
            createRecoginzer();
        }
        if (mIat != null) {
            mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            int ret = mIat.updateLexicon(key, datas.toString(), mLexiconListener);
        }
    }


    public void startUnderstander() {
        if (mSpeechUnderstander == null) {
            createUnderstander();
        }

        if (mSpeechUnderstander.isUnderstanding()) {// 开始前检查状态
            mSpeechUnderstander.stopUnderstanding();
        } else {
            int ret = mSpeechUnderstander.startUnderstanding(mSpeechUnderstanderListener);
            if (ret != 0) {
                sendError();
            } else {
                //ToastUtil.showMessage("请开始说话");
                sendSuccess();
            }
        }
    }

    public void stopUnderstander() {
        if (mSpeechUnderstander != null) {
            mSpeechUnderstander.stopUnderstanding();
        }
    }

    public void startAiui() {
        if (mAgent == null) {
            createAIUIAgent();
        }
        try {
            mRecorder = new PcmRecorder(16000, 40);
            mRecorder.startRecording(this);
        } catch (SpeechError speechError) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "startAiui error", speechError);
            }
        }
//        startAiuiRecorder();
    }

    public void stopAiui() {
//        startAiuiRecorder();
        stopWriteAudio();
        if (mRecorder != null) {
            mRecorder.stopRecord(true);
            mRecorder = null;
        }
    }

    public void destory() {
        if (mAgent != null) {
            mAgent.destroy();
            mAgent = null;
        }
    }

    public void syncDynamicEntity(DynamicEntityData data) {
        if (mAgent == null) {
            createAIUIAgent();
        }
        try {
            // schema数据同步
            JSONObject syncSchemaJson = new JSONObject();
            JSONObject paramJson = new JSONObject();

            paramJson.put("appid", mContext.getString(R.string.app_id));
            paramJson.put("id_name", data.idName);
            paramJson.put("id_value", data.idValue);
            paramJson.put("res_name", data.resName);

            syncSchemaJson.put("param", paramJson);
            syncSchemaJson.put("data", Base64.encodeToString(
                    data.syncData.getBytes(), Base64.DEFAULT | Base64.NO_WRAP));

            // 传入的数据一定要为utf-8编码
            byte[] syncData = syncSchemaJson.toString().getBytes("utf-8");

            AIUIMessage syncAthenaMessage = new AIUIMessage(AIUIConstant.CMD_SYNC,
                    AIUIConstant.SYNC_DATA_SCHEMA, 0, "", syncData);
            sendMessage(syncAthenaMessage);
        } catch (Exception e) {
        }
    }

    public void queryDynamicSyncStatus(String sid) {
        JSONObject paramsJson = new JSONObject();
        try {
            paramsJson.put("sid", sid);
            AIUIMessage querySyncMsg = new AIUIMessage(AIUIConstant.CMD_QUERY_SYNC_STATUS,
                    AIUIConstant.SYNC_DATA_SCHEMA, 0,
                    paramsJson.toString(), null);
            sendMessage(querySyncMsg);

        } catch (JSONException e) {
        }
    }

    private void createRecoginzerDialog() {
        if (mIatDialog == null) {
            mIatDialog = new RecognizerDialog(mContext, null);
        }
    }

    private void createRecoginzer() {
        if (mIat == null) {
            mIat = SpeechRecognizer.createRecognizer(mContext, null);

            mIat.setParameter(SpeechConstant.PARAMS, null);

            // 设置听写引擎
            mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置返回结果格式
            mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

            // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
            mIat.setParameter(SpeechConstant.VAD_BOS, "15000");

            // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
            mIat.setParameter(SpeechConstant.VAD_EOS, "15000");

            // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
            mIat.setParameter(SpeechConstant.ASR_PTT, "1");

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
            mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
            //mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/mIat.wav");

            // 设置音频来源为外部文件
//            mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        }
    }


    private void createUnderstander() {
        if (mSpeechUnderstander == null) {
            mSpeechUnderstander = SpeechUnderstander.createUnderstander(mContext, mSpeechUdrInitListener);

            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, "mandarin");

            // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
            mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS, "4000");

            // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
            mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, "1000");

            // 设置标点符号，默认：1（有标点）
            mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, "0");

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
            mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        }
    }

    private void appendResult(RecognizerResult results) {
        if (results != null) {
            try {
                JSONTokener tokener = new JSONTokener(results.getResultString());
                JSONObject joResult = new JSONObject(tokener);

                JSONArray words = joResult.getJSONArray("ws");
                for (int i = 0; i < words.length(); i++) {
                    // 转写结果词，默认使用第一个结果
                    JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                    JSONObject obj = items.getJSONObject(0);
                    mIatResults.append(obj.getString("w"));
                }
            } catch (Exception e) {
            }
        }
    }

    private void createAIUIAgent() {
        if (mAgent == null) {
            try {
                mAgent = AIUIAgent.createAgent(mContext, new JSONObject(readAIUICfg()).toString(), new AIUIListener() {
                    @Override
                    public void onEvent(AIUIEvent aiuiEvent) {
                        Logger.d(TAG, "event type: " + aiuiEvent.eventType);
                        switch (aiuiEvent.eventType) {
                            case AIUIConstant.EVENT_STATE: {
                                mCurrentState = aiuiEvent.arg1;
                            }
                            break;
                            case AIUIConstant.EVENT_RESULT: {
                                JSONObject semanticResult = getSemanticResult(aiuiEvent);
                                if (semanticResult != null && semanticResult.length() != 0) {
//                                    sendResult(SemanticParser.parser(semanticResult));
                                    transform(semanticResult.toString());
                                }
                            }
                            break;
                            case AIUIConstant.EVENT_SLEEP: {
                            }
                            break;

                            case AIUIConstant.EVENT_ERROR: {
                                if (aiuiEvent.arg1 == 10120) {
                                }

                                if (Logger.isDebugable()) {
                                    Logger.d(TAG, "ERR: " + aiuiEvent.info + " arg1: " + aiuiEvent.arg1 + " arg2: " + aiuiEvent.arg2);
                                }
                            }
                            break;
                            case AIUIConstant.EVENT_CMD_RETURN: {
                                processCmdReturnEvent(aiuiEvent);
                            }
                            break;
                        }
                    }
                });
                mAgent.sendMessage(new AIUIMessage(AIUIConstant.CMD_START, 0, 0, null, null));
                mAgent.sendMessage(new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null));
            } catch (Exception e) {

            }
        }
    }

    private JSONObject getSemanticResult(AIUIEvent event) {
        try {
            JSONObject bizParamJson = new JSONObject(event.info);
            JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
            JSONObject params = data.getJSONObject("params");
            JSONObject content = data.getJSONArray("content").getJSONObject(0);

            if (content.has("cnt_id")) {
                String cnt_id = content.getString("cnt_id");
                JSONObject cntJson = new JSONObject(new String(event.data.getByteArray(cnt_id), "utf-8"));

                String sub = params.optString("sub");
                if ("nlp".equals(sub)) {
                    // 解析得到语义结果
                    return cntJson.optJSONObject("intent");
                }
            }
        } catch (Throwable e) {
        }

        return null;
    }

    private void processCmdReturnEvent(AIUIEvent event) {
        int cmdType = event.arg1;
        switch (cmdType) {
            case AIUIConstant.CMD_SYNC: {
                int syncType = event.data.getInt("sync_dtype");
                int resultCode = event.arg2;

                if (AIUIConstant.SYNC_DATA_SCHEMA == syncType) {
                    String sid = event.data.getString("sid");
                    if (resultCode == 0) {
//                        queryDynamicSyncStatus(sid);
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "CMD_SYNC SUCCESS");
                        }
                    } else {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "CMD_SYNC FAIL");
                        }
                    }
                } else if (AIUIConstant.SYNC_DATA_SPEAKABLE == syncType) {
                }
            }
            break;

            case AIUIConstant.CMD_QUERY_SYNC_STATUS: {
                int syncType = event.data.getInt("sync_dtype");

                if (AIUIConstant.SYNC_DATA_QUERY == syncType) {
                    String result = event.data.getString("result");
                    int resultCode = event.arg2;

                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("ret", String.valueOf(resultCode));
                    mapData.put("result", result);
                }
            }

        }
    }

    public void writeText(String message) {
        sendMessage(new AIUIMessage(AIUIConstant.CMD_WRITE, 0, 0,
                "data_type=text", message.getBytes()));
    }

    public void writeAudio(byte[] audio) {
        sendMessage(new AIUIMessage(AIUIConstant.CMD_WRITE, 0, 0,
                "data_type=audio,sample_rate=16000,pers_param={\"uid\":\"\"}", audio));
    }

    public void stopWriteAudio() {
        sendMessage(new AIUIMessage(AIUIConstant.CMD_STOP_WRITE, 0, 0,
                "data_type=audio,sample_rate=16000", null));
    }

    public void startAiuiRecorder() {
        sendMessage(new AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0,
                "sample_rate=16000,data_type=audio", null));
    }

    public void stopAiuiRecorder() {
        sendMessage(new AIUIMessage(AIUIConstant.CMD_STOP_RECORD, 0, 0,
                "data_type=audio,sample_rate=16000", null));
    }

    private String readAIUICfg() {
        try {
            InputStream input = mContext.getAssets().open("aiui_phone.cfg");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            input.close();
            output.close();

            return new String(output.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void transform(String text) {
        Logger.d(TAG, text);
        // 暂时先走本地解析
        BaseAction action = SemanticParser.parser(text);
        if (Logger.isDebugable()) {
            Logger.d(TAG, action.toJson());
        }

        // 语义理解失败，放在本地处理
        sendResult(action);
        return;
    }

    private void sendMessage(AIUIMessage message) {
        if (mAgent != null) {
            if (mCurrentState != AIUIConstant.STATE_WORKING) {
                mAgent.sendMessage(new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null));
            }

            mAgent.sendMessage(message);
        }
    }

    private void sendResult(BaseAction action) {
        if (mListener != null) {
            mListener.onSemanticResult(action);
        }

        if (action != null) {
            Logger.d(TAG, action.toJson());
        }
    }

    private void sendSuccess() {
        if (mListener != null) {
            mListener.onSuccess();
        }
    }

    private void sendError() {
        if (mListener != null) {
            mListener.onError();
        }
    }

    private Location getCurrentLocation() {
        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            //获取所有可用的位置提供器
            List<String> providers = locationManager.getProviders(true);
            String locationProvider = null;
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是GPS
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是Network
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                return null;
            }
            //获取Location
            return locationManager.getLastKnownLocation(locationProvider);
        } catch (Exception e) {

        }
        return null;
    }
}
