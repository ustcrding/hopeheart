package com.yuntongxun.voipdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDeskManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.im.ECCmdMessageBody;
import com.yuntongxun.plugin.common.AppMgr;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.common.common.utils.RongXInUtils;
import com.yuntongxun.plugin.common.common.utils.ToastUtil;
import com.yuntongxun.plugin.common.ui.RongXinFragmentActivity;
import com.yuntongxun.plugin.voip.Voip;

public class MainActivity extends RongXinFragmentActivity {
    private TextView tv_show;
    private EditText et_account;
    private Button btn_voip_voice, btn_voip_video, btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.e("MainActivity onCreate: " + getIntent().toUri(Intent.URI_INTENT_SCHEME) + "\n" + getIntent());


        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_show);
        et_account = (EditText) findViewById(R.id.et_account);
        btn_voip_voice = (Button) findViewById(R.id.btn_voip_voice);
        btn_voip_video = (Button) findViewById(R.id.btn_voip_video);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        tv_show.setText(getIntent().getStringExtra("userid"));
        btn_voip_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //语音通话
                String toAccountVoice = et_account.getText().toString();
                if (TextUtils.isEmpty(toAccountVoice)) {
                    return;
                }

                /**
                 * 发起语音通话
                 * @param Context
                 * @param callType    呼叫类型 ECVoIPCallManager.CallType.VOICE or ECVoIPCallManager.CallType.VIDEO
                 * @param nickname    被叫者昵称 也可以通过IVoipCallBack接口 设置
                 * @param contactId   被叫者userID
                 * @param phoneNumber 对方手机号 如果 isCallBack设置为ture，那么该参数必须要传
                 * @param isCallBack  true表示回拨 false表示不回拨
                 * @param IViopCallBack  建议写为实现类 名字为VoipImpl，如果是其他名字请把YuntxNotifyReceiver中的VoipImpl替换
                 *                       实现IVoipCallBack接口
                 *                       参考Demo中的VoipImpl实现类
                 */
                Voip.startCallAction(
                        MainActivity.this,
                        ECVoIPCallManager.CallType.VOICE,
                        "姓名-" + toAccountVoice,
                        toAccountVoice,
                        "手机号",
                        false
                );
            }
        });
        btn_voip_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //视频通话
                String toAccountVideo = et_account.getText().toString();
                if (TextUtils.isEmpty(toAccountVideo)) {
                    return;
                }


                /**
                 * 发起视频通话
                 * @param Context
                 * @param callType    呼叫类型 ECVoIPCallManager.CallType.VOICE or ECVoIPCallManager.CallType.VIDEO
                 * @param nickname    被叫者昵称 也可以通过IVoipCallBack接口 设置
                 * @param contactId   被叫者userID
                 * @param phoneNumber 对方手机号 如果 isCallBack设置为ture，那么该参数必须要传
                 * @param isCallBack  true表示回拨 false表示不回拨
                 * @param IViopCallBack  建议写为实现类 名字为VoipImpl，如果是其他名字请把YuntxNotifyReceiver中的VoipImpl替换
                 *                       实现IVoipCallBack接口
                 *                       参考Demo中的VoipImpl实现类
                 */
                Voip.startCallAction(
                        MainActivity.this,
                        ECVoIPCallManager.CallType.VIDEO,
                        "姓名-" + toAccountVideo,
                        toAccountVideo,
                        "手机号",
                        false

                );

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.close();
                SDKCoreHelper.logout();
            }
        });

        findViewById(R.id.uploadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ECMessage message = ECMessage.createECMessage(ECMessage.Type.CMD);
                message.setTo(AppMgr.getClientUser().getUserId());
                message.setSessionId(AppMgr.getClientUser().getUserId());
                ECCmdMessageBody body = new ECCmdMessageBody("上传日志");
                message.setBody(body);
                message.setUserData("customtype=\"601\";cmd=\"rongxin://debuglog\"");

                ECDevice.getECChatManager().sendMessage(message, new ECChatManager.OnSendMessageListener() {
                    @Override
                    public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                        if (ecError.errorCode == 200){

                        }
                    }

                    @Override
                    public void onProgress(String s, int i, int i1) {
                    }
                });

            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKCoreHelper.ACTION_KICK_OFF);
        filter.addAction(SDKCoreHelper.ACTION_LOGOUT);
        registerReceiver(internalReceiver, filter);
    }

    private InternalReceiver internalReceiver = new InternalReceiver();

    @Override
    protected void onDestroy() {
        LogUtil.e("MainActivity", "onDestroy");
        super.onDestroy();
        unregisterReceiver(internalReceiver);
    }

    private class InternalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            if (intent.getAction().equals(SDKCoreHelper.ACTION_KICK_OFF)) {
                LogUtil.e("异地登入SDK状态" + ECDevice.isInitialized());
                if (ECDevice.isInitialized()) {
                    ECDevice.unInitial();
                }
                LogUtil.e("异地登入SDK状态new==" + ECDevice.isInitialized());
                String kickoffText = intent.getStringExtra("kickoffText");
                ToastUtil.showMessage(kickoffText);
                finish();
            } else if (intent.getAction().equals(SDKCoreHelper.ACTION_LOGOUT)) {
                // 断开SDK连接之后的逻辑处理
                // ......释放资源
                if (ECDevice.isInitialized()) {
                    ECDevice.unInitial();
                }
                finish();
            }
        }
    }


}
