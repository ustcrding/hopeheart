package com.boc.hopeheatapp.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author dwl
 * @date 2018/2/8.
 */
public class VoiceNaviAction extends BaseAction {
    private static final String INTENT_VOICE_NAVI = "voice_navi";
    private static final String SLOT_OPEN = "open";
    private static final String SLOT_FUNCMENU = "functionmenu";

    private static final String PARAM = "param";

    private String action;
    private String param;


    public VoiceNaviAction(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject obj = new JSONObject(json);
                from(obj);
            } catch (JSONException e) {
            }
        }
    }

    public VoiceNaviAction(BaseAction action) {
        copy(action);
    }

    @Override
    protected int getType() {
        return ACTION_VOICE_NAVI;
    }

    @Override
    protected JSONObject getDataJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(PARAM, param);
            return to(obj);
        } catch (JSONException e) {
        }
        return null;
    }

    public static class Matcher implements ActionMatcher {
        @Override
        public BaseAction getCloudResult(BaseAction action, String intent, String[] name, String[] normName) {
            if (INTENT_VOICE_NAVI.equals(intent)) {
                VoiceNaviAction dAction = new VoiceNaviAction(action);

                if (name != null && name.length > 0 && normName != null && normName.length > 0) {
                    for (int i = 0; i < name.length; ++i) {
                        if (SLOT_OPEN.equals(name[i])) {
                            dAction.action = name[i];
                        } else if (SLOT_FUNCMENU.equals(name[i])) {
                            dAction.param = normName[i];
                        }
                    }
                }
                if (dAction.param != null) {
                    dAction.action = SLOT_OPEN;
                }
                return dAction;
            }
            return null;
        }

        @Override
        public BaseAction getLocalResult(BaseAction action, String query) {
            return null;
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
