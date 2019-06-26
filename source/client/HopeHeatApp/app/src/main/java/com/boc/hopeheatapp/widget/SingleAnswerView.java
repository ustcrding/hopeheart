package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.EvaluateInterface;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.model.MsgEntity;

import java.util.List;

/**
 * 单条答案的view
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class SingleAnswerView extends AnswerView {

    private static final String TAG = "SingleAnswerView";

    /**
     * 左边的头像
     */
    protected ImageView ivLeft;
    /**
     * 右边的头像
     */
    protected TextView tvLeft;
    protected TextView btnLike;
    protected TextView btnUnLike;
    protected TextView tvLikeStatus;
    protected LinearLayout answerContainer;
    protected LinearLayout statisfactionContainer;
    protected boolean needShowLikeContainer;

    public SingleAnswerView(Context context) {
        super(context);

        needShowLikeContainer = true;
    }

    @Override
    protected void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_single_answer, this, true);

        ivLeft = (ImageView) view.findViewById(R.id.iv_left);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        btnLike = (TextView) view.findViewById(R.id.btn_like);
        btnUnLike = (TextView) view.findViewById(R.id.btn_unlike);
        tvLikeStatus = (TextView) view.findViewById(R.id.tv_like_status);


        answerContainer = (LinearLayout) view.findViewById(R.id.ll_answer_container);
        statisfactionContainer = (LinearLayout) view.findViewById(R.id.ll_satisfaction_container);

        updateView();
    }

    protected void updateView() {
        tvLeft.setVisibility(View.VISIBLE);
        statisfactionContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void updateUi() {
        BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) msgEntity.getContent();
        AnswerEntity answer = response.getData();
        List<AnswerItem> list = answer.getList();

        if (list != null && list.size() == 1) {
            AnswerItem item = list.get(0);
            tvLeft.setText(item.getAnswer());

            //处理点赞点踩,需要在加一个分支处理异常
            if (msgEntity.getLiked() == MsgEntity.UNKNOWN) {
                btnLike.setVisibility(View.VISIBLE);
                btnUnLike.setVisibility(View.VISIBLE);
                tvLikeStatus.setVisibility(View.GONE);

                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickedLike();
                    }
                });

                btnUnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickedUnLike();
                    }
                });
            } else {
                btnLike.setVisibility(View.GONE);
                btnUnLike.setVisibility(View.GONE);
                tvLikeStatus.setVisibility(View.VISIBLE);
                if (msgEntity.getLiked() == MsgEntity.LIKE) {
                    tvLikeStatus.setText(R.string.like);
                } else {
                    tvLikeStatus.setText(R.string.unlike);
                }

                btnLike.setOnClickListener(null);
                btnUnLike.setOnClickListener(null);
            }

            if (needShowLikeContainer) {
                statisfactionContainer.setVisibility(View.VISIBLE);
            } else {
                statisfactionContainer.setVisibility(View.GONE);
            }
        } else if (list == null || list.size() == 0) {
            tvLeft.setText(R.string.tip_no_result);
        }
    }

    /**
     * 用户点赞
     */
    protected void onClickedLike() {
        msgEntity.setLiked(MsgEntity.LIKE);
        btnLike.setVisibility(View.GONE);
        btnUnLike.setVisibility(View.GONE);
        tvLikeStatus.setVisibility(View.VISIBLE);
        tvLikeStatus.setText(R.string.like);

        if (evaluateInterface != null) {
            BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) msgEntity.getContent();
            evaluateInterface.evaluate(response.getData(), EvaluateInterface.FLAG_LAUD);
        }
    }

    /**
     * 用户点踩
     */
    protected void onClickedUnLike() {
        msgEntity.setLiked(MsgEntity.UNLIKE);
        btnLike.setVisibility(View.GONE);
        btnUnLike.setVisibility(View.GONE);
        tvLikeStatus.setVisibility(View.VISIBLE);
        tvLikeStatus.setText(R.string.unlike);

        if (evaluateInterface != null) {
            BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) msgEntity.getContent();
            evaluateInterface.evaluate(response.getData(), EvaluateInterface.FLAG_TREAD);
        }
    }
}
