package com.boc.hopeheatapp.widget.channel;

import android.view.View;

import com.boc.hopeheatapp.model.ChannelEntity;

/**
 * 网格被点击回调
 *
 * @author dwl
 * @date 2018/5/19.
 */
public interface GridItemClickListener {

    /**
     * 每一条点击事件
     *
     * @param view
     * @param channelEntity
     */
    void onGridItemClick(View view, ChannelEntity channelEntity);
}
