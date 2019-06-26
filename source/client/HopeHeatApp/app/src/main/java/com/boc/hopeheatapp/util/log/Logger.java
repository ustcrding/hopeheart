package com.boc.hopeheatapp.util.log;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Log工具类
 *
 * @author dwl
 * @date 2017/12/21.
 */
public class Logger {

    protected static boolean isDebugable = true;
    public static final String DIR_EXT_VIAFLY = "bocVoice" + File.separator;
    public static final String FILE_EXCEPITON_LOG = "UncaughtException.log";
    private static final String PREFIX = "bocvoice_";

    public static void setDebugable(boolean isDebugable) {
        Logger.isDebugable = isDebugable;
    }

    public static boolean isDebugable() {
        return isDebugable;
    }

    public static int v(String tag, String msg) {
        int result = 0;
        if (isDebugable) {
            result = Log.v(PREFIX + tag, msg);
        }
        return result;
    }

    public static int v(String tag, String msg, Throwable tr) {
        int result = 0;
        if (isDebugable) {
            result = Log.v(PREFIX + tag, msg, tr);
        }
        return result;
    }

    public static int d(String tag, String msg) {
        int result = 0;
        if (isDebugable) {
            result = Log.w(PREFIX + tag, msg);
        }
        return result;
    }

    public static int d(String tag, String msg, Throwable tr) {
        int result = 0;
        if (isDebugable) {
            result = Log.w(PREFIX + tag, msg, tr);
        }
        return result;
    }

    public static int i(String tag, String msg) {
        int result = 0;
        if (isDebugable) {
            result = Log.i(PREFIX + tag, msg);
        }
        return result;
    }

    public static int i(String tag, String msg, Throwable tr) {
        int result = 0;
        if (isDebugable) {
            result = Log.i(PREFIX + tag, msg, tr);
        }
        return result;
    }

    public static int w(String tag, String msg) {
        int result = 0;
        if (isDebugable) {
            result = Log.w(PREFIX + tag, msg);
        }
        return result;
    }

    public static int w(String tag, String msg, Throwable tr) {
        int result = 0;
        if (isDebugable) {
            result = Log.w(PREFIX + tag, msg, tr);
        }
        return result;
    }

    public static int w(String tag, Throwable tr) {
        int result = 0;
        if (isDebugable) {
            result = Log.w(PREFIX + tag, tr);
        }
        return result;
    }

    public static int e(String tag, String msg) {
        int result = 0;
        if (isDebugable) {
            result = Log.e(PREFIX + tag, msg);
        }
        return result;
    }

    public static int e(String tag, String msg, Throwable tr) {
        int result = 0;
        if (isDebugable) {
            result = Log.e(PREFIX + tag, msg, tr);
        }
        return result;
    }

    private static String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(cal.getTime());
    }

    /**
     * 注册捕获全局异常
     */
    public static void logUncaughtException(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler(
                context));
    }

    private static class MyUncaughtExceptionHandler implements
            Thread.UncaughtExceptionHandler {
        private Context mContext;

        public MyUncaughtExceptionHandler(Context context) {
            mContext = context;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            saveUncaughtException(mContext, ex);
        }

        /**
         * 保存未处理的全局异常到Log中
         *
         * @param e
         */
        private void saveUncaughtException(final Context context,
                                           final Throwable e) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File dir = new File(DIR_EXT_VIAFLY);
                    File f = new File(DIR_EXT_VIAFLY + FILE_EXCEPITON_LOG);
                    FileOutputStream fs = null;

                    StringBuilder log = new StringBuilder();
                    try {
                        dir.mkdirs();

                        log.append(getDate()).append('\n');
//                        log.append(Environment.getInstance().getUserAgent())
//                                .append('\n');

                        log.append(Log.getStackTraceString(e));
                        log.append("\n\n");

                        fs = new FileOutputStream(f, true);
                        Logger.d("House", log.toString());
                        fs.write(log.toString().getBytes());
                        fs.flush();
                        fs.close();

                    } catch (FileNotFoundException e1) {

                    } catch (IOException e2) {

                    } catch (Exception e3) {

                    } finally {
                        Log.e("", "", e);
                        android.os.Process.killProcess(Process.myTid());
                        if (fs != null) {
                            try {
                                fs.close();
                                fs = null;
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }
}
