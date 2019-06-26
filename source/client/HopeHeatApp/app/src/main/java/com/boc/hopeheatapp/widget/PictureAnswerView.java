package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.EvaluateInterface;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dwl
 * @date 2018/4/13.
 */
public class PictureAnswerView extends AnswerView {

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

    private AnswerPictureAdapter answerPictureAdapter;

    /**
     * 轮播图片
     */
    RollPagerView rollViewPager;
    protected boolean needShowLikeContainer;

    private ArrayList<String> pictureList;


    public PictureAnswerView(Context context) {
        super(context);

        needShowLikeContainer = true;
        pictureList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_picture_answer, this, true);

        ivLeft = (ImageView) view.findViewById(R.id.iv_left);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        btnLike = (TextView) view.findViewById(R.id.btn_like);
        btnUnLike = (TextView) view.findViewById(R.id.btn_unlike);
        tvLikeStatus = (TextView) view.findViewById(R.id.tv_like_status);


        answerContainer = (LinearLayout) view.findViewById(R.id.ll_answer_container);
        statisfactionContainer = (LinearLayout) view.findViewById(R.id.ll_satisfaction_container);

        rollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);

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

                updateRollViewPager(item);
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

    private class AnswerPictureAdapter extends StaticPagerAdapter {
        private List<String> imgs;

        public AnswerPictureAdapter() {
            imgs = new ArrayList<>();
        }

        public void setImgs(List<String> imgs) {
            if (imgs == null) {
                return;
            }

            this.imgs.clear();
            this.imgs.addAll(imgs);
            this.notifyDataSetChanged();
        }

        public String getItem(int position) {
            if (position < 0 || position >= imgs.size()) {
                return null;
            }

            return imgs.get(position);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ViewHolder holder = null;
            View convertView = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.view_answer_picture_page, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            String item = imgs.get(position);
            Glide.with(getContext())
                    .load(item)
                    .into(holder.imageView);
            return convertView;
        }

        @Override
        public int getCount() {
            return imgs.size();
        }

        public class ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                imageView = (ImageView) itemView.findViewById(R.id.iv_answer_picture);
            }
        }
    }

    private void updateRollViewPager(AnswerItem answerItem) {
        String answerUrl = answerItem.getAnswerUrl();
        if (StringUtil.isEmpty(answerUrl)) {
            rollViewPager.setVisibility(View.GONE);
            return;
        } else {
            rollViewPager.setVisibility(View.VISIBLE);
            answerPictureAdapter = new AnswerPictureAdapter();
            rollViewPager.setAdapter(answerPictureAdapter);

            String[] pictures = answerUrl.split(AnswerItem.SEPRATOR);
            pictureList.clear();
            pictureList.addAll(Arrays.asList(pictures));
            answerPictureAdapter.setImgs(pictureList);

            rollViewPager.setHintView(new ColorPointHintView(getContext(), getContext().getResources().getColor(R.color.common_main_red), Color.WHITE));

            rollViewPager.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ActivityJumper.startPictureShow(getContext(), pictureList, position);
                }
            });

        }
    }
}
