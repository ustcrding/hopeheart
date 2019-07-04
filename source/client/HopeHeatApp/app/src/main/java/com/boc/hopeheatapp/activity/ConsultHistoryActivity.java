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
import com.boc.hopeheatapp.adapter.ConsultHistoryAdapter;
import com.boc.hopeheatapp.model.CatalogListEntity;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.model.ConsultHistoryEntity;
import com.boc.hopeheatapp.service.biz.CatalogLoader;
import com.boc.hopeheatapp.service.biz.ConsultLoader;
import com.boc.hopeheatapp.user.UserManager;
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

    private ListView lvConsultHistory;

    private ConsultHistoryAdapter historyAdapter;

    private final int DEFAULT_SPAN_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consult_history);

        initView();

        initData();
        addListener();

        requestHistory();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.consult_history_title);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        lvConsultHistory = (ListView) findViewById(R.id.lv_consult_history);
        lvConsultHistory.setDividerHeight(0);

        historyAdapter = new ConsultHistoryAdapter(getApplicationContext());
        lvConsultHistory.setAdapter(historyAdapter);

    }

    private void initData() {
        tvTitle.setText(R.string.consult_history_title);

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

        lvConsultHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ConsultHistoryEntity.Result> datas = historyAdapter.getDatas();
                if (datas.size() > position) {
                    ActivityJumper.startConsultDetailActivity(ConsultHistoryActivity.this, UserManager.getInstance().getUser().getRoleId(), datas.get(position).getVictimtestId());
                }
            }
        });
    }

    private void requestHistory() {
        ConsultLoader consultLoader = new ConsultLoader();
        consultLoader.queryConsultHistory(UserManager.getInstance().getUser().getRoleId(), "1").subscribe(new Subscriber<ConsultHistoryEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(ConsultHistoryEntity catalogListEntity) {
                //handleLoginSuccess(username, pwd, userEntity);
                if (catalogListEntity != null) {
                    List<ConsultHistoryEntity.Result> results = catalogListEntity.getResults();
                    if (results != null ) {
                        historyAdapter.setData(results);
                        historyAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }
}
