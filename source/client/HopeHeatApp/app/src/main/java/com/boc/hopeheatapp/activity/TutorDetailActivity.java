package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.TutorDetailEntity;
import com.boc.hopeheatapp.service.biz.TutorLoader;
import com.boc.hopeheatapp.util.string.StringUtil;

import butterknife.BindView;
import rx.Subscriber;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class TutorDetailActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    @BindView(R.id.tv_victim_id)
    protected TextView tvVictimId;

    @BindView(R.id.tv_victim_level)
    protected TextView tvVictimLevel;

    @BindView(R.id.tv_victim_name)
    protected TextView tvVictimName;

    @BindView(R.id.tv_victim_sex)
    protected TextView tvVictimSex;

    @BindView(R.id.tv_credentials_type)
    protected TextView tvCredentialsType;

    @BindView(R.id.tv_user_phone)
    protected TextView tvVictimPhone;

    @BindView(R.id.tv_user_city)
    protected TextView tvVictimCity;

    @BindView(R.id.tv_user_addr)
    protected TextView tvVictimAddr;

    @BindView(R.id.tv_tutor_date)
    protected TextView tvTutorDate;

    @BindView(R.id.tv_tutor_time)
    protected TextView tvTutorTime;

    @BindView(R.id.tv_tutor_locate_area)
    protected TextView tvTutorLocateArea;

    @BindView(R.id.tv_tutor_satisfaction)
    protected TextView tvTutorSatisfaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutor_detail);

        initView();

        initData();

        addListener();

        String victimId = getIntent().getStringExtra(ActivityJumper.EXTRA_VICTIM_ID);
        String victimTestId = getIntent().getStringExtra(ActivityJumper.EXTRA_VICTIM_TEST_ID);
        requestHistory(victimId, victimTestId);
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.tutor_detail_title);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

    }

    private void initData() {
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

    private void requestHistory(String victimId, String victimTestId) {
        TutorLoader consultLoader = new TutorLoader();
        consultLoader.queryTutorDetail(victimId, victimTestId).subscribe(new Subscriber<TutorDetailEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(TutorDetailEntity entity) {
                if (entity != null) {
                    tvVictimId.setText(entity.getVictimtestId());
                    if (StringUtil.equals(entity.getTestsLevel(), "H")) {
                        tvVictimLevel.setText("心理健康");
                    } else if (StringUtil.equals(entity.getTestsLevel(), "U")) {
                        tvVictimLevel.setText("不良状态");
                    } else if (StringUtil.equals(entity.getTestsLevel(), "B")) {
                        tvVictimLevel.setText("心理障碍");
                    } else if (StringUtil.equals(entity.getTestsLevel(), "I")) {
                        tvVictimLevel.setText("心理疾病");
                    }

                    tvVictimName.setText(entity.getVictimName());
                    tvVictimSex.setText(entity.getVictimGender());
                    tvCredentialsType.setText(entity.getVictimCerType());
                    tvVictimPhone.setText(entity.getVictimPhone());
                    tvVictimAddr.setText(entity.getAddressDetail());
                    tvVictimCity.setText(entity.getAddressCode());
                    tvTutorDate.setText(entity.getTestJoinDate());
                    tvTutorTime.setText(entity.getTestJoinTime());

                    tvTutorLocateArea.setText(entity.getVictimAddressCode());
                    if (StringUtil.equals(entity.getSatisficing(), "W")) {
                        tvVictimLevel.setText("非常满意");
                    } else if (StringUtil.equals(entity.getTestsLevel(), "G")) {
                        tvVictimLevel.setText("基本满意");
                    } else if (StringUtil.equals(entity.getTestsLevel(), "Y")) {
                        tvVictimLevel.setText("不满意");
                    }

                }
            }
        });
    }
}

