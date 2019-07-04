package com.boc.hopeheatapp.call;

import android.content.Context;
import android.content.Intent;

import com.boc.hopeheatapp.util.log.Logger;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class CallHelper {
    private static final String TAG = "CallHelper";

    public static final String VOIP_LOGIN_ACTION = "com.boc.hopeheatapp.viop_login";

    public static final String VOIP_VIDEO_CALL = "com.boc.hopeheatapp.video_call";

    /**
     * @param context
     * @param phone
     */
    public static void voipLogin(Context context, String phone) {
        Logger.d(TAG, "voipLogin | phone = " + phone);

        Intent intent = new Intent();
        intent.setAction(VOIP_LOGIN_ACTION);  //应用在清淡文件中注册的action
        intent.setPackage("com.yuntongxun.voipdemo");                  //应用程序的包名
        intent.putExtra("phone", "18019996045");
        intent.putExtra("username", "d2");
        context.startService(intent);
    }


    /**
     * 视频通话
     *
     * @param context
     * @param phone
     * @param username
     */
    public static void videoCall(Context context, String phone, String username) {
        Logger.d(TAG, "videoCall | phone = " + phone + ", username = " + username);

        Intent intent = new Intent();
        intent.setAction(VOIP_VIDEO_CALL);  //应用在清淡文件中注册的action
        intent.setPackage("com.yuntongxun.voipdemo");                  //应用程序的包名
        intent.putExtra("phone", phone);
        intent.putExtra("username", username);
        context.startService(intent);
    }
}
