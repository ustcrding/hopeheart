package com.boc.hopeheatapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.parser.TextUtils;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;

import rx.Subscriber;

/**
 * 用户注册页面
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class UserInfoActivity extends TitleColorActivity {

    private static final String TAG = UserInfoActivity.class.getSimpleName();

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
     * 性别
     */
    private TextView tvSex;
    /**
     * 详细地址
     */
    private EditText etAddress;

    /**
     * 联系方式
     */
    private EditText tvPhone;

    /**
     * 备注
     */
    private EditText tvMemo;

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

        tvSex = (TextView) findViewById(R.id.user_sex);

        tvPhone = (EditText) findViewById(R.id.register_user_phone);

        tvMemo = (EditText) findViewById(R.id.et_memo);
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

//        findViewById(R.id.search_info).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityJumper.startHomeActivity(UserInfoActivity.this);
//            }
//        });

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

        tvSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexList();
            }
        });
    }

    private void initData() {

    }

    public void onClickedBack() {
        finish();
    }

    private void onClickedResiter() {
        String userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShort(this, R.string.fill_tips);
            return ;
        }
        String defChoice = getResources().getString(R.string.choice_default);
        String sex = tvSex.getText().toString();
        if (defChoice.equals(sex)) {
            ToastUtils.showShort(this, R.string.fill_tips);
            return ;
        }

        String credentialsType = tvCredentialsType.getText().toString();
        if (defChoice.equals(credentialsType)) {
            ToastUtils.showShort(this, R.string.fill_tips);
            return;
        }
        int credentialsIndex = -1;
        CharSequence[] arr = getResources().getTextArray(R.array.credentials_type);
        for (int i = 0 ; i < arr.length; ++i) {
            if (credentialsType.equals(arr[i])) {
                credentialsIndex = i;
                break;
            }
        }
        String credentialsNo = etCredentialsNo.getText().toString();
        if (TextUtils.isEmpty(credentialsNo)) {
            ToastUtils.showShort(this, R.string.fill_tips);
            return ;
        }

        String phone = tvPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, R.string.fill_tips);
            return;
        }

        String city = tvCityName.getText().toString();
        if (defChoice.equals(city)) {
            ToastUtils.showShort(this, R.string.fill_tips);
            return;
        }

        String address = etAddress.getText().toString();
//        if (TextUtils.isEmpty(address)) {
//            return;
//        }

        String memo = tvMemo.getText().toString();


        UserLoader userLoader = new UserLoader();
        UserEntity user = UserManager.getInstance().getUser();
        userLoader.uploadUserInfo(user.getUserId() + "", userName, sex.equals("男") ? "0" : "1", credentialsIndex + "", credentialsNo, phone, "", city, address, memo).subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showShort(UserInfoActivity.this, R.string.error_tips);
            }

            @Override
            public void onNext(Void s) {
                ToastUtils.showShort(UserInfoActivity.this, R.string.success_tips);
                finish();
            }
        });
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

    private void showSexList() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.sex_name_title);
        AlertDialog dialog = alertBuilder.setItems(R.array.sex_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvSex.setText(getResources().getTextArray(R.array.sex_list)[i]);
                dialogInterface.dismiss();
            }
        }).create();
        dialog.show();
    }
}
