package com.boc.hopeheatapp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.user.UserManager;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.FileAccessor;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.voip.Voip;

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

        boolean shouldResult = shouldInit(this);
        LogUtil.d(TAG, "App onCreate " + shouldResult);
        if (!shouldResult) {
            return;
        }

        init();
    }

    private void init() {
        // 初始化日志
        BocSettings.createInstance(this);

        UserManager.createInstance(getApplicationContext());

        // 初始化本地文件夹
        FileAccessor.initFileAccess();
        // 云通讯SDK上下文对象，必选
        SDKCoreHelper.setContext(this);
        // 音视频通话插件初始化(可选)
        Voip.VoIPPluginParams.Builder builder = new Voip.VoIPPluginParams.Builder();
        // 开启音频录制
        // builder.setOpenRecordMicrophone(true);
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
