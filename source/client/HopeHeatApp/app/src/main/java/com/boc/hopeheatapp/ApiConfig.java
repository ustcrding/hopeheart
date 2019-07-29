package com.boc.hopeheatapp;


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
     * 知识库的基本接口
     */
    public static final String KB_BASE_URL = "http://192.168.43.165:9090";


    private static String appId = "";


    /**
     * BASE url
     */
    public static final String HOPE_HEAT_BASE_URL = "http://192.168.43.165";

    public static void setAppId(String appId) {
        ApiConfig.appId = appId;
    }

    public static String getAppId() {
        return appId;
    }
}
