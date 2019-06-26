package com.boc.hopeheatapp.vocal;

import android.content.Context;

import com.boc.hopeheatapp.util.log.Logger;
import com.iflytek.cloud.IdentityResult;
import com.iflytek.cloud.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 单用户声纹识别接口
 *
 * @author dwl
 * @date 2018/2/12.
 */
public class VocalVerifier extends AbsVocalVerifier {

    private static final String TAG = "VocalVerifier";
    private VocalVerifyListener vocalVerifyListener;
    private String mAuthid;

    public VocalVerifier(Context context) {
        super(context);
    }

    @Override
    protected void vocalVerify(String authid, String verifyNum, VocalVerifyListener vocalVerifyListener) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "vocalVerify | authid = " + authid + ", verifyNum = " + verifyNum);
        }

        mVerifyNum = verifyNum;
        mAuthid = authid;
        this.vocalVerifyListener = vocalVerifyListener;

        // 设置声纹验证参数
        // 清空参数
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, VocalConst.IVP);
        // 设置会话类型
        mIdVerifier.setParameter(SpeechConstant.MFV_SST, VocalConst.VERIFY);
        // 验证模式，单一验证模式：sin
        mIdVerifier.setParameter(SpeechConstant.MFV_VCM, VocalConst.SIN);
        // 用户的唯一标识，在声纹业务获取注册、验证、查询和删除模型时都要填写，不能为空
        mIdVerifier.setParameter(SpeechConstant.AUTH_ID, authid);
        // 设置监听器，开始会话
        mIdVerifier.startWorking(mIdentityListener);
    }


    @Override
    protected void handleRecordBuffer(byte[] data, int offset, int length) {
        StringBuffer params = new StringBuffer();
        params.append("ptxt=" + mVerifyNum + ",");
        params.append("pwdt=" + mPwdType + ",");
        mIdVerifier.writeData("ivp", params.toString(), data, 0, length);
    }

    @Override
    protected void handleResult(IdentityResult result) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "IdentityListener.verify:" + result.getResultString());
        }

        try {
            JSONObject object = new JSONObject(result.getResultString());
            String decision = object.getString(VocalConst.DECISION);
            if (VocalConst.ACCEPTED.equalsIgnoreCase(decision)) {
                if (vocalVerifyListener != null) {
                    vocalVerifyListener.onVocalVerifySuccess(mAuthid);
                }

                if (Logger.isDebugable()) {
                    Logger.e(TAG, "IdentityListener.onResult | 验证通过");
                }
            } else {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "IdentityListener.onResult | 验证失败");
                }

                if (vocalVerifyListener != null) {
                    vocalVerifyListener.onVocalVerifyFailed(-1, "验证失败");
                }
            }
        } catch (JSONException e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "IdentityListener.onResult error", e);
            }

            if (vocalVerifyListener != null) {
                vocalVerifyListener.onVocalVerifyFailed(-2, "内部错误");
            }
        }
    }

}
