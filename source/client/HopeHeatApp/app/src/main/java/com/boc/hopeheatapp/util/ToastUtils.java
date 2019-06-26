package com.boc.hopeheatapp.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.boc.hopeheatapp.util.log.Logger;

/**
 * toast工具类
 *
 * @author dwl
 * @date 2018/2/22.
 */
public class ToastUtils {

    private static final String TAG = "ToastUtils";
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();

    public static void showShort(Context ct, int resId) {
        showShort(ct, ct.getResources().getString(resId));
    }

    public static void showShort(Context ct, String msg) {
        show(ct, msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context ct, int resId) {
        showLong(ct, ct.getResources().getString(resId));
    }

    public static void showLong(Context ct, String msg) {
        show(ct, msg, Toast.LENGTH_SHORT);
    }

    public static void show(final Context ct, final String msg, final int duration) {
        if (msg == null || "".equals(msg)) {
            Logger.d(TAG, "[ToastUtil.show] response message is null.");
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast != null) {
                        //toast.cancel();
                        toast.setText(msg);
                        toast.setDuration(duration);
                    } else {
                        toast = Toast.makeText(ct, msg, duration);
                    }
                    toast.show();
                }
            }
        });
    }

    public static void dismissToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
