package com.boc.hopeheatapp.vocal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.IdentityListener;
import com.iflytek.cloud.IdentityResult;
import com.iflytek.cloud.IdentityVerifier;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.record.PcmRecorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author dwl
 * @date 2018/2/22.
 */
public class VocalRegister {
    private static final String TAG = "VocalRegister";
    private final int TOTAL_TIME = 5;

    // 密码类型
    // 默认为数字密码
    private int mPwdType = 3;

    // 数字密码类型为3，其他类型暂未开放
    private static final int PWD_TYPE_NUM = 3;

    // 数字声纹密码
    private String mNumPwd = "";

    // 数字声纹密码段，默认有5段
    private String[] mNumPwdSegs;

    // 是否可以录音
    private boolean mCanStartRecord = false;

    // 是否可以录音
    private boolean isStartWork = false;

    // 录音采样率
    private final int SAMPLE_RATE = 16000;

    // pcm录音机
    private PcmRecorder mPcmRecorder;

    // 用户id，唯一标识
    private String mAuthId;

    // 身份验证对象
    private IdentityVerifier mIdVerifier;

    private Context mContext;

    private VocalDownloadPwdListener mVocalDownloadPwdListener;

    private VocalRegistListener mVocalRegistListener;

    private int mModelStatus = VocalModelCmd.STATUS_UNKNOWN;

    // 模型操作类型
    private int mModelCmd;

    private String mGroupId;

    public VocalRegister(Context context, String authId) {
        mContext = context;
        mAuthId = authId;
        init();
    }

