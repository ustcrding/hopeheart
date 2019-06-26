package com.boc.hopeheatapp.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SemanticParser {
    private static final String DATA = "data";
    private static final String RESULT = "result";
    private static final String TEXT = "text";
    private static final String UUID = "uuid";
    private static final String RC = "rc";
    private static final String SEMANTIC = "semantic";
    private static final String SLOTS = "slots";
    private static final String SERVICE = "service";
    private static final String ANSWER = "answer";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String INTENT_TYPE = "intentType";
    private static final String CUSTOM = "custom";
    private static final String INTENT = "intent";
    private static final String NORM_VALUE = "normValue";


    private static final String AIR_DATA = "airData";
    private static final String AIR_QUALITY = "airQuality";
    private static final String CITY = "city";
    private static final String DATE = "date";
    private static final String DATE_LONG = "dateLong";
    private static final String HUMIDITY = "humidity";
    private static final String LAST_UPDATE_TIME = "lastUpdateTime";
    private static final String PM25 = "pm25";
    private static final String TEMP = "temp";
    private static final String TEMPRANGE = "tempRange";
    private static final String WEATHER = "weather";
    private static final String WEATHER_TYPE = "weatherType";
    private static final String WIND = "wind";
    private static final String WIND_LEVEL = "windLevel";

    private static final String TELEPHONE = "telephone";


    private static List<BaseAction.ActionMatcher> mAllMatchers;


    public static StringBuilder sSb;

    public static BaseAction parser(String json) {
        try {
            return parser(new JSONObject(json));
        } catch (JSONException e) {
        }
        return null;
    }

    public static BaseAction parser(JSONObject obj) {
        if (mAllMatchers == null) {
            mAllMatchers = new ArrayList<BaseAction.ActionMatcher>();
            mAllMatchers.add(new VoiceNaviAction.Matcher());
//            mAllMatchers.add(new DocumentAction.Matcher());
        }

        try {
            String service = "";
            if (obj.has(INTENT_TYPE) && CUSTOM.equals(obj.getString(INTENT_TYPE))) {
                BaseAction action = parserCustom(obj);
                if (action != null) {
                    return action;
                }
            }
            return parserDefaultAction(obj);
        } catch (JSONException e) {

        }
        return null;
    }

    private static BaseAction parserCustom(JSONObject obj) throws JSONException {
        DefaultAction action = new DefaultAction();
        parserBaseValue(action, obj);

        if (obj.has(SEMANTIC)) {
            JSONObject semantic = obj.getJSONArray(SEMANTIC).getJSONObject(0);
            if (semantic != null && semantic.has(INTENT)) {
                String intent = semantic.getString(INTENT);
                if (!TextUtils.isEmpty(intent)) {
                    if (semantic.has(SLOTS)) {
                        JSONArray array = semantic.getJSONArray(SLOTS);
                        if (array.length() > 0) {
                            int size = array.length();
                            String[] key = new String[size];
                            String[] value = new String[size];
                            int index = 0;
                            for (int i = 0; i < array.length(); ++i) {
                                JSONObject slot = array.getJSONObject(i);
                                if (slot.has(NAME) && slot.has(NORM_VALUE)) {
                                    key[index] = slot.getString(NAME);
                                    value[index] = slot.getString(NORM_VALUE);
                                    if (TextUtils.isEmpty(value[index])) {
                                        value[index] = slot.getString(VALUE);
                                    }
                                    ++index;
                                }
                            }
                            BaseAction dAction = processSlot(key, value, action, intent);
                            if (dAction != null) {
                                return dAction;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static BaseAction processSlot(String[] name, String[] value, BaseAction action, String intent) throws JSONException {
        for (BaseAction.ActionMatcher matcher : mAllMatchers) {
            BaseAction matchAction = matcher.getCloudResult(action, intent, name, value);
            if (matchAction != null) {
                return matchAction;
            }
        }

        return null;
    }

    private static BaseAction parserDefaultAction(JSONObject obj) throws JSONException {
        DefaultAction action = new DefaultAction();
        parserBaseValue(action, obj);

        if (!TextUtils.isEmpty(action.oriText)) {
            String query = filter(action.oriText);
            for (BaseAction.ActionMatcher matcher : mAllMatchers) {
                BaseAction matchAction = matcher.getLocalResult(action, query);
                if (matchAction != null) {
                    return matchAction;
                }
            }
        }
        action.speaking = "未能识别语义";
        return action;
    }

    private static void parserBaseValue(BaseAction action, JSONObject obj) throws JSONException {
        if (action != null && obj != null) {
            if (obj.has(RC)) {
                action.rc = obj.getInt(RC);
            }
            if (obj.has(UUID)) {
                action.uuid = obj.getString(UUID);
            }
            if (obj.has(TEXT)) {
                action.oriText = obj.getString(TEXT);
            }
        }
    }

    private static String filter(String text) {
        if (sSb == null) {
            sSb = new StringBuilder();
        } else {
            sSb.delete(0, sSb.length());
        }
        for (char ch : text.toCharArray()) {
            if (ch == '1') {
                sSb.append("一");
            } else if (ch == '2') {
                sSb.append("二");
            } else if (ch == '3') {
                sSb.append("三");
            } else if (ch == '4') {
                sSb.append("四");
            } else if (ch == '5') {
                sSb.append("五");
            } else if (ch == '6') {
                sSb.append("六");
            } else if (ch == '7') {
                sSb.append("七");
            } else if (ch == '8') {
                sSb.append("八");
            } else if (ch == '9') {
                sSb.append("九");
            } else if (ch == '0') {
                sSb.append("零");
            } else if (ch == ',' || ch == '?' || ch == '!' || ch == '.'
                    || ch == '，' || ch == '？' || ch == '！' || ch == '。') {
                continue;
            } else {
                sSb.append(ch);
            }
        }
        return sSb.toString();
    }
}
