package com.boc.hopeheatapp.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.MsgListAdapter;
import com.boc.hopeheatapp.model.MessageEntity;
import com.boc.hopeheatapp.widget.SwipeRefreshView;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.plugin.voip.Voip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MsgFragment extends BaseFragment {

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
    @BindView(R.id.swiperefreshlayout)
    protected SwipeRefreshView mSwipeRefreshLayout;

    @BindView(R.id.listview)
    protected ListView mListView;

    private MsgListAdapter adapter;

//    private List<MessageEntity> list = new ArrayList<>();

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initView();

        initData();

        addListener();
    }

    private void initView() {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        // 手动调用,通知系统去测量
//        mSwipeRefreshLayout.measure(0, 0);
//        mSwipeRefreshLayout.setRefreshing(true);


        adapter = new MsgListAdapter(getContext());
        mListView.setAdapter(adapter);


        btnBack.setVisibility(View.INVISIBLE);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("聊天消息");
    }

    private void initData() {
        requestData();
    }

    private void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
    }

    private void requestData() {
        mSwipeRefreshLayout.setRefreshing(true);

        List<MessageEntity> list = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMsgFrom("李某某");
        messageEntity.setMsg("你好，在吗");
        messageEntity.setTime("15:30");

        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setMsgFrom("李某某");
        messageEntity2.setMsg("你好，在吗");
        messageEntity2.setTime("15:30");

        MessageEntity messageEntity3 = new MessageEntity();
        messageEntity3.setMsgFrom("李某某");
        messageEntity3.setMsg("你好，在吗");
        messageEntity3.setTime("15:30");

        list.add(messageEntity);
        list.add(messageEntity2);
        list.add(messageEntity3);

        adapter.setItems(list);

        mSwipeRefreshLayout.setRefreshing(false);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickedItem((MessageEntity) adapter.getItem(position));
            }
        });
    }

    private void onClickedItem(MessageEntity messageEntity) {
        Voip.startCallAction(
                getActivity(),
                ECVoIPCallManager.CallType.VIDEO,
                "姓名- Ding",
                "18919996045",
                "手机号",
                false
        );
    }
}
