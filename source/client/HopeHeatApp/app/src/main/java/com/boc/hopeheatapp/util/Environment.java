package com.boc.hopeheatapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.WindowManager;

/**
 * @auther ruiding
 * @date 2015/5/15.
 */
public class Environment {

    private static int mScreenWidth;

    private static int mScreenHeight;

    /**
     * 获取系统版本号
     */
    public static int getOSVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统代号
     */
    public static String getOSVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取系统唯一标志
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度（单位：px）
     */
    public static int getScreenWidth(Context context) {
        if (0 == mScreenWidth) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        }
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度（单位：px）
     */
    public static int getScreenHeight(Context context) {
        if (0 == mScreenHeight) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mScreenHeight = windowManager.getDefaultDisplay().getHeight();
        }
        return mScreenHeight;
    }

    /**
     * 获取我的版本code
     *
     * @param context 上下文
     * @return
     */
    public static int getMyVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
        }
        return 0;
    }

    public static boolean isWifi(Context context) {
        // 获取系统的连接服务
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取网络的连接情况
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取我的版本号
     * @param context
     * @return
     */
    public static String getMyVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取最新版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 判断是否是小米手机rom
     * @return
     */
    public static boolean isMIUIRom(){
        boolean value = false;
        String classString = "miui.os.Build";
        try {
            Class.forName(classString);
            value = true;
        } catch (Exception ignored) {
           //ignored
        }

        return value;
    }
}
