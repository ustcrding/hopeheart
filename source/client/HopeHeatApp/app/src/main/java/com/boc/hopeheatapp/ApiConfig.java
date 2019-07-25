package com.boc.hopeheatapp;

import com.boc.hopeheatapp.BuildConfig;

/**
 * Created by qsy on 2018/1/19.
 */

public class ApiConfig {
    /**
     * DEBUG
     */
    public static final Boolean DEBUG = BuildConfig.DEBUG;

    /**
     * APPID
     */
    //public static final String APP_ID = "IJz2Vta8";

    /**
     * 网标系统appid
     */
    //public static final String APP_ID = "NL7P32Wz";

    /**
     * 服务器地址，应该根据 DEBUG 判断选用哪个环境
     */
    public static final String BASE_URL = "http://www.shenshencrown.cn:8080/smartRobot/";

    /**
     * 升级服务器地址，应该根据 DEBUG 判断选用哪个环境
     */
    public static final String UPDATE = "http://www.shenshencrown.cn:8080/boc_vol/";

    /**
     * 知识库的基本接口
     */
    public static final String KB_BASE_URL = "http://www.shenshencrown.cn:8080/boc_kb/";

    /**
     * 语音识别服务器url
     */
    public static final String SRP_URL = "http://95566.boc.cn/srp";

    private static String appId = "";

    /**
     * 心理问卷采集url
     */
    public static final String PSYCHOLOGICAL_TEST_URL = "https://boysama.cn/index2.html";

    /**
     * BASE url
     */
    public static final String HOPE_HEAT_BASE_URL = "http://172.20.10.3";

    public static void setAppId(String appId) {
        ApiConfig.appId = appId;
    }

    public static String getAppId() {
        return appId;
    }
}
