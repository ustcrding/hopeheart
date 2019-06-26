package com.boc.hopeheatapp.proxy;


import android.content.Context;
import android.text.TextUtils;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.boc.hopeheatapp.parser.BaseAction;
import com.boc.hopeheatapp.parser.SemanticParser;
import com.boc.hopeheatapp.parser.WeatherAction;

public class WeatherWrapper {
    private static final String WEATHER_QUERY = "上海的天气";
    private TextUnderstander mTextUnderstander;
    private Context mContext;
    private IResultListener mListener;

    public interface IResultListener {
        void onResult(WeatherAction action);

        void onError();
    }

    public WeatherWrapper(Context context) {
        mContext = context;
    }

    private InitListener mTextUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                mTextUnderstander = null;

                if (mListener != null) {
                    mListener.onError();
                }
            }
        }
    };

    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                // 显示
                String text = result.getResultString();
                if (!TextUtils.isEmpty(text)) {
                    BaseAction action = SemanticParser.parser(text);
                    if (action != null && action instanceof WeatherAction) {
                        if (mListener != null) {
                            mListener.onResult((WeatherAction) action);
                        }
                    } else {
                        WeatherWrapper.this.onError();
                    }
                } else {
                    WeatherWrapper.this.onError();
                }
            } else {
                WeatherWrapper.this.onError();
            }
        }

        @Override
        public void onError(SpeechError error) {
            WeatherWrapper.this.onError();
        }
    };

    public void queryWeather(IResultListener listener) {
        mListener = listener;
        if (mTextUnderstander == null) {
            mTextUnderstander = TextUnderstander.createTextUnderstander(mContext, mTextUdrInitListener);
        }

        int ret = mTextUnderstander.understandText(WEATHER_QUERY, mTextUnderstanderListener);
        if (ret != 0) {
            onError();
        }
    }

    public void destroy() {
        if (mTextUnderstander != null) {
            if (mTextUnderstander.isUnderstanding()) {
                mTextUnderstander.cancel();
            }
            mTextUnderstander.destroy();
        }
    }

    private void onError() {
        if (mListener != null) {
            mListener.onError();
        }
    }
}
