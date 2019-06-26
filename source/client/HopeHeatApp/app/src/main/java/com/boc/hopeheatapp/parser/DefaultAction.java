package com.boc.hopeheatapp.parser;


import org.json.JSONException;
import org.json.JSONObject;

public class DefaultAction extends BaseAction {
    public DefaultAction() {

    }

    public DefaultAction(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject obj = new JSONObject(json);
                from(obj);
            } catch (JSONException e) {
            }
        }
    }

    public DefaultAction(BaseAction action) {
        copy(action);
    }

    @Override
    protected int getType() {
        return ACTION_DEFAULT;
    }

    @Override
    protected JSONObject getDataJson() {
        try {
            return to(new JSONObject());
        } catch (JSONException e) {
        }
        return null;
    }
}
