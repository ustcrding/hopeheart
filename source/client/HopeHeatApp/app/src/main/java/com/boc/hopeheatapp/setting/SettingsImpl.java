package com.boc.hopeheatapp.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.boc.hopeheatapp.util.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 设置的具体实现
 *
 * @author fpliu@iflytek.com 2014-2-13
 */
final class SettingsImpl implements ISettings {

    private static final String TAG = "SettingsImpl";

    private SharedPreferences mSharedPref;


    public SettingsImpl(Context context, String name) {
        mSharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isSetted(String key) {
        return mSharedPref.contains(key);
    }

    @Override
    public void setSetting(String key, boolean value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception e) {
            Logger.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }

    @Override
    public void setSetting(String key, int value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putInt(key, value);
            editor.commit();
        } catch (Exception e) {
            Logger.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }

    @Override
    public void setSetting(String key, float value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putFloat(key, value);
            editor.commit();
        } catch (Exception e) {
            Logger.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }

    @Override
    public void setSetting(String key, long value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putLong(key, value);
            editor.commit();
        } catch (Exception e) {
            Logger.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }

    @Override
    public void setSetting(String key, String value) {
        if (null != value) {
            //要过滤'\0',否则会使XML读取异常
            value = value.replace("\0", "");
        }

        try {
            Editor editor = mSharedPref.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            Logger.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        boolean result = defaultValue;
        try {
            result = mSharedPref.getBoolean(key, result);
        } catch (Exception e) {
            Logger.e(TAG, "getBoolean()", e);
        }
        return result;
    }

    @Override
    public int getInt(String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        int value = defaultValue;
        try {
            value = mSharedPref.getInt(key, defaultValue);
        } catch (Exception e) {
            Logger.e(TAG, "getSetting()", e);
        }
        return value;
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        float value = defaultValue;
        try {
            value = mSharedPref.getFloat(key, defaultValue);
        } catch (Exception e) {
            Logger.e(TAG, "getLongSetting()", e);
        }
        return value;
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        long value = defaultValue;
        try {
            value = mSharedPref.getLong(key, defaultValue);
        } catch (Exception e) {
            Logger.e(TAG, "getLongSetting()", e);
        }
        return value;
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = defaultValue;
        try {
            value = mSharedPref.getString(key, defaultValue);
        } catch (Exception e) {
            Logger.e(TAG, "getString()", e);
        }
        return value;
    }

    @Override
    public void saveObject(String fileName, Object object) {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception e) {
            Logger.e(TAG, "saveObject()", e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    Logger.e(TAG, "saveObject()", e);
                }
            }
        }
    }

    @Override
    public Object readObject(String fileName) {

        Object object = null;
        ObjectInputStream objectInputStream = null;
        try {

            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            object = objectInputStream.readObject();
        } catch (Exception e) {

            Logger.e(TAG, "readObject()" + e);
        } finally {
            if (objectInputStream != null) {

                try {
                    objectInputStream.close();
                } catch (IOException e) {

                    Logger.e(TAG, "readObject()" + e);
                }
            }
        }
        return object;
    }

    @Override
    public void clearObject(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
                Logger.e(TAG, "delete file success");
            }
        } catch (Exception e) {
            Logger.e(TAG, " clearObject()", e);
        }
    }

    @Override
    public void removeSetting(String key) {
        try {
            //如果key不为空，把key删掉
            if (!TextUtils.isEmpty(key)) {
                Editor editor = mSharedPref.edit();
                editor.remove(key);
                editor.commit();
            }
        } catch (Exception e) {
            Logger.e(TAG, "removeSetting(" + key + ")", e);
        }
    }
}
