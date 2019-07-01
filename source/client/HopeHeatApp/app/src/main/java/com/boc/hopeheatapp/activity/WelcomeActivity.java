package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;

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
    }


}
