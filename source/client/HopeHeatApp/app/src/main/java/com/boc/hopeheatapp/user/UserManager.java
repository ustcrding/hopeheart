package com.boc.hopeheatapp.user;

import android.content.Context;

import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.util.json.JsonUtils;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;

/**
 * @author dingr
 * @date 2017/12/4.
 */
public class UserManager {

    private static final String TAG = "UserManager";

    private Context context;

    private static UserManager instance;

    private UserEntity user;

    private boolean checkAuthority = true;

    private UserManager(Context context) {
        this.context = context;

        user = new UserEntity();
        user.setUsername("anon");
        user.setUserId(0);
        user.setRoleId("1");
        init();
    }

    public static UserManager createInstance(Context context) {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager(context);
                }
            }
        }
        return instance;
    }

    public static UserManager getInstance() {
        return instance;
    }

    private void init() {
        BocSettings settings = BocSettings.getInstance();
        boolean hasLogout = settings.getBoolean(BocSettings.LOGIN_OUT, false);

        if (hasLogout) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "init | hasLogout = true, return");
            }
            return;
        }

        String strJson = settings.getString(BocSettings.LOGIN_USER_INFO);
        if (StringUtil.isEmpty(strJson)) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "init | jsons is null");
            }
            return;
        }

        user = JsonUtils.fromJson(strJson, UserEntity.class);
    }

    public void setUser(final UserEntity userEntity) {
        this.user = userEntity;

        saveUser();
//        if (this.user != null) {
//            String userid = String.valueOf(this.user.userid);
//            //PushManager.getInstance(context).setAlias(userid);
//            //BuglyHelper.setUid(userid, context);
//
//            // 设置给讯飞统计sdk用户id
//            FlowerCollector.setUserID(context, userid);
//        }
    }

    public void saveUser() {
        if (null == user) {
            return;
        }

        BocSettings.getInstance().setSetting(BocSettings.LOGIN_USER_INFO, JsonUtils.toJson(user));
        BocSettings.getInstance().setSetting(BocSettings.LOGIN_OUT, false);
    }

    public UserEntity getUser() {
        return user;
    }

    public void logout() {
        user = null;

        BocSettings.getInstance().setSetting(BocSettings.LOGIN_OUT, true);
        //BocSettings.getInstance().setSetting(BocSettings.LOGIN_USER_INFO, "");
    }

    public boolean hasLogin() {
        return user != null;
    }
}
