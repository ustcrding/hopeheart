package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import rx.Subscriber;

/**
 * 用户登录页面
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class LoginActivity extends TitleColorActivity {

    /**
     * title 左侧返回按钮
     */
    private View btnBack;

    /**
     * title正文
     */
    private TextView tvTitle;

    /**
     * title右侧按钮
     */
    private TextView btnTitleRight;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        addListener();

        initData();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);
        btnRegister = (TextView) findViewById(R.id.btn_register);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_login);

        btnBack.setVisibility(View.INVISIBLE);
        btnTitleRight.setVisibility(View.INVISIBLE);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedBack();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedRegister();
            }
        });
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

//        doLogin(username, pwd);
//        ActivityJumper.startMainActivity(this);
        ActivityJumper.startWelcomeActivity(getApplicationContext(), false);
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

        if (!StringUtil.equals(userEntity.getRoleType(), UserEntity.TYPE_DOCTOR)) {
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
