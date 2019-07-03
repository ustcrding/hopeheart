package com.boc.hopeheatapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.boc.hopeheatapp.util.log.Logger;


/**
 * @author ruiding
 * @date 2016/3/7
 */
public class NetworkUtil {

    private static final String TAG = "NetworkUtil";


    /**
     * 判断WIFI是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager mgrTel = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return ((cm.getActiveNetworkInfo() != null && cm
                    .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                    .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是3G网络
     *
     * @param context
     * @return
     */
    public static boolean is3rd(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkINfo = cm.getActiveNetworkInfo();
            if (networkINfo != null
                    && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是wifi还是3g网络
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkINfo = cm.getActiveNetworkInfo();
            if (networkINfo != null
                    && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNetworkConnected(Context ctx) {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = null;
            info = cm.getActiveNetworkInfo();
            if (info == null) {
                Logger.d(TAG, "isNetworkConnected | info is null");
                return true;
            }
            return info.isConnected();
        } catch (Exception e) {
            Logger.e(TAG, "isNetworkConnected | error", e);
            return true;
        }
    }
}
