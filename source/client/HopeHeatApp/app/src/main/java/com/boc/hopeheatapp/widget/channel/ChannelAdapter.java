package com.boc.hopeheatapp.widget.channel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道适配器
 *
 * @author dwl
 * @date 2018/5/19.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<ChannelEntity> mDataList;

    private GridItemClickListener gridItemClickListener;

    public ChannelAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setDataList(List<ChannelEntity> list) {
        mDataList.clear();

        if (list != null) {
            mDataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setGridItemClickListener(GridItemClickListener listener) {
        this.gridItemClickListener = listener;
    }

    @Override
    public ChannelAdapter.ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.view_channel_button, parent, false));
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.ChannelViewHolder holder, int position) {
        //holder.mTextView.setText(mDataList.get(position));
        ChannelEntity channelEntity = mDataList.get(position);
        String channelName = channelEntity.getName();

        if (StringUtil.equals(channelEntity.getType(), ChannelEntity.TYPE_EMPTY)) {
            holder.ivChannelIcon.setVisibility(View.GONE);
            holder.tvChannelName.setVisibility(View.GONE);
        } else {
            holder.ivChannelIcon.setVisibility(View.VISIBLE);
            holder.tvChannelName.setVisibility(View.VISIBLE);

            int icon = R.drawable.icon_logo;
            if (StringUtil.contains(channelName, "网标")) {
                icon = R.drawable.icon_wb_kb;
            } else if (StringUtil.contains(channelName, "客服")) {
                icon = R.drawable.icon_zx_kb;
            } else if (StringUtil.contains(channelName, "用户")) {
                icon = R.drawable.icon_user_kb;
            } else if (StringUtil.contains(channelName, "更多")) {
                icon = R.drawable.icon_kb_more;
            }
            holder.ivChannelIcon.setImageResource(icon);
            holder.tvChannelName.setText(channelEntity.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * 频道的icon
         */
        private ImageView ivChannelIcon;

        /**
         * 频道的名称
         */
        private TextView tvChannelName;

        public ChannelViewHolder(View itemView) {
            super(itemView);

            ivChannelIcon = (ImageView) itemView.findViewById(R.id.iv_channel_icon);

            tvChannelName = (TextView) itemView.findViewById(R.id.tv_channel_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onClickItem(v, mDataList.get(position));
        }
    }

    private void onClickItem(View v, ChannelEntity channelEntity) {
        if (StringUtil.equals(channelEntity.getType(), ChannelEntity.TYPE_EMPTY)) {
            return;
        }

        if (gridItemClickListener != null) {
            gridItemClickListener.onGridItemClick(v, channelEntity);
        }
    }

}
