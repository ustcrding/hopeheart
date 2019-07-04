package com.yuntongxun.voipdemo.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.voip.Voip;
import com.yuntongxun.voipdemo.MainActivity;

import org.jsoup.helper.StringUtil;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by WJ on 2016/12/18.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";

    public class IBind extends Binder {
        public void test() {
            LogUtil.e(TAG, "test IBind");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e(TAG, "onBind");
        return new IBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG, "onCreate");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.e(TAG, "onTrimMemory");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(TAG, "onUnbind " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "onStartCommand " + intent);

        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.e(TAG, "onConfigurationChanged");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.e(TAG, "onLowMemory");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtil.e(TAG, "onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        LogUtil.e(TAG, "onTaskRemoved");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.e(TAG, "onStart");
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
        LogUtil.e(TAG, "dump" + args);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (StringUtil.isBlank(action)) {
            return;
        }

        if ("com.boc.hopeheatapp.video_call".equals(action)) {
            String phone = intent.getStringExtra("phone");
            String username = intent.getStringExtra("useranem");

            LogUtil.e(TAG, "handleIntent | action = " + action + ", phone = " + phone + ", username = " + username);
            Voip.startCallAction(
                    getApplicationContext(),
                    ECVoIPCallManager.CallType.VIDEO,
                    username,
                    phone,
                    "手机号",
                    false
            );
        }
    }
}
