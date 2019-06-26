package com.boc.hopeheatapp.parser;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseAction {
    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String RC_KEY = "rc";
    private static final String UUID_KEY = "uuid";
    private static final String ORITEXT_KEY = "ot";
    private static final String SPEAKING_KEY = "sp";
    private static final String EXTRA_KEY = "ext";

    /**
     * 未匹配的语义
     */
    public static final int ACTION_DEFAULT = 0;
    /**
     * 大会资料
     */
    public static final int ACTION_DOCUMENT = 1;

    // 语音导航
    public static final int ACTION_VOICE_NAVI = 2;

    public int rc;
    public String oriText;
    public String speaking;
    public String uuid;
    public String extra;

    public String toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(TYPE, getType());

            JSONObject data = getDataJson();
            if (data != null) {
                obj.put(DATA, data);
            } else {
                obj.put(DATA, "");
            }

            return obj.toString();
        } catch (JSONException e) {
        }
        return null;
    }

    public static BaseAction fromJson(String json) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
            int type = obj.getInt(TYPE);
            String data = obj.getString(DATA);
            if (TextUtils.isEmpty(data)) {
                return null;
            }

            switch (type) {
                case ACTION_VOICE_NAVI:
                    return new VoiceNaviAction(data);
                default:
                    return new DefaultAction(data);
            }
        } catch (JSONException e) {
        }

        return null;
    }

    protected void copy(BaseAction action) {
        this.oriText = action.oriText;
        this.rc = action.rc;
        this.uuid = action.uuid;
    }

    protected void from(JSONObject obj) throws JSONException{
        if (obj != null) {
            if (obj.has(RC_KEY)) {
                rc = obj.getInt(RC_KEY);
            }
            if (obj.has(UUID_KEY)) {
                uuid = obj.getString(UUID_KEY);
            }
            if (obj.has(ORITEXT_KEY)) {
                oriText = obj.getString(ORITEXT_KEY);
            }
            if (obj.has(SPEAKING_KEY)) {
                speaking = obj.getString(SPEAKING_KEY);
            }
            if (obj.has(EXTRA_KEY)) {
                extra = obj.getString(EXTRA_KEY);
            }
        }
    }

    public JSONObject to(JSONObject obj) throws JSONException{
        if (obj != null) {
            obj.put(RC_KEY, rc);
            if (!TextUtils.isEmpty(oriText)) {
                obj.put(ORITEXT_KEY, oriText);
            }
            if (!TextUtils.isEmpty(speaking)) {
                obj.put(SPEAKING_KEY, speaking);
            }
            if (!TextUtils.isEmpty(uuid)) {
                obj.put(UUID_KEY, uuid);
            }
            if (!TextUtils.isEmpty(extra)) {
                obj.put(EXTRA_KEY, extra);
            }
            return obj;
        }
        return null;
    }

    protected abstract int getType() ;

    protected abstract JSONObject getDataJson();

    public interface ActionMatcher {
        BaseAction getCloudResult(BaseAction action, String intent, String[] name, String[] normName);
        BaseAction getLocalResult(BaseAction action, String query);
    }
}
