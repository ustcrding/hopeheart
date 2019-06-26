package com.boc.hopeheatapp.vocal;

import android.content.Context;

import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.IdentityResult;
import com.iflytek.cloud.SpeechConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 按组进行声纹识别
 *
 * @author dwl
 * @date 2018/2/13.
 */
public class VocalIdentifier extends AbsVocalVerifier {

    private static final String TAG = "VocalIdentifier";
    private String mGroupId;

    private final double DEFAULT_SCORE = 90.0d;

    public VocalIdentifier(Context context) {
        super(context);
    }

    @Override
    protected void handleRecordBuffer(byte[] data, int offset, int length) {
        StringBuffer params = new StringBuffer();
        params.append("ptxt=" + mVerifyNum + ",");
        params.append("pwdt=" + mPwdType + ",");
        params.append(",group_id=" + mGroupId + ",topc=3");

        mIdVerifier.writeData("ivp", params.toString(), data, 0, length);
    }

    @Override
    protected void vocalVerify(String groupId, String verifyNum, VocalVerifyListener vocalVerifyListener) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "vocalVerify | groupId = " + groupId + ", verifyNum = " + verifyNum);
        }

        mGroupId = groupId;

        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);

        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, VocalConst.IVP);

        // 设置会话类型
        mIdVerifier.setParameter(SpeechConstant.MFV_SST, VocalConst.IDENTIFY);

        // 设置组ID
        mIdVerifier.setParameter(VocalConst.GROUP_ID, mGroupId);

        // 设置监听器，开始会话
        mIdVerifier.startWorking(mIdentityListener);
    }

    @Override
    protected void handleResult(IdentityResult result) {
        if (null == result) {
            return;
        }
        try {
            String resultStr = result.getResultString();
            JSONObject resultJson = new JSONObject(resultStr);
            if (ErrorCode.SUCCESS == resultJson.getInt(VocalConst.RET)) {
                // 保存到历史记录中
                /*DemoApp.getmHisList().addHisItem(resultJson.getString("group_id"),
                        resultJson.getString("group_name") + "(" + resultJson.getString("group_id") + ")");
				FuncUtil.saveObject(VocalIdentifyActivity.this, DemoApp.getmHisList(), DemoApp.HIS_FILE_NAME);*/

                // 跳转到结果展示页面
                JSONObject json = resultJson.getJSONObject(VocalConst.IFV_RESULT);
                JSONArray candidates = json.getJSONArray(VocalConst.CANDIDATES);

                String user = candidates.getJSONObject(0).getString(VocalConst.USER);
                double score = candidates.getJSONObject(0).getDouble(VocalConst.SCORE);
                String accepted = candidates.getJSONObject(0).getString(VocalConst.DECISION);
                if (StringUtil.equals(accepted, VocalConst.ACCEPTED)) {
                    if (mVocalVerifyListener != null) {
                        mVocalVerifyListener.onVocalVerifySuccess(user);
                    }
                } else {
                    if (mVocalVerifyListener != null) {
                        mVocalVerifyListener.onVocalVerifyFailed(-1, "验证失败");
                    }
                }
            } else {
                if (mVocalVerifyListener != null) {
                    mVocalVerifyListener.onVocalVerifyFailed(-1, "验证失败");
                }
            }
        } catch (JSONException e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "IdentityListener.onResult error", e);
            }

            if (mVocalVerifyListener != null) {
                mVocalVerifyListener.onVocalVerifyFailed(-2, "内部错误");
            }
        }
    }
}
