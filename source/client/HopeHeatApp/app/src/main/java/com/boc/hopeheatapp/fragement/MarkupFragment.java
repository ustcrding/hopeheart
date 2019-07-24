package com.boc.hopeheatapp.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.MarkupListAdapter;
import com.boc.hopeheatapp.adapter.MsgListAdapter;
import com.boc.hopeheatapp.model.MessageEntity;
import com.boc.hopeheatapp.model.VictimEntity;
import com.boc.hopeheatapp.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MarkupFragment extends BaseFragment {


    /**
     * title 左侧返回按钮
     */
    @Nullable
    @BindView(R.id.top_back_container)
    protected View btnBack;

    /**
     * title正文
     */
    @Nullable
    @BindView(R.id.top_title_text)
    protected TextView tvTitle;

    @Nullable
    @BindView(R.id.tv_title_right)
    protected TextView tvMarkup;


    @BindView(R.id.listview)
    protected LoadMoreListView mListView;

    private MarkupListAdapter adapter;

    public static MarkupFragment newInstance() {
        MarkupFragment fragment = new MarkupFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_markup;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initView();

        initData();

        addListener();
    }

    private void initView() {
        // 手动调用,通知系统去测量
//        mSwipeRefreshLayout.measure(0, 0);
//        mSwipeRefreshLayout.setRefreshing(true);


        mListView.setDividerHeight(0);

        adapter = new MarkupListAdapter(getContext());
        mListView.setAdapter(adapter);


        btnBack.setVisibility(View.INVISIBLE);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("在线标记");

        tvMarkup.setVisibility(View.VISIBLE);
        tvMarkup.setText("标记");
    }

    private void initData() {
        requestData();
    }

    private void addListener() {
        mListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onclickedItem((VictimEntity) adapter.getItem(position));
            }
        });
    }

    private void onclickedItem(VictimEntity item) {
        ActivityJumper.startVictimDetailActivity(getContext(), item.getVictimId());
    }

    private void requestData() {
        List<VictimEntity> list = new ArrayList<>();
        VictimEntity messageEntity = new VictimEntity();
        messageEntity.setVictimName("李某某");
        messageEntity.setVictimAge(16);
        messageEntity.setVictimGender("F");

        VictimEntity messageEntity2 = new VictimEntity();
        messageEntity2.setVictimName("张某某");
        messageEntity2.setVictimAge(9);
        messageEntity2.setVictimGender("C");

        VictimEntity messageEntity3 = new VictimEntity();
        messageEntity3.setVictimName("丁某某");
        messageEntity3.setVictimAge(26);
        messageEntity3.setVictimGender("F");

        for (int i = 0 ; i < 10; ++i) {
            VictimEntity entity = new VictimEntity();
            entity.setVictimName("丁某某");
            entity.setVictimAge(25);
            entity.setVictimGender("M");

            list.add(entity);
        }

        list.add(messageEntity);
        list.add(messageEntity2);
        list.add(messageEntity3);

        adapter.setItems(list);

    }

}
