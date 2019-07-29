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
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Subscriber;

/**
 * 用户注册页面
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class RegisterActivity extends TitleColorActivity {

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
    private TextView btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        initView();
        addListener();

        initData();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_register);

        btnBack.setVisibility(View.VISIBLE);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();


        etUserName = (EditText) findViewById(R.id.register_username);
        etPassword = (EditText) findViewById(R.id.register_pwd);
        btnRegister = (TextView) findViewById(R.id.btn_register);
    }

    private void addListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedBack();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedResiter();
            }
        });
    }

    private void initData() {

    }

    public void onClickedBack() {
        finish();
    }

    private void onClickedResiter() {
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

        doRegister(username, pwd);
    }

    private void doRegister(String username, String pwd) {
        UserLoader userLoader = new UserLoader();
        userLoader.register(username, pwd).subscribe(new Subscriber<UserEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showLong(getApplication(), throwable.getMessage());
            }

            @Override
            public void onNext(UserEntity userEntity) {
                handleRegisterSuccess(userEntity);
            }
        });
    }

    private void handleRegisterSuccess(UserEntity userEntity) {
        if (userEntity == null) {
            ToastUtils.showLong(getApplication(), R.string.register_failed);
            return;
        }

        ToastUtils.showShort(getApplication(), R.string.register_success);
        UserManager.getInstance().setUser(userEntity);

        // 打开home页面
        ActivityJumper.startLoginActivity(getApplicationContext());
        finish();
    }
}
