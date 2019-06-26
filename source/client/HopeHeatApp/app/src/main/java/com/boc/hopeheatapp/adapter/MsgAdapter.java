package com.boc.hopeheatapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.phone.DensityUtils;

import java.util.List;

/**
 * @author dwl
 * @date 2018/1/19.
 */
public class MsgAdapter extends KBaseAdapter<MsgEntity> {

    private final String TAG = "MsgAdapter";

    private final int TYPE_SEND = 0;
    private final int TYPE_REPLY = 1;

    private EvaluateInterface evaluateInterface;

    private LinkInterface linkInterface;

    // 设置是否显示赞踩按钮
    private boolean needShowLikeContainer;

    public MsgAdapter(Context context) {
        super(context);
        needShowLikeContainer = true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(position);
        if (type == TYPE_SEND) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.adapter_msg_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MsgEntity data = itemList.get(position);
            setAskViewHolder(data, holder);

            return convertView;
        } else {
            final ReplyViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.adapter_reply_item, null);
                holder = new ReplyViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ReplyViewHolder) convertView.getTag();
            }

            final MsgEntity data = itemList.get(position);
            // todo 多条记录未写完，在此做区分
            //if (true) {


            setReplyViewHolder(data, holder);
            /*} else {
                holder.tvLeft.setVisibility(View.GONE);
                holder.statisfactionContainer.setVisibility(View.GONE);
                holder.multiAnswerContainer.setVisibility(View.VISIBLE);
                holder.tvMultiAnswerTip.setVisibility(View.VISIBLE);

                holder.multiAnswerContainer.removeAllViews();
                TextView textView1 = genTextView(data, "信用卡如何还款");
                TextView textView2 = genTextView(data, "信用卡取现收费吗");
                TextView textView3 = genTextView(data, "借记卡如何办理");
                holder.multiAnswerContainer.addView(textView1);
                holder.multiAnswerContainer.addView(textView2);
                holder.multiAnswerContainer.addView(textView3);
            }*/
            return convertView;
        }
    }

    private TextView genTextView(final AnswerEntity data, final int index) {
        final AnswerItem item = data.getList().get(index);
        TextView textView = new TextView(context);
        textView.setText(item.getQuestion());
        textView.setTextColor(context.getResources().getColorStateList(R.color.color_multi_answer_item));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        int left = DensityUtils.dip2Px(context, 5);
        int top = DensityUtils.dip2Px(context, 3);
        textView.setPadding(left, top, 0, top);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getQuestion(), Toast.LENGTH_SHORT).show();

                if (linkInterface != null) {
                    linkInterface.answerLink(data, index);
                }
            }
        });
        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        MsgEntity data = itemList.get(position);
        if (MsgEntity.TYPE_SEND == data.getMsgType()) {
            return TYPE_SEND;
        } else if (MsgEntity.TYPE_RECEIVER == data.getMsgType()) {
            return TYPE_REPLY;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public static class ViewHolder {
        ImageView ivLeft;
        ImageView ivRight;
        TextView tvLeft;
        TextView tvRight;

        public ViewHolder(View view) {
            ivLeft = (ImageView) view.findViewById(R.id.iv_left);
            ivRight = (ImageView) view.findViewById(R.id.iv_right);
            tvLeft = (TextView) view.findViewById(R.id.tv_left);
            tvRight = (TextView) view.findViewById(R.id.tv_right);
        }
    }

    public static class ReplyViewHolder {
        ImageView ivLeft;
        TextView tvLeft;
        TextView btnLike;
        TextView btnUnLike;
        TextView tvLikeStatus;
        LinearLayout answerContainer;
        LinearLayout statisfactionContainer;
        LinearLayout multiAnswerContainer;
        TextView tvMultiAnswerTip;


        public ReplyViewHolder(View view) {
            ivLeft = (ImageView) view.findViewById(R.id.iv_left);
            tvLeft = (TextView) view.findViewById(R.id.tv_left);
            btnLike = (TextView) view.findViewById(R.id.btn_like);
            btnUnLike = (TextView) view.findViewById(R.id.btn_unlike);
            tvLikeStatus = (TextView) view.findViewById(R.id.tv_like_status);
            tvMultiAnswerTip = (TextView) view.findViewById(R.id.tv_multi_answer_tip);

            answerContainer = (LinearLayout) view.findViewById(R.id.ll_answer_container);
            statisfactionContainer = (LinearLayout) view.findViewById(R.id.ll_satisfaction_container);
            multiAnswerContainer = (LinearLayout) view.findViewById(R.id.ll_multi_answer_container);
        }
    }

    /**
     * 用户点赞
     *
     * @param data
     * @param holder
     */
    private void onClickedLike(MsgEntity data, ReplyViewHolder holder) {
        data.setLiked(MsgEntity.LIKE);
        holder.btnLike.setVisibility(View.GONE);
        holder.btnUnLike.setVisibility(View.GONE);
        holder.tvLikeStatus.setVisibility(View.VISIBLE);
        holder.tvLikeStatus.setText("喜欢");

        if (evaluateInterface != null) {
            BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) data.getContent();
            evaluateInterface.evaluate(response.getData(), EvaluateInterface.FLAG_LAUD);
        }
    }

    /**
     * 用户点踩
     *
     * @param data
     * @param holder
     */
    private void onClickedUnLike(MsgEntity data, ReplyViewHolder holder) {
        data.setLiked(MsgEntity.UNLIKE);
        holder.btnLike.setVisibility(View.GONE);
        holder.btnUnLike.setVisibility(View.GONE);
        holder.tvLikeStatus.setVisibility(View.VISIBLE);
        holder.tvLikeStatus.setText("不喜欢");

        if (evaluateInterface != null) {
            BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) data.getContent();
            evaluateInterface.evaluate(response.getData(), EvaluateInterface.FLAG_TREAD);
        }
    }

    /**
     * 显示提问内容
     *
     * @param data
     * @param holder
     */
    private void setAskViewHolder(MsgEntity data, ViewHolder holder) {
        holder.ivLeft.setVisibility(View.GONE);
        holder.tvLeft.setVisibility(View.GONE);
        holder.ivRight.setVisibility(View.VISIBLE);
        holder.tvRight.setVisibility(View.VISIBLE);
        //holder.tvRight.setText(data.getContent());
        holder.tvRight.setText(data.getContent().toString());
    }

    /**
     * 显示回复内容
     *
     * @param data
     * @param holder
     */
    private void setReplyViewHolder(final MsgEntity data, final ReplyViewHolder holder) {
        holder.tvLeft.setVisibility(View.VISIBLE);
        holder.statisfactionContainer.setVisibility(View.VISIBLE);
        holder.multiAnswerContainer.setVisibility(View.GONE);
        holder.tvMultiAnswerTip.setVisibility(View.GONE);

        BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) data.getContent();
        if (response.getCode() == BaseResponse.OK) {
            AnswerEntity answer = response.getData();
            List<AnswerItem> list = answer.getList();
            int size = list.size();
            AnswerItem item = null;
            if (size == 1) {
                item = list.get(0);
                holder.tvLeft.setText(item.getAnswer());

                //处理点赞点踩,需要在加一个分支处理异常
                if (data.getLiked() == MsgEntity.UNKNOWN) {
                    holder.btnLike.setVisibility(View.VISIBLE);
                    holder.btnUnLike.setVisibility(View.VISIBLE);
                    holder.tvLikeStatus.setVisibility(View.GONE);

                    holder.btnLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickedLike(data, holder);
                        }
                    });

                    holder.btnUnLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickedUnLike(data, holder);
                        }
                    });
                } else {
                    holder.btnLike.setVisibility(View.GONE);
                    holder.btnUnLike.setVisibility(View.GONE);
                    holder.tvLikeStatus.setVisibility(View.VISIBLE);
                    if (data.getLiked() == MsgEntity.LIKE) {
                        holder.tvLikeStatus.setText("喜欢");
                    } else {
                        holder.tvLikeStatus.setText("不喜欢");
                    }

                    holder.btnLike.setOnClickListener(null);
                    holder.btnUnLike.setOnClickListener(null);
                }

                if (needShowLikeContainer) {
                    holder.statisfactionContainer.setVisibility(View.VISIBLE);
                } else {
                    holder.statisfactionContainer.setVisibility(View.GONE);
                }
            } else if (size > 1) {
                //addAnswer("找到多条答案：" + size);
                holder.tvLeft.setVisibility(View.GONE);
                holder.statisfactionContainer.setVisibility(View.GONE);
                holder.multiAnswerContainer.setVisibility(View.VISIBLE);
                holder.tvMultiAnswerTip.setVisibility(View.VISIBLE);

                holder.multiAnswerContainer.removeAllViews();
                for (int i = 0; i < size; i++) {
                    TextView textView1 = genTextView(answer, i);
                    holder.multiAnswerContainer.addView(textView1);
                }
                //TextView textView1 = genTextView(data, "信用卡如何还款");
                //TextView textView2 = genTextView(data, "信用卡取现收费吗");
                //TextView textView3 = genTextView(data, "借记卡如何办理");
                //holder.multiAnswerContainer.addView(textView1);
                //holder.multiAnswerContainer.addView(textView2);
                //holder.multiAnswerContainer.addView(textView3);
            } else {
                holder.tvLeft.setText("抱歉，没有找到答案");
                holder.statisfactionContainer.setVisibility(View.GONE);
            }
        } else {
            holder.tvLeft.setText("错误信息：" + response.getMessage());
            if (Logger.isDebugable()) {
                Logger.e(TAG, "requestQuestion.onNext | code =  " + response.getCode() + ", message = " + response.getMessage());
            }
        }

    }

    public EvaluateInterface getEvaluateInterface() {
        return evaluateInterface;
    }

    public void setEvaluateInterface(EvaluateInterface evaluateInterface) {
        this.evaluateInterface = evaluateInterface;
    }

    public LinkInterface getLinkInterface() {
        return linkInterface;
    }

    public void setLinkInterface(LinkInterface linkInterface) {
        this.linkInterface = linkInterface;
    }

    public void setShowLikeContainer(boolean showLikeContainer) {
        needShowLikeContainer = showLikeContainer;
    }

}
