package com.yuntongxun.voipdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.huawei.android.pushagent.api.PushManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.DeviceUtil;
import com.yuntongxun.plugin.common.common.utils.FileAccessor;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.voip.Voip;

import java.util.List;

/**
 * Created by WJ on 2016/11/21.
 */

public class DemoApplication extends Application {
    private static final String TAG = ".DemoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        boolean shouldResult = shouldInit(this);
        LogUtil.d(TAG, "App onCreate " + shouldResult);
        if (!shouldResult) {
            return;
        }
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

        if (DeviceUtil.isXiaomiDevices()) {
            // 参数一替换成自己小米AppId, 参数二替换成自己小米推送AppKey
            initXiaoMiPush("2882303761517605416", "5341760593416");
        } else if (DeviceUtil.isHuaweiDevices()) {
            // 华为
            PushManager.requestToken(getApplicationContext());
            PushManager.enableReceiveNormalMsg(this, true);
        }
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

    /**
     * 小米初始化
     */
    private void initXiaoMiPush(String appid, String appkey) {
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        MiPushClient.registerPush(this, appid, appkey);
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

    }


}
