package com.boc.hopeheatapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.hopeheatapp.model.ChatMessageBean;
import com.boc.hopeheatapp.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mao Jiqing on 2016/9/29.
 */
public class ChatRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int SENDING = 0;
    public static final int COMPLETED = 1;
    public static final int SENDERROR = 2;


    private Context context;
    private List<ChatMessageBean> userList = new ArrayList<ChatMessageBean>();
    private ArrayList<String> imageList = new ArrayList<String>();
    private HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();
    public static final int FROM_USER_MSG = 0;//接收消息类型
    public static final int TO_USER_MSG = 1;//发送消息类型

    private int mMaxItemWith;
    private int mMinItemWith;
    private SendErrorListener sendErrorListener;
    private LayoutInflater mLayoutInflater;
    private boolean isGif = true;

    public interface SendErrorListener {
        public void onClick(int position);
    }

    public void setSendErrorListener(SendErrorListener sendErrorListener) {
        this.sendErrorListener = sendErrorListener;
    }



    public ChatRecyclerAdapter(Context context, List<ChatMessageBean> userList) {
        this.context = context;
        this.userList = userList;
        mLayoutInflater = LayoutInflater.from(context);
        // 获取系统宽度
        WindowManager wManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.5f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
    }


    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case FROM_USER_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msgfrom_list_item, parent, false);
                holder = new FromUserMsgViewHolder(view);
                break;

            case TO_USER_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msgto_list_item, parent, false);
                holder = new ToUserMsgViewHolder(view);
                break;
        }
        return holder;
    }

    class FromUserMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private TextView content;

        public FromUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            content = (TextView) view.findViewById(R.id.tv_content);
        }
    }



    class ToUserMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private TextView content;

        public ToUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view.findViewById(R.id.mychat_time);
            content = (TextView) view.findViewById(R.id.tv_content);
        }
    }


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageBean tbub = userList.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case FROM_USER_MSG:
                fromMsgUserLayout((FromUserMsgViewHolder) holder, tbub, position);
                break;
            case TO_USER_MSG:
                toMsgUserLayout((ToUserMsgViewHolder) holder, tbub, position);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        return userList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void fromMsgUserLayout(final FromUserMsgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.drawable.icon_user_def);

        holder.content.setVisibility(View.VISIBLE);
        holder.content.setText(tbub.getUserContent());
    }


    private void toMsgUserLayout(final ToUserMsgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.drawable.icon_user_def);
        holder.headicon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_user_def));


        holder.content.setVisibility(View.VISIBLE);
        holder.content.setText(tbub.getUserContent());
    }


}
