package com.boc.hopeheatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.MessageEntity;

/**
 * @author dwl
 * @date 2019/7/3.
 */
public class MsgListAdapter extends KBaseAdapter<MessageEntity> {


    public MsgListAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.adapter_message_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MessageEntity messageEntity = itemList.get(position);
        holder.tvMsgFrom.setText(messageEntity.getMsgFrom());
        holder.tvMsgContent.setText(messageEntity.getMsg());
        holder.tvMsgTime.setText(messageEntity.getTime());

        return convertView;
    }

    public static class ViewHolder {
        ImageView ivUserICon;
        TextView tvMsgFrom;
        TextView tvMsgContent;
        TextView tvMsgTime;

        public ViewHolder(View view) {
            ivUserICon = (ImageView) view.findViewById(R.id.iv_user_icon);
            tvMsgFrom = (TextView) view.findViewById(R.id.tv_msg_from);
            tvMsgContent = (TextView) view.findViewById(R.id.tv_msg_content);
            tvMsgTime = (TextView) view.findViewById(R.id.tv_msg_time);
        }
    }
}
