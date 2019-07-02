package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.CatalogListEntity;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.service.biz.CatalogLoader;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.channel.ChannelAdapter;
import com.boc.hopeheatapp.widget.channel.GridDivider;
import com.boc.hopeheatapp.widget.channel.GridItemClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 频道列表界面
 *
 * @author dwl
 * @date 2018/5/28.
 */
public class ConsultHistoryActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private ChannelEntity channelEntity;

    private ListView lvConsultHistory;
    private LinearLayoutManager mManagerDrawable;
    private GridLayoutManager mManagerColor;

    private ChannelAdapter channelAdapter;

    private final int DEFAULT_SPAN_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consult_history);

        initView();

        channelEntity = getIntent().getParcelableExtra(ActivityJumper.EXTRA_CHANNEL_ENTITY);

        initData();
        addListener();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.consult_history_title);
        btnBack.setVisibility(View.VISIBLE);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        lvConsultHistory = (ListView) findViewById(R.id.lv_consult_history);

        //设置颜色分割线
        mManagerColor = new GridLayoutManager(this, DEFAULT_SPAN_COUNT);

        channelAdapter = new ChannelAdapter(this);

        lvConsultHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initData() {
        tvTitle.setText(R.string.title_channel_list);

        initChannel();
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

    private void initChannel() {
        channelAdapter.setGridItemClickListener(new GridItemClickListener() {
            @Override
            public void onGridItemClick(View view, ChannelEntity channelEntity) {
                if (StringUtil.equals(channelEntity.getType(), ChannelEntity.TYPE_OPEN_URL)) {
                    ActivityJumper.startBrowserActivity(ConsultHistoryActivity.this, channelEntity.getOpenUrl());
                } else {
                    ActivityJumper.startChannelActivity(ConsultHistoryActivity.this, channelEntity);
                }

            }
        });

        requestChannel();
    }

    private void requestChannel() {
        CatalogLoader catelogLoader = new CatalogLoader();
        catelogLoader.get().subscribe(new Subscriber<CatalogListEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(CatalogListEntity catalogListEntity) {
                //handleLoginSuccess(username, pwd, userEntity);
                if (catalogListEntity != null) {
                    List<ChannelEntity> list = new ArrayList<>(catalogListEntity.getList());
                    int mod = DEFAULT_SPAN_COUNT - list.size() % DEFAULT_SPAN_COUNT;
                    if (mod != 0) {
                        for (int i = 0; i < mod; ++i) {
                            ChannelEntity channelEntity = new ChannelEntity();
                            channelEntity.setType(ChannelEntity.TYPE_EMPTY);
                            list.add(channelEntity);
                        }
                    }
                    channelAdapter.setDataList(list);
                }
            }
        });
    }
}
