package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.util.Environment;

/**
 * 关于页面
 *
 * @author dwl
 * @date 2018/3/18.
 */
public class AboutActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        initView();
        addListener();

        showVersion();
    }

    private void initView() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvVersion = (TextView) findViewById(R.id.tv_version);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_about_me);
        btnBack.setVisibility(View.VISIBLE);
    }

    private void addListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedBack();
            }
        });
    }

    public void onClickedBack() {
        onBackPressed();
    }

    private void showVersion() {
        String version = Environment.getMyVersionName(getApplicationContext());
        tvVersion.setText("版本号:" + version);
    }
}
