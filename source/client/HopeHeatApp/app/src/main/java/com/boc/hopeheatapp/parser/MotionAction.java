package com.boc.hopeheatapp.parser;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class MotionAction extends BaseAction {
    private static final String QUERY_SUGGEXTION = "query_suggestion";
    private static final String QUERY_YIAN = "query_yian";
    private static final String SUBJECT = "subject";

    public static final String MOTION_FULL_MATCHER = "查看[\\u4e00-\\u9fa5]+(的议案|的建议)";  //click
    public static final String MOTION_FUZZY_MATCHER = "查看[\\u4e00-\\u9fa5]+";   //click
    public static final String MOTION_FUZZY_MATCHER_1 = "查找议案建议[\\u4e00-\\u9fa5]+";   //click

    public static final String SUGGEXTION_KEY = "sug";
    public boolean suggestion;
    public MotionAction(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject obj = new JSONObject(json);
                from(obj);
            } catch (JSONException e) {
            }
        }
    }

    public MotionAction(BaseAction action) {
        copy(action);
    }

    @Override
    protected int getType() {
        //return ACTION_MOTION;
        return -1;
    }

    @Override
    protected JSONObject getDataJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(SUGGEXTION_KEY, suggestion);
            return to(obj);
        } catch (JSONException e) {
        }
        return null;
    }

    public static class Matcher implements ActionMatcher{

        @Override
        public BaseAction getCloudResult(BaseAction action, String intent, String[] name, String[] normName) {
            if (QUERY_SUGGEXTION.equals(intent)) {
                MotionAction dAction = new MotionAction(action);
                dAction.suggestion = true;

                if (name != null && name.length > 0 && normName != null && normName.length > 0) {
                    for (int i = 0; i < name.length; ++i) {
                        if (SUBJECT.equals(name[i])) {
                            dAction.extra = normName[i];
                            dAction.speaking = dAction.extra;
                            break;
                        }
                    }
                }
                return dAction;
            } else if (QUERY_YIAN.equals(intent)) {
                MotionAction dAction = new MotionAction(action);
                dAction.suggestion = false;

                if (name != null && name.length > 0 && normName != null && normName.length > 0) {
                    for (int i = 0; i < name.length; ++i) {
                        if (SUBJECT.equals(name[i])) {
                            dAction.extra = normName[i];
                            dAction.speaking = dAction.extra;
                            return dAction;
                        }
                    }
                }
                return dAction;
            }
            return null;
        }

        @Override
        public BaseAction getLocalResult(BaseAction action, String query) {
            if (Pattern.matches(MOTION_FULL_MATCHER, query)) {
                MotionAction dAction = new MotionAction(action);
                dAction.extra = query.substring(2, query.length() - 3);
                dAction.speaking = query;
                return dAction;
            } else if (Pattern.matches(MOTION_FUZZY_MATCHER, query)) {
                MotionAction dAction = new MotionAction(action);
                dAction.extra = query.substring(2);
                dAction.speaking = query;
                return dAction;
            } else if (Pattern.matches(MOTION_FUZZY_MATCHER_1, query)) {
                MotionAction dAction = new MotionAction(action);
                dAction.extra = query.substring(6);
                dAction.speaking = query;
                return dAction;
            }
            return null;
        }
    }
}
