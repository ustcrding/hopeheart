package com.boc.hopeheatapp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;
import com.iflytek.util.Logs;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.FileAccessor;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.voip.Voip;
import com.yuntongxun.voipdemo.VoipImpl;

import java.util.List;

/**
 * 语音识别Application
 *
 * @author dwl
 * @date 2017/12/20.
 */
public class HopeHeatApplication extends Application {

    private static final String TAG = "HopeHeatApplication";

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

        initYuntongxun();
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

    private void initYuntongxun() {
        boolean shouldResult = shouldInit(this);
        Logger.d(TAG, "App onCreate " + shouldResult);
        if (!shouldResult) {
            return;
        }

        // 初始化本地文件夹
        FileAccessor.initFileAccess();
        // 云通讯SDK上下文对象，必选
        SDKCoreHelper.setContext(this);
        // 音视频通话插件初始化(可选)
//        Voip.VoIPPluginParams.Builder builder = new Voip.VoIPPluginParams.Builder();
//        builder.setVoipCallback(VoipImpl.getInstance());
//        Voip.initVoIPPlugin(builder.build());

        Voip.VoIPPluginParams.Builder builder = new Voip.VoIPPluginParams.Builder();
        // 设置VoIP回掉接口
        builder.setVoipCallback(VoipImpl.getInstance());
        Voip.initVoIPPlugin(builder.build());
    }

    /**
     * 判断是否是应用进程
     *
     * @param context
     * @return
     */
    public static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        LogUtil.d(TAG, "[shouldInit] mainProcessName is " + mainProcessName + " pid is " + myPid);
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            LogUtil.d(TAG, "[shouldInit] processName " + info.processName + " pid is " + info.pid);
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
