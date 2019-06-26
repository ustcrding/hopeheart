package com.boc.hopeheatapp;

import android.app.Application;

import com.boc.hopeheatapp.BuildConfig;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;
import com.iflytek.util.Logs;

/**
 * 语音识别Application
 *
 * @author dwl
 * @date 2017/12/20.
 */
public class HopeHeatApplication extends Application {

    private final String TAG = "HopeHeatApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        initLog();
        initSpeech();

        initSrp();

        UserManager.createInstance(getApplicationContext());
    }

    /**
     * 初始化日志类
     */
    private void initLog() {
        Logger.setDebugable(BuildConfig.DEBUG);

        FlowerCollector.setDebugMode(BuildConfig.DEBUG);
        FlowerCollector.setCaptureUncaughtException(true);
    }

    /**
     * 初始化语音识别
     * <p>此处暂时初始化讯飞sdk，带后续等封装好再重新修改</p>
     */
    private void initSpeech() {
        // 初始化日志
        BocSettings.createInstance(this);

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));

        String robotAppId = BocSettings.getInstance().getString(BocSettings.APP_ID);
        if (StringUtil.isEmpty(robotAppId)) {
            robotAppId = "NL7P32Wz";  // 写死网标
        }
        ApiConfig.setAppId(robotAppId);

        TtsManager.getInstance(this);

        Setting.setShowLog(BuildConfig.DEBUG);
    }

    private final String BASE_PATH = "/sdcard/test/";
    /**
     * 是否授权成功
     */
    public static boolean isAuthor = false;

    private void initSrp() {
        com.iflytek.aipsdk.common.SpeechUtility.createUtility(this, null);

        Logs.setSaveFlag(false, BASE_PATH);
        Logs.setPerfFlag(false);
        Logs.setPrintFlag(Logger.isDebugable());
//        Authorize authorize = new Authorize();
//        authorize.login("sn=c,appid=pc20onli,url=172.31.3.84:1035", null, new IAuthorizeLoginListener() {
//
//            @Override
//            public void onLoginResult(int code) {
//
//                if (code == 0) {
//                    Logs.v("AIPSDKDemo", "[" + Thread.currentThread().getName() + "][" + TAG + "][onCreate] " + "--授权成功---");
//                    isAuthor = true;
//
//                } else {
//                    Logs.v("AIPSDKDemo", "[" + Thread.currentThread().getName() + "][" + TAG + "][onCreate] " + "--授权失败---");
//                    isAuthor = false;
//                }
//            }
//        });
    }

}
