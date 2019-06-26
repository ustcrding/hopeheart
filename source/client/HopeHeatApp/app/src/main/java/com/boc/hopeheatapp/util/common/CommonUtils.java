package com.boc.hopeheatapp.util.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.boc.hopeheatapp.util.log.Logger;

import java.util.Date;

/**
 * Created by xuhan on 15/4/26.
 */
public class CommonUtils {

    public static void showToast(final Activity context, final String message) {
        if (null == context || TextUtils.isEmpty(message)) {
            return;
        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static void showToast(final Context context, final String message) {
        if (null == context || TextUtils.isEmpty(message)) {
            return;
        }

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String onFormatTimeStr(final long timeMill) {

        Date curDate = new Date(timeMill);
        Logger.v("time:",  curDate.toString());

        Date todayDate = new Date();
        String strFormat = "yyyy-MM-dd HH:mm";
        if (curDate.getYear() == todayDate.getYear() && curDate.getMonth() == todayDate.getMonth() && curDate.getDay() == todayDate.getDay()) {
            strFormat = "HH:mm";
        }

        return DateFormat.formatDate(curDate, strFormat);
    }

}
