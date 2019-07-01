package com.boc.hopeheatapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;

/**
 * 用户注册页面
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class UserInfoActivity extends TitleColorActivity {

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
     * id
     */
    private EditText etUserId;
    /**
     * 用户名
     */
    private EditText etUserName;
    /**
     * 证件类型
     */
    private TextView tvCredentialsType;
    /**
     * 证件号码
     */
    private EditText etCredentialsNo;
    /**
     * 城市名称
     */
    private TextView tvCityName;
    /**
     * 详细地址
     */
    private EditText etAddress;

    /**
     * 注册按钮
     */
    private LinearLayout btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_userinfo);

        initView();
        addListener();

        initData();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.user_info_title);

        btnBack.setVisibility(View.VISIBLE);
        btnTitleRight.setVisibility(View.VISIBLE);
        btnTitleRight.setText(R.string.submit);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        etUserId = (EditText) findViewById(R.id.register_userid);
        etUserName = (EditText) findViewById(R.id.register_username);
        tvCredentialsType = (TextView) findViewById(R.id.register_credentials_type);
        etCredentialsNo = (EditText) findViewById(R.id.register_credentials_no);
        tvCityName = (TextView) findViewById(R.id.city_name);
        etAddress = (EditText) findViewById(R.id.address_detail);
        btnRegister = (LinearLayout) findViewById(R.id.ll_title_right_container);
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

        findViewById(R.id.search_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startHomeActivity(UserInfoActivity.this);
            }
        });

        findViewById(R.id.ll_credentials_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCredentialsList();
            }
        });

        findViewById(R.id.ll_city_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityList();
            }
        });
    }

    private void initData() {

    }

    public void onClickedBack() {
        finish();
    }

    private void onClickedResiter() {
        //TODO
    }

    private void showCredentialsList() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.user_credentials_type);
        AlertDialog dialog = alertBuilder.setItems(R.array.credentials_type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvCredentialsType.setText(getResources().getTextArray(R.array.credentials_type)[i]);
                dialogInterface.dismiss();
            }
        }).create();
        dialog.show();
    }

    private void showCityList() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.city_name_title);
        AlertDialog dialog = alertBuilder.setItems(R.array.city_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvCityName.setText(getResources().getTextArray(R.array.city_list)[i]);
                dialogInterface.dismiss();
            }
        }).create();
        dialog.show();
    }
}
