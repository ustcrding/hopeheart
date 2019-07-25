package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ConsultDetailEntity;
import com.boc.hopeheatapp.model.VictimEntity;
import com.boc.hopeheatapp.service.biz.ConsultLoader;
import com.boc.hopeheatapp.service.biz.VictimLoader;

import rx.Subscriber;

/**
 * 频道列表界面
 *
 * @author dwl
 * @date 2018/5/28.
 */
public class VictimDetailActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private TextView tvVictimName;
    private TextView tvVictimSex;
    private TextView tvVictimAge;
    private TextView tvDate;
    private TextView tvAddress;
    private TextView tvRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_victim_detail);

        initView();

        initData();

        addListener();

        String victimId = getIntent().getStringExtra(ActivityJumper.EXTRA_VICTIM_ID);
        requestDetail(victimId);
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.victim_detail_title);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        tvVictimName = (TextView) findViewById(R.id.tv_victim_name);
        tvVictimSex = (TextView) findViewById(R.id.tv_victim_sex);
        tvVictimAge = (TextView) findViewById(R.id.tv_victim_age);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvRemark = (TextView) findViewById(R.id.tv_remark);
    }

    private void initData() {
        tvTitle.setText(R.string.victim_detail_title);

    }

    /**
     * 添加监听器
     */
    private void addListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void requestDetail(String victimId) {
        VictimLoader victimLoader = new VictimLoader();
        victimLoader.getCoachedDetail(victimId).subscribe(new Subscriber<VictimEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(VictimEntity entity) {
                if (entity != null) {
                    tvVictimName.setText(entity.getVictimName());
                    if ("F".equals(entity.getVictimAge())) {
                        tvVictimSex.setText(R.string.female);
                    } else {
                        tvVictimSex.setText(R.string.male);
                    }
                    tvVictimAge.setText(entity.getVictimAge() + "岁");
                    tvDate.setText(entity.getVictimDate());
                    tvAddress.setText(entity.getAddressDetail());
                    tvRemark.setText(entity.getAdded());
                }
            }
        });
    }
}
