package com.boc.hopeheatapp.parser;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class DocumentAction extends BaseAction {
    private static final String QUERY_FILE = "query_file";
    private static final String FILE_NAME = "filename";
    private static final String FUZZY_FILE_NAME = "fuzzyfilename";

    public static final String DOC_FULL_MATCHER = "我要找第十四届人民代表大会[\\u4e00-\\u9fa5]+(议程|草案)"; //open
    public static final String DOC_FUZZY_MATCHER = "我要找[\\u4e00-\\u9fa5]+(议程|草案)";    //show all

    public static final String FULL_KEY = "full";
    public boolean full;


    public DocumentAction(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject obj = new JSONObject(json);
                from(obj);
                full = obj.getBoolean(FULL_KEY);
            } catch (JSONException e) {
            }
        }
    }

    public DocumentAction(BaseAction action) {
        copy(action);
    }

    @Override
    protected int getType() {
        return ACTION_DOCUMENT;
    }

    @Override
    protected JSONObject getDataJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(FULL_KEY, full);
            return to(obj);
        } catch (JSONException e) {
        }
        return null;
    }

    public static class Matcher implements ActionMatcher{

        @Override
        public BaseAction getCloudResult(BaseAction action, String intent, String[] name, String[] normName) {
            if (QUERY_FILE.equals(intent)) {
                if (name != null && name.length > 0 && normName != null && normName.length > 0) {
                    for (int i = 0; i < name.length; ++i) {
                        if (FILE_NAME.equals(name[i])) {
                            DocumentAction dAction = new DocumentAction(action);
                            dAction.full = true;
                            dAction.extra = normName[i];
                            dAction.speaking = dAction.extra;
                            return dAction;
                        } else if (FUZZY_FILE_NAME.equals(name[i])) {
                            DocumentAction dAction = new DocumentAction(action);
                            dAction.full = false;
                            dAction.extra = normName[i];
                            dAction.speaking = dAction.extra;
                            return dAction;
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public BaseAction getLocalResult(BaseAction action, String query) {
            if (Pattern.matches(DOC_FULL_MATCHER, query)) {
                DocumentAction dAction = new DocumentAction(action);
                dAction.full = true;
                dAction.extra = query.substring(13);
                dAction.speaking = query;
                return dAction;
            } else if (Pattern.matches(DOC_FUZZY_MATCHER, query)) {
                DocumentAction dAction = new DocumentAction(action);
                dAction.full = true;
                dAction.extra = query.substring(3, query.length() - 2);
                dAction.speaking = query;
                return dAction;
            }
            return null;
        }
    }
}
