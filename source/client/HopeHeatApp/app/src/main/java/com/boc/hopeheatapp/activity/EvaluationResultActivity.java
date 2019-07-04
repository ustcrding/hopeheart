package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.call.CallHelper;
import com.boc.hopeheatapp.fragement.BaseFragment;
import com.boc.hopeheatapp.fragement.HomeFragment;
import com.boc.hopeheatapp.fragement.MyFragment;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.widget.bottombar.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dwl
 * @date 2018/2/8.
 */
public class EvaluationResultActivity extends TitleColorActivity {
    private static final String TAG = EvaluationResultActivity.class.getSimpleName();

    private TextView tvEvaluationResult;
    private TextView tvEvaluationAdvise;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        initView();
        addListener();

        String message = getIntent().getStringExtra(ActivityJumper.EXTRA_EVALUATION_MSG);
        tvEvaluationResult.setText(message);

        int score = getIntent().getIntExtra(ActivityJumper.EXTRA_EVALUATION_SCORE, 0);
        if (score < 53) {
            tvEvaluationAdvise.setText(R.string.evaluation_advise1);
        } else if (score < 63) {
            tvEvaluationAdvise.setText(R.string.evaluation_advise2);
        } else if (score < 73) {
            tvEvaluationAdvise.setText(R.string.evaluation_advise3);
        } else {
            tvEvaluationAdvise.setText(R.string.evaluation_advise4);
        }
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.evaluation_result);

        btnTitleRight.setVisibility(View.INVISIBLE);

        tvEvaluationResult = (TextView) findViewById(R.id.evaluation_result);
        tvEvaluationAdvise = (TextView) findViewById(R.id.evaluation_advise);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

    }

    private void addListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.psychology_consult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startConsultActivity(EvaluationResultActivity.this);
            }
        });

        findViewById(R.id.psychology_evaluation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityJumper.startKnowledgeActivity(EvaluationResultActivity.this, KnowledgeActivity.TYPE_PSYCHOLOGY);
                try {
                    CallHelper.videoCall(getApplicationContext(), "18963792291", "患者");
                } catch (Throwable t) {

                }
            }
        });
    }

}
