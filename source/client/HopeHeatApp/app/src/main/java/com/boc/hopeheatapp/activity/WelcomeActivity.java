package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Subscriber;

/**
 * @author dwl
 * @date 2018/2/8.
 */
public class WelcomeActivity extends TitleColorActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        findViewById(R.id.read_from_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                doUserBind();
            }
        });

        findViewById(R.id.input_personal_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startUserInfoActivity(WelcomeActivity.this);
            }
        });

        findViewById(R.id.ignore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 查询救援知识
        findViewById(R.id.btn_search_rescue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 查询心理知识
        findViewById(R.id.btn_search_psychology).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void doUserBind() {
        UserLoader userLoader = new UserLoader();
        userLoader.userBind("" + UserManager.getInstance().getUser().getUserId()).subscribe(new Subscriber<UserEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getApplicationContext(), "未找到用户", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserEntity user) {
                if (user != null && StringUtil.equals(user.getPreserve(), "0")) {
                    UserManager.getInstance().getUser().setPreserve(user.getPreserve());
                    UserManager.getInstance().saveUser();
                    Toast.makeText(getApplicationContext(), "已发现用户身份", Toast.LENGTH_LONG).show();
                    ActivityJumper.startMainActivity(getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(), "未找到用户", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
