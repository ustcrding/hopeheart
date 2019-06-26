package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.widget.channel.ChannelConversationView;

/**
 * 频道页面
 *
 * @author dwl
 * @date 2018/5/22.
 */
public class ChannelActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;
    private ChannelEntity channelEntity;

    /**
     * 频道会话页面
     */
    private ChannelConversationView channelConversationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_channel);

        initView();


        channelEntity = getIntent().getParcelableExtra(ActivityJumper.EXTRA_CHANNEL_ENTITY);
        if (channelEntity == null) {
            return;
        }

        initData();
        addListener();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_boc);
        btnBack.setVisibility(View.VISIBLE);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        channelConversationView = (ChannelConversationView) findViewById(R.id.cv_channel_conversation_view);
    }

    private void initData() {
        tvTitle.setText(channelEntity.getName());

        channelConversationView.setChannelEntity(channelEntity);
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
}
