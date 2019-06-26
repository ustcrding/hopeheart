package com.boc.hopeheatapp;

import android.content.Context;
import android.content.Intent;

import com.boc.hopeheatapp.activity.AboutActivity;
import com.boc.hopeheatapp.activity.BrowserActivity;
import com.boc.hopeheatapp.activity.ChannelActivity;
import com.boc.hopeheatapp.activity.ChannelListActivity;
import com.boc.hopeheatapp.activity.HomeActivity;
import com.boc.hopeheatapp.activity.LoginActivity;
import com.boc.hopeheatapp.activity.PictureShowActivity;
import com.boc.hopeheatapp.activity.PsychologicalTestActivity;
import com.boc.hopeheatapp.activity.RegisterActivity;
import com.boc.hopeheatapp.activity.ScheduleSystemActivity;
import com.boc.hopeheatapp.activity.SettingActivity;
import com.boc.hopeheatapp.activity.UpdateDialog;
import com.boc.hopeheatapp.activity.VocalIdentifyActivity;
import com.boc.hopeheatapp.activity.VocalRegisterActivity;
import com.boc.hopeheatapp.activity.VocalVerifyActivity;
import com.boc.hopeheatapp.activity.WorkbenchActivity;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.model.VersionEntity;
import com.boc.hopeheatapp.util.string.StringUtil;

import java.util.ArrayList;

/**
 * activity跳转工具类
 *
 * @author dwl
 * @date 2017/12/21.
 */
public class ActivityJumper {

    public static final String EXTRA_UPDATE_ENTITY = "extra_update_entity";

    public static final String EXTRA_AUTHID = "extra_authid";

    public static final String EXREA_DO_ACTION = "extra_do_action";

    public static String INTENT_DATA = "intent_data";

    /**
     * 照片对应的索引
     */
    public static String INTENT_PAGE_INDEX = "intent_page_index";

    /**
     * 频道信息
     */
    public static String EXTRA_CHANNEL_ENTITY = "extra_channel_entity";

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_OVERRIDE_IN_NEW_ACTICITY = "extra_override_in_wewActivity";

    /**
     * 打开系统设置页面
     *
     * @param context
     */
    public static void startSettingActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开自动更新页面
     *
     * @param context
     * @param versionEntity
     */
    public static void startUpdateDialog(Context context, VersionEntity versionEntity) {
        if (context == null || versionEntity == null) {
            return;
        }

        Intent intent = new Intent(context, UpdateDialog.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_UPDATE_ENTITY, versionEntity);
        context.startActivity(intent);
    }

    /**
     * 打开排班系统
     *
     * @param context
     */
    public static void startScheduleSystemctivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ScheduleSystemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开我的工作台
     *
     * @param context
     */
    public static void startWorkbenchActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, WorkbenchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开声纹识别页面
     *
     * @param context
     */
    public static void startVocalVerifyActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, VocalVerifyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开声纹识别页面
     *
     * @param context
     */
    public static void startVocalIdentifyActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, VocalIdentifyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开声纹注册页面
     *
     * @param context
     */
    public static void startVocalRegisterActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, VocalRegisterActivity.class);
        //intent.putExtra(EXTRA_AUTHID, authid);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开注册页面
     *
     * @param context
     */
    public static void startRegisterActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开home页面
     *
     * @param context
     */
    public static void startHomeActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开home页面
     *
     * @param context
     */
    public static void startHomeActivity(Context context, String doAction) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (StringUtil.isNotBlank(doAction)) {
            intent.putExtra(ActivityJumper.EXREA_DO_ACTION, doAction);
        }
        context.startActivity(intent);
    }

    /**
     * 打开登录页面
     *
     * @param context
     */
    public static void startLoginActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开关于页面
     *
     * @param context
     */
    public static void startAboutActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, AboutActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开图片浏览界面
     *
     * @param context
     * @param list
     * @param picIndex
     */
    public static void startPictureShow(Context context, ArrayList<String> list, int picIndex) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PictureShowActivity.class);
        intent.putStringArrayListExtra(ActivityJumper.INTENT_DATA, list);
        intent.putExtra(ActivityJumper.INTENT_PAGE_INDEX, picIndex);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开二级频道页面
     *
     * @param context
     * @param channelEntity
     */
    public static void startChannelActivity(Context context, ChannelEntity channelEntity) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ChannelActivity.class);
        intent.putExtra(ActivityJumper.EXTRA_CHANNEL_ENTITY, channelEntity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开频道列表页面
     *
     * @param context
     * @param channelEntity
     */
    public static void startChannelListActivity(Context context, ChannelEntity channelEntity) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ChannelListActivity.class);
        intent.putExtra(ActivityJumper.EXTRA_CHANNEL_ENTITY, channelEntity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     *
     * @param context
     */
    public static void startBrowserActivity(Context context, String url) {
        startBrowserActivityWithTitle(context, url, null, false);
    }

    /**
     * 打开浏览器
     *
     * @param context
     */
    public static void startBrowserActivityWithTitle(Context context, String url, String title, boolean overrideInNewActivity) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(EXTRA_URL, url);
        if (StringUtil.isNotBlank(title)) {
            intent.putExtra(EXTRA_TITLE, title);
        }
        intent.putExtra(EXTRA_OVERRIDE_IN_NEW_ACTICITY, overrideInNewActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开心理问卷调查页面
     *
     * @param context
     */
    public static void startPsychologicalTestActivity(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PsychologicalTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