    /**
     * 下载密码监听器
     */
    private IdentityListener mDownloadPwdListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "onResult | result = " + result.getResultString());
            }
            switch (mPwdType) {
                case PWD_TYPE_NUM:
                    StringBuffer numberString = new StringBuffer();
                    try {
                        JSONObject object = new JSONObject(result.getResultString());
                        if (!object.has(VocalConst.NUM_PWD)) {
                            mNumPwd = null;
                            return;
                        }

                        JSONArray pwdArray = object.optJSONArray(VocalConst.NUM_PWD);
                        numberString.append(pwdArray.get(0));
                        for (int i = 1; i < pwdArray.length(); i++) {
                            numberString.append("-" + pwdArray.get(i));
                        }
                    } catch (JSONException e) {
                        if (Logger.isDebugable()) {
                            Logger.e(TAG, "DownloadPwdListener.onResult error", e);
                        }
                    }
                    mNumPwd = numberString.toString();
                    mNumPwdSegs = mNumPwd.split("-");

                    if (mVocalDownloadPwdListener != null) {
                        mVocalDownloadPwdListener.onDownloadSuccess(mNumPwd, mNumPwdSegs);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }

        @Override
        public void onError(SpeechError error) {
            if (mVocalDownloadPwdListener != null) {
                mVocalDownloadPwdListener.onDownloadError(error.getErrorCode(), error.getPlainDescription(true));
            }
        }
    };

    /**
     * 声纹注册监听器
     */
    private IdentityListener mEnrollListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "EnrollListener.onResult | result = " + result.getResultString() + ", islast = " + islast);
            }

            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result.getResultString());
                int ret = jsonResult.getInt("ret");

                if (ErrorCode.SUCCESS == ret) {

                    final int suc = Integer.parseInt(jsonResult.optString(VocalConst.SUC));
                    final int rgn = Integer.parseInt(jsonResult.optString(VocalConst.RGN));

                    if (suc == rgn) {
                        mModelStatus = VocalModelCmd.STATUS_EXISTED;
                        joinGroup();

                        if (mPcmRecorder != null) {
                            mPcmRecorder.stopRecord(true);
                        }
                    } else {
                        int nowTimes = suc + 1;
                        int leftTimes = TOTAL_TIME - nowTimes;

                        if (mVocalRegistListener != null) {
                            mVocalRegistListener.onNextSegs(nowTimes, leftTimes, mNumPwdSegs[nowTimes - 1]);
                        }
                    }
                } else {
                    SpeechError speechError = new SpeechError(ret);
                    showTip(speechError.getPlainDescription(true));

                    if (mVocalRegistListener != null) {
                        mVocalRegistListener.onRegisterFailed(ret, speechError.getPlainDescription(true));
                    }
                }
            } catch (JSONException e) {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "EnrollListener.onResult error", e);
                }
                if (mVocalRegistListener != null) {
                    mVocalRegistListener.onRegisterFailed(-1, "内部错误");
                }
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle bundle) {
            if (SpeechEvent.EVENT_VOLUME == eventType) {
                //showTip("音量：" + arg1);
                if (mVocalRegistListener != null) {
                    mVocalRegistListener.onRegisterVoloum(arg1);
                }
            } else if (SpeechEvent.EVENT_VAD_EOS == eventType) {
                //showTip("录音结束");
            }

        }

        @Override
        public void onError(SpeechError error) {
            isStartWork = false;
            if (mVocalRegistListener != null) {
                mVocalRegistListener.onRegisterFailed(error.getErrorCode(), error.getPlainDescription(true));
            }
        }

    };

    private void showTip(String tip) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, tip);
        }
    }

    /**
     * 录音机监听器
     */
    private PcmRecorder.PcmRecordListener mPcmRecordListener = new PcmRecorder.PcmRecordListener() {

        @Override
        public void onRecordStarted(boolean success) {
        }

        @Override
        public void onRecordReleased() {
        }

        @Override
        public void onRecordBuffer(byte[] data, int offset, int length) {
            StringBuffer params = new StringBuffer();
            params.append("rgn=").append(String.valueOf(TOTAL_TIME)).append(",");
            params.append("ptxt=").append(mNumPwd).append(",");
            params.append("pwdt=").append(mPwdType).append(",");
            mIdVerifier.writeData(VocalConst.IVP, params.toString(), data, 0, length);
        }

        @Override
        public void onError(SpeechError e) {
        }
    };

    private void init() {
        mGroupId = VocalConst.DEFAULT_GROUP_ID;
        mIdVerifier = IdentityVerifier.createVerifier(mContext, new InitListener() {

            @Override
            public void onInit(int errorCode) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "init | errorCode = " + errorCode);
                }
            }
        });
    }

    public void register(String authid, VocalRegistListener vocalRegistListener) {
        mAuthId = authid;
        mVocalRegistListener = vocalRegistListener;

        if (mModelStatus == VocalModelCmd.STATUS_UNKNOWN) {
            checkModelAndRegister();
        } else {
            registerImpl();
        }
    }

    private void registerImpl() {
        if (!isStartWork) {
            // 根据业务类型调用服务

            if (null == mNumPwdSegs) {
                // 启动录音机时密码为空，中断此次操作，下载密码
                if (mVocalRegistListener != null) {
                    mVocalRegistListener.onRegisterFailed(-1, "启动录音机时密码为空，请先下载声纹密码");
                }
                return;
            }
            vocalEnroll();
            isStartWork = true;
            mCanStartRecord = true;
        }

        if (mCanStartRecord) {
            try {
                mPcmRecorder = new PcmRecorder(SAMPLE_RATE, 40);
                mPcmRecorder.startRecording(mPcmRecordListener);
            } catch (SpeechError e) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "startSpeech error", e);
                }
            }
        }
    }

    /**
     * 下载密码
     */
    public void downloadPwd(VocalDownloadPwdListener vocalDownloadPwdListener) {
        mVocalDownloadPwdListener = vocalDownloadPwdListener;
        // 获取密码之前先终止之前的操作
        mIdVerifier.cancel();
        mNumPwd = null;
        // 设置下载密码参数
        // 清空参数
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ivp");

        // 设置声纹密码组数
        mIdVerifier.setParameter("rgn", String.valueOf(TOTAL_TIME));


        // 子业务执行参数，若无可以传空字符传
        StringBuffer params = new StringBuffer();
        // 设置模型操作的密码类型
        params.append("pwdt=").append(mPwdType).append(",").append("rgn=").append(String.valueOf(TOTAL_TIME));
        // 执行密码下载操作
        mIdVerifier.execute("ivp", "download", params.toString(), mDownloadPwdListener);

        if (Logger.isDebugable()) {
            Logger.d(TAG, "downloadPwd");
        }
    }

    /**
     * 注册
     */
    private void vocalEnroll() {
        if (mVocalRegistListener != null) {
            mVocalRegistListener.onNextSegs(1, mNumPwdSegs.length - 1, mNumPwdSegs[0]);
        }

        // 设置声纹注册参数
        // 清空参数
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ivp");
        // 设置会话类型
        mIdVerifier.setParameter(SpeechConstant.MFV_SST, "enroll");
        // 用户id
        mIdVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthId);
        // 设置监听器，开始会话
        mIdVerifier.startWorking(mEnrollListener);
    }

    public void stopSpeech() {
        mIdVerifier.stopWrite("ivp");
        if (null != mPcmRecorder) {

            mPcmRecorder.stopRecord(true);
        }
    }

    public void cancelOperation() {
        mIdVerifier.cancel();

        if (null != mPcmRecorder) {
            mPcmRecorder.stopRecord(true);
        }
    }

    private void joinGroup() {
        //joinGroupImpl();
        checkInGroup();
    }

    public void checkInGroup() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "checkInGroup | authid = " + mAuthId + ", groupId = " + mGroupId);
        }

        // sst=add，auth_id=eqhe，group_id=123456，scope=person
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ipt");
        // 用户id
        mIdVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthId);
        // 设置模型参数，若无可以传空字符传
        StringBuffer params2 = new StringBuffer();
        params2.append("scope=group");
        params2.append(",group_id=" + mGroupId);
        // 执行模型操作
        mIdVerifier.execute(VocalConst.IPT, VocalConst.QUERY, params2.toString(), new IdentityListener() {
                    @Override
                    public void onResult(IdentityResult result, boolean islast) {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "QueryListener.onResult | result = " + result.getResultString());
                        }
                        try {
                            JSONObject jsonResult = new JSONObject(result.getResultString());
                            JSONArray personArray = jsonResult.getJSONArray(VocalConst.PERSON);
                            boolean found = false;
                            for (int i = 0; i < personArray.length(); ++i) {
                                JSONObject personObject = personArray.getJSONObject(i);
                                String user = personObject.getString(VocalConst.USER);
                                if (StringUtil.equals(user, mAuthId)) {
                                    found = true;
                                    break;
                                }
                            }

                            if (found == true) {
                                if (Logger.isDebugable()) {
                                    Logger.d(TAG, "QueryListener.onResult | " + mAuthId + " has in group, notify register success");
                                }
                                notifySuccess();
                            } else {
                                if (Logger.isDebugable()) {
                                    Logger.d(TAG, "QueryListener.onResult | " + mAuthId + " has not in group, call joinGroup");
                                }
                                joinGroupImpl();
                            }
                        } catch (Exception e) {
                            notifyFailed(-2, "内部错误");
                        }
                    }

                    @Override
                    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

                    }

                    @Override
                    public void onError(SpeechError error) {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, error.getPlainDescription(true));
                        }
                        notifyFailed(error.getErrorCode(), error.getPlainDescription(true));
                    }
                }
        );
    }

    private void joinGroupImpl() {
        // sst=add，auth_id=eqhe，group_id=123456，scope=person
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ipt");
        // 用户id
        mIdVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthId);
        // 设置模型参数，若无可以传空字符传
        StringBuffer params2 = new StringBuffer();
        params2.append("auth_id=" + mAuthId);
        params2.append(",scope=person");
        params2.append(",group_id=" + mGroupId);
        // 执行模型操作
        mIdVerifier.execute("ipt", "add", params2.toString(), mAddListener);
    }

    /**
     * 加入组监听器
     */
    private IdentityListener mAddListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "mAddListener.onResult | result = " + result.getResultString());
            }
            try {
                JSONObject resObj = new JSONObject(result.getResultString());
                String groupName = resObj.getString("group_name");
                String groudId = resObj.getString("group_id");

                if (Logger.isDebugable()) {
                    Logger.d(TAG, "IdentityListener.onResult | groupName = " + groupName + ", groudId = " + groudId);
                }
            } catch (JSONException e) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "IdentityListener.onResult", e);
                }
            }
            showTip("加入组成功");

            mCanStartRecord = false;
            isStartWork = false;

            if (mVocalRegistListener != null) {
                mVocalRegistListener.onRegisterSuccess();
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }

        @Override
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
            mCanStartRecord = false;
            isStartWork = false;

            if (mVocalRegistListener != null) {
                mVocalRegistListener.onRegisterFailed(error.getErrorCode(), error.getPlainDescription(true));
            }
        }
    };

    /**
     * 模型操作
     *
     * @param cmd 命令
     */
    private void executeModelCommand(String cmd) {
        // 设置声纹模型参数
        // 清空参数
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ivp");
        // 用户id
        mIdVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthId);

        // 子业务执行参数，若无可以传空字符传
        StringBuffer params3 = new StringBuffer();
        // 设置模型操作的密码类型
        params3.append("pwdt=" + mPwdType + ",");
        // 执行模型操作
        mIdVerifier.execute("ivp", cmd, params3.toString(), mModelListener);
    }

    /**
     * 声纹模型操作监听器
     */
    private IdentityListener mModelListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "model operation:" + result.getResultString());
            }

            JSONObject jsonResult = null;
            int ret = ErrorCode.SUCCESS;
            try {
                jsonResult = new JSONObject(result.getResultString());
                ret = jsonResult.getInt(VocalConst.RET);
            } catch (JSONException e) {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "mModelListener error", e);
                }
            }

            switch (mModelCmd) {
                case VocalModelCmd.MODEL_QUE:
                    if (ErrorCode.SUCCESS == ret) {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "ModelListener.onResult | 模型存在(" + mAuthId + "), call deleteModel");
                        }
                        mModelStatus = VocalModelCmd.STATUS_EXISTED;
                        deleteModel();
                    } else {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "ModelListener.onResult | 模型不存在(" + mAuthId + "), call registerImpl");
                        }
                        mModelStatus = VocalModelCmd.STATUS_UNEXISTED;
                        registerImpl();
                    }
                    break;
                case VocalModelCmd.MODEL_DEL:
                    if (ErrorCode.SUCCESS == ret) {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "ModelListener.onResult | 模型已删除(" + mAuthId + "), call registerImpl");
                        }
                        mModelStatus = VocalModelCmd.STATUS_UNEXISTED;
                        registerImpl();
                    } else {
                        if (Logger.isDebugable()) {
                            Logger.d(TAG, "ModelListener.onResult | 模型已删除(" + mAuthId + "), notifyFailed");
                        }
                        notifyFailed(-2, "模型删除失败");
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }

        @Override
        public void onError(SpeechError error) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "ModelListener.onError" + error.getPlainDescription(true));
            }

            // 数据模型不存在
            if (error.getErrorCode() == 10116) {
                mModelStatus = VocalModelCmd.STATUS_UNEXISTED;
                registerImpl();

                if (Logger.isDebugable()) {
                    Logger.e(TAG, "ModelListener.onError | errorcode = 10116, call registerImpl");
                }
            }
        }
    };

    private void checkModelAndRegister() {
        executeModelCommand(VocalModelCmd.QUERY);
    }

    private void deleteModel() {
        executeModelCommand(VocalModelCmd.DELETE);
    }

    private void notifyFailed(int code, String msg) {
        if (mVocalRegistListener != null) {
            mVocalRegistListener.onRegisterFailed(code, msg);
        }
    }

    private void notifySuccess() {
        if (mVocalRegistListener != null) {
            mVocalRegistListener.onRegisterSuccess();
        }
    }

    private IdentityListener mDeleteListener = new IdentityListener() {
        @Override
        public void onResult(IdentityResult result, boolean islast) {
            Log.d(TAG, result.getResultString());
            try {
                JSONObject resObj = new JSONObject(result.getResultString());
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "DeleteListener.onResult | resObj == " + resObj.toString());
                }
                int ret = resObj.getInt("ret");
                if (0 != ret) {
                    onError(new SpeechError(ret));
                    return;
                } else {
                    if (result.getResultString().contains("user")) {
                        String user = resObj.getString("user");
                        showTip("删除组成员" + user + "成功");
                    } else {
                        showTip("删除组成功");


                    }
                }
            } catch (JSONException e) {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "DeleteListener.onResult error", e);
                }
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }

        @Override
        public void onError(SpeechError error) {
            //stopProgress();
            showTip(error.getPlainDescription(true));
        }
    };

    public String getGroupId() {
        return mGroupId;
    }
}
