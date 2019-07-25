package com.boc.hopeheatapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.plugin.common.AppMgr;
import com.yuntongxun.plugin.common.ClientUser;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.ECPreferences;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.common.common.utils.ToastUtil;

import rx.Subscriber;

/**
 * 用户登录页面
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class LoginActivity extends TitleColorActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * 用户名
     */
    private EditText etUserName;

    /**
     * 密码
     */
    private EditText etPassword;

    /**
     * 注册按钮
     */
    private TextView btnLogin;

    // 注册按钮
    private TextView btnRegister;

    private BroadcastReceiver mSDKNotifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SDKCoreHelper.isLoginSuccess(intent)) {
                String pushToken = ECPreferences.getSharedPreferences().getString("pushToken", null);
                LogUtil.d(TAG, "SDK connect Success ,reportToken:" + pushToken);
                if (!TextUtils.isEmpty(pushToken)) {
                    ECDevice.reportHuaWeiToken(pushToken);
                }
//                Intent action = new Intent(LoginActivity.this, MainActivity.class);
//                action.putExtra("userid", AppMgr.getUserId());
//                startActivity(action);
//                finish();

//                String str1 = intent.toUri(Intent.URI_INTENT_SCHEME);
//                String str2 = action.toUri(Intent.URI_INTENT_SCHEME);
//                LogUtil.d(TAG, "uri1:" + str1 + ",uri2:" + str2);

            } else {
                int error = intent.getIntExtra("error", 0);
                if (error == SdkErrorCode.CONNECTING) return;
                LogUtil.e(TAG, "登入失败[" + error + "]");
                ToastUtil.showMessage("登入失败 == " + error);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        addListener();

        initData();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SDKCoreHelper.ACTION_SDK_CONNECT);
        intentFilter.addAction(SDKCoreHelper.ACTION_LOGOUT);
        registerReceiver(mSDKNotifyReceiver, intentFilter);
        if (AppMgr.getClientUser() != null) {
            LogUtil.d(TAG, "SDK auto connect...");
            SDKCoreHelper.init(getApplicationContext());
            //设置自为debug模式
            LogUtil.setDebugMode(true);
        }
    }

    private void initTitle() {

        btnRegister = (TextView) findViewById(R.id.btn_register);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_pwd);
        btnLogin = (TextView) findViewById(R.id.btn_login);
    }

    private void addListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedLogin();
            }
        });

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickedRegister();
//            }
//        });
    }

    private void initData() {
        String lastUserName = BocSettings.getInstance().getString(BocSettings.LOGIN_USERNAME);
        String lastPassword = BocSettings.getInstance().getString(BocSettings.LOGIN_PASSWORD);

        if (StringUtil.isNotEmpty(lastUserName)
                && StringUtil.isNotEmpty(lastPassword)) {
            etUserName.setText(lastUserName);
            etUserName.setSelection(lastUserName.length());

            etPassword.setText(lastPassword);
            etPassword.setSelection(lastPassword.length());
        }
    }

    public void onClickedBack() {
        finish();
    }

    private void onClickedLogin() {
        String username = etUserName.getText().toString();
        String pwd = etPassword.getText().toString();

        if (StringUtil.isEmpty(username)) {
            ToastUtils.showLong(getApplicationContext(), R.string.tip_username_invalid);
            return;
        }

        if (StringUtil.isEmpty(pwd)) {
            ToastUtils.showLong(getApplicationContext(), R.string.tip_pwd_invalid);
            return;
        }

        doLogin(username, pwd);
//        ActivityJumper.startMainActivity(this);
//        ActivityJumper.startWelcomeActivity(getApplicationContext(), false);
//        ActivityJumper.startConsultActivity(getApplicationContext());
    }

    private void doLogin(final String username, final String pwd) {

        UserLoader userLoader = new UserLoader();
        userLoader.login(username, pwd).subscribe(new Subscriber<UserEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                handleLoginFailed(throwable);
            }

            @Override
            public void onNext(UserEntity userEntity) {
                handleLoginSuccess(username, pwd, userEntity);
            }
        });
    }

    private void handleLoginSuccess(String userName, String password, UserEntity userEntity) {
        if (userEntity == null) {
            ToastUtils.showLong(getApplication(), R.string.login_failed);
            return;
        }

        ToastUtils.showShort(getApplication(), R.string.login_success);
        UserManager.getInstance().setUser(userEntity);
        BocSettings.getInstance().setSetting(BocSettings.LOGIN_USERNAME, userName);
        BocSettings.getInstance().setSetting(BocSettings.LOGIN_PASSWORD, password);

        if (!StringUtil.equals(userEntity.getRoleType(), UserEntity.TYPE_DOCTOR)
                && !StringUtil.equals(userEntity.getRoleType(), UserEntity.TYPE_VOLUNTEER)) {
            if (StringUtil.equals(userEntity.getPreserve(), "1")) {
                // 需要完善个人信息
                ActivityJumper.startWelcomeActivity(getApplicationContext(), true);
            } else {
                // 打开home页面
                ActivityJumper.startWelcomeActivity(getApplicationContext(), false);
            }
        } else {
            ActivityJumper.startMainActivity(getApplicationContext());
        }

        if (userEntity != null) {
            ClientUser.UserBuilder builder = new ClientUser.UserBuilder(userEntity.getPhone(), userEntity.getUsername() );
            // 以下参数均为可选
            SDKCoreHelper.login(builder.build());
            //设置自为debug模式
 //           LogUtil.setDebugMode(true);
        }
        finish();
    }

    public void handleLoginFailed(Throwable throwable) {
        String message = throwable.getMessage();
        if (StringUtil.isNotBlank(message)) {
            ToastUtils.showLong(getApplication(), throwable.getMessage());
        } else {
            ToastUtils.showLong(getApplication(), R.string.login_failed);
            return;
        }
    }

    /**
     * 点击注册按钮
     */
    public void onClickedRegister() {
        ActivityJumper.startRegisterActivity(this);
    }
}
