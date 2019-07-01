package com.boc.hopeheatapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.log.Logger;
import java.lang.ref.WeakReference;

/**
 * @author dingr
 * @date 2017/11/10.
 */
public class SplashActivity extends TitleColorActivity {
    private ServerHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("SplashActivity", "onCreate");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        handler = new ServerHandler(this);
        initView();
    }

    private void initView() {
        handler.sendEmptyMessage(MSG_GOTO_MAIN);
    }

    private static final int MSG_SHOW_SPLASH = 1000;
    private static final int MSG_GOTO_MAIN = 1001;

    private static class ServerHandler extends Handler {

        private WeakReference<SplashActivity> activityRef;

        public ServerHandler(SplashActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            SplashActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            switch (msg.what) {
                case MSG_SHOW_SPLASH:
                    break;
                case MSG_GOTO_MAIN:
                    activity.onGotoMain();
                    break;
                default:
                    break;
            }

        }
    }

    private void onGotoMain() {
        if (UserManager.getInstance().hasLogin()) {
            handleAutoLogin();
        } else {
            //1:N声纹登录
            //ActivityJumper.startVocalIdentifyActivity(this);

            //1:1声纹登录
            ActivityJumper.startVocalVerifyActivity(this);
        }
        finish();
    }

    private void handleAutoLogin() {
        UserEntity user = UserManager.getInstance().getUser();
        if (user != null) {
            //正常用户，自动登陆
//            ActivityJumper.startHomeActivity(this);
//            ActivityJumper.startPsychologicalTestActivity(this);
//            ActivityJumper.startQuestionnaireCompleteActivity(this);
//            String url = "file:////android_asset/test.html";
//            ActivityJumper.startQuestionnaireCompleteActivity(this, url);
            ActivityJumper.startWelcomeActivity(this);
        } else {
            //其他状态用户，走登陆逻辑
            //ActivityJumper.startVocalIdentifyActivity(this);


            ActivityJumper.startLoginActivity(this);
        }
    }
}
