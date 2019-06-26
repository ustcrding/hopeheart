package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.widget.channel.ChannelAdapter;
import com.boc.hopeheatapp.widget.channel.GridDivider;
import com.boc.hopeheatapp.widget.channel.GridItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道工具栏视图
 *
 * @author dwl
 * @date 2018/5/18.
 */
public class ChannelBarView extends LinearLayout {

    private RecyclerView recycleView;
    private LinearLayoutManager mManagerDrawable;
    private GridLayoutManager mManagerColor;

    private ChannelAdapter channelAdapter;

    private final int DEFAULT_SPAN_COUNT = 5;

    public ChannelBarView(Context context) {
        super(context);

        initView();
    }

    public ChannelBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public ChannelBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_channel_bar, this, true);

        recycleView = (RecyclerView) view.findViewById(R.id.rv_channel_bar);

        //设置颜色分割线
        mManagerColor = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        recycleView.setLayoutManager(mManagerColor);
        recycleView.addItemDecoration(new GridDivider(getContext(), 1, this.getResources().getColor(R.color.color_white)));

        channelAdapter = new ChannelAdapter(getContext());

        //设置Adapter
        recycleView.setAdapter(channelAdapter);

        //设置分隔线
        recycleView.addItemDecoration(new GridDivider(getContext()));

        //设置增加或删除条目的动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setData(List<ChannelEntity> list) {
        if (list != null && !list.isEmpty()) {
            int spanCount = list.size();
            if (spanCount > DEFAULT_SPAN_COUNT) {
                spanCount = (spanCount + 1) / 2;
            }
            mManagerColor.setSpanCount(spanCount);

            List<ChannelEntity> chanelList = new ArrayList<>(list);
            int mod = spanCount - chanelList.size() % spanCount;
            if (mod != 0) {
                for (int i = 0; i < mod; ++i) {
                    ChannelEntity channelEntity = new ChannelEntity();
                    channelEntity.setType(ChannelEntity.TYPE_EMPTY);
                    chanelList.add(channelEntity);
                }
            }

            channelAdapter.setDataList(chanelList);
        }
    }

    /**
     * 设置频道的条目点击事件
     *
     * @param listener
     */
    public void setChannelItemClickListener(GridItemClickListener listener) {
        channelAdapter.setGridItemClickListener(listener);
    }
}
