package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.TutorHistoryAdapter;
import com.boc.hopeheatapp.model.TutorHistoryEntity;
import com.boc.hopeheatapp.service.biz.TutorLoader;
import com.boc.hopeheatapp.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 辅导历史
 *
 * @author dwl
 * @date 2019/7/4.
 */
public class TutorHistoryActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private ListView lvConsultHistory;

    private TutorHistoryAdapter historyAdapter;

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
        tvTitle.setText(R.string.tutor_history);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        lvConsultHistory = (ListView) findViewById(R.id.lv_consult_history);
        lvConsultHistory.setDividerHeight(0);

        historyAdapter = new TutorHistoryAdapter(getApplicationContext());
        lvConsultHistory.setAdapter(historyAdapter);

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

        lvConsultHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<TutorHistoryEntity.Result> datas = historyAdapter.getDatas();
                if (datas.size() > position) {
                    ActivityJumper.startTutorDetailActivity(TutorHistoryActivity.this, datas.get(position).getVictimId(), datas.get(position).getVictimTestId());
                }
            }
        });
    }

    private void requestHistory() {
        TutorLoader consultLoader = new TutorLoader();
        consultLoader.queryTutorHistory("" + UserManager.getInstance().getUser().getUserId(), "1").subscribe(new Subscriber<TutorHistoryEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(TutorHistoryEntity catalogListEntity) {
                //handleLoginSuccess(username, pwd, userEntity);
                if (catalogListEntity != null) {
                    List<TutorHistoryEntity.Result> results = catalogListEntity.getResults();
                    if (results != null) {
                        historyAdapter.setData(results);
                        historyAdapter.notifyDataSetChanged();
                    }
                } else {
                    List<TutorHistoryEntity.Result> results = new ArrayList<>();
                    TutorHistoryEntity.Result result = new TutorHistoryEntity.Result();
                    result.setAddressCode("合肥");
                    result.setVictimId("test002");
                    result.setTestioinDate("20190703");
                    result.setVictimId("9787115435040002");
                    results.add(result);
                    historyAdapter.setData(results);
                    historyAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
