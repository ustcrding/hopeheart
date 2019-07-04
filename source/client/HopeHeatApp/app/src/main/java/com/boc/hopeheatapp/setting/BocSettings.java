package com.boc.hopeheatapp.setting;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * @auther ruiding
 * @date 2015/11/7.
 */
public class BocSettings implements ISettings {
    private static final String PACKAGE_NAME = "com.boc.cc.bocvoice";
    private static final String SETTINGS_NAME = "settings.xml";

    public static final String KEY_TTS_SWITH = "key_tts_switch";

    public static final String LOGIN_USER_INFO = "login_user_info";
    public static final String AUTO_LOGIN = "autoLogin";
    public static final String LOGIN_OUT = "login_out";

    /**
     * 登录的用户名
     */
    public static final String LOGIN_USERNAME = "loginUserName";

    /**
     * 登录的密码
     */
    public static final String LOGIN_PASSWORD = "loginPassword";

    /**
     * 知识库热门文档
     */
    public static final String HOT_QUESTION_KB = "hot_question_kb";

    /**
     * 上传请求知识库热门问题的时间
     */
    public static final String LAST_HOT_QUESTION_TIME_KB = "last_hot_question_time_kb";

    /**
     * appid
     */
    public static final String APP_ID = "appId";

    public static final String VICTIM_TEST_ID = "victim_test_id";
    public static final String DOCTOR_ID = "doctor_id";

    private HashMap<String, Integer> mDefaultSettingHashMap;
    private ISettings mSettings;
    private static BocSettings mInstance;


    private BocSettings(Context context) {
        // 默认值列表，com.boc.cc.bocvoice，0表示false
        mDefaultSettingHashMap = new HashMap<String, Integer>();
        //推送开关，默认打开
        mDefaultSettingHashMap.put(KEY_TTS_SWITH, 1);

        mSettings = SettingsFactory.newInstance(context, SETTINGS_NAME);
    }

    public synchronized static void createInstance(Context context) {
        mInstance = new BocSettings(context.getApplicationContext());
    }

    public static BocSettings getInstance() {
        return mInstance;
    }

    @Override
    public boolean isSetted(String key) {
        return mSettings.isSetted(key);
    }

    @Override
    public void setSetting(String key, boolean value) {
        mSettings.setSetting(key, value);
    }

    @Override
    public void setSetting(String key, int value) {
        mSettings.setSetting(key, value);
    }

    @Override
    public void setSetting(String key, float value) {
        mSettings.setSetting(key, value);
    }

    @Override
    public void setSetting(String key, long value) {
        mSettings.setSetting(key, value);
    }

    @Override
    public void setSetting(String key, String value) {
        mSettings.setSetting(key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        if (isSetted(key)) {
            return mSettings.getBoolean(key);
        }
        return getDefaultBooleanSetting(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mSettings.getBoolean(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return mSettings.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        if (isSetted(key)) {
            return mSettings.getInt(key, defaultValue);
        }
        return getDefaultIntSetting(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return mSettings.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return mSettings.getFloat(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return mSettings.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return mSettings.getLong(key, defaultValue);
    }

    @Override
    public String getString(String key) {
        return mSettings.getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }

    @Override
    public void saveObject(String fileName, Object object) {
        mSettings.saveObject(fileName, object);
    }

    @Override
    public Object readObject(String fileName) {
        return mSettings.readObject(fileName);
    }

    @Override
    public void clearObject(String fileName) {
        mSettings.clearObject(fileName);
    }

    private boolean getDefaultBooleanSetting(String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }

        String result = "";
        if (mDefaultSettingHashMap.containsKey(key)) {
            result = mDefaultSettingHashMap.get(key).toString();
        }

        return "1".equals(result);
    }

    private int getDefaultIntSetting(String key, int defaultValue) {
        int value = defaultValue;
        if (null != key && mDefaultSettingHashMap.containsKey(key)) {
            value = mDefaultSettingHashMap.get(key);
        }
        return value;
    }

    @Override
    public void removeSetting(String key) {
        mSettings.removeSetting(key);
    }
}
