package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ConsultDetailEntity;
import com.boc.hopeheatapp.service.biz.ConsultLoader;

import rx.Subscriber;

/**
 * 频道列表界面
 *
 * @author dwl
 * @date 2018/5/28.
 */
public class ConsultActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private TextView tvVictimId;
    private TextView tvVictimLevel;
    private TextView tvJoinConsult;
    private TextView tvDoctorName;
    private TextView tvDoctorLevel;
    private TextView tvVolunteerPlatform;
    private TextView tvJoinDate;
    private TextView tvJoinTime;
    private TextView tvConsultSatisfaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consult);

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
        tvTitle.setText(R.string.consult_key);
        btnTitleRight.setVisibility(View.VISIBLE);
        btnTitleRight.setText(R.string.finish);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        tvVictimId = (TextView) findViewById(R.id.tv_victim_id);
        tvVictimLevel = (TextView) findViewById(R.id.tv_victim_level);
        tvJoinConsult = (TextView) findViewById(R.id.tv_join_consult);
        tvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        tvDoctorLevel = (TextView) findViewById(R.id.tv_doctor_level);
        tvVolunteerPlatform = (TextView) findViewById(R.id.tv_volunteer_platform);
        tvJoinDate = (TextView) findViewById(R.id.tv_join_date);
        tvJoinTime = (TextView) findViewById(R.id.tv_join_time);
        tvConsultSatisfaction = (TextView) findViewById(R.id.tv_consult_satisfaction);

    }

    private void initData() {
        tvTitle.setText(R.string.consult_key);

        btnTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        ConsultLoader consultLoader = new ConsultLoader();
        consultLoader.queryConsultDetail(victimId, victimTestId).subscribe(new Subscriber<ConsultDetailEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(ConsultDetailEntity entity) {
                //handleLoginSuccess(username, pwd, userEntity);
                if (entity != null) {
                    tvVictimId.setText(entity.getVictimtestId());
                    tvVictimLevel.setText(entity.getTestsLevel());
                    if ("Y".equals(entity.getCounseling())) {
                        tvJoinConsult.setText(R.string.agree);
                    } else {
                        tvJoinConsult.setText(R.string.disagree);
                    }
                    tvDoctorName.setText(entity.getVolunteerName());
                    //tvDoctorLevel.setText();
                    tvVolunteerPlatform.setText(entity.getPlatform());
                    tvJoinDate.setText(entity.getTestioinDate());
                    //tvJoinTime.setText(); ;
                    tvConsultSatisfaction.setText(entity.getSatisficing());

                }
            }
        });
    }
}
