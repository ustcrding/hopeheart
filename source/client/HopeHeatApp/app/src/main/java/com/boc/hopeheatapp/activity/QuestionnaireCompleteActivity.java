package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.R;

/**
 * 作答结束页面
 *
 * @author dwl
 * @date 2019/6/28.
 */
public class QuestionnaireCompleteActivity extends TitleColorActivity {

    /*
     * 文件结果页
     */
    private TextView tvResult;

    /*
     * 一键咨询
     */
    private TextView btnOneKeyConsultation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questionnaire_complete);

        initView();

        addListener();
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.tv_result);

        btnOneKeyConsultation = (TextView) findViewById(R.id.btn_one_Key_consultation);
    }

    private void addListener() {
        btnOneKeyConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedOneKeyConsultation();
            }
        });
    }

    private void onClickedOneKeyConsultation() {

    }
}
