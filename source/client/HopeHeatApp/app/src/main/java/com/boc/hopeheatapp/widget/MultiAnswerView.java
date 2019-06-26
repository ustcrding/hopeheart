package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.util.phone.DensityUtils;

import java.util.List;

/**
 * 多条答案view
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class MultiAnswerView extends AnswerView {

    private static final String TAG = "MultiAnswerView";
    protected ImageView ivLeft;

    protected LinearLayout answerContainer;
    protected TextView tvMultiAnswerTip;
    protected LinearLayout multiAnswerContainer;

    public MultiAnswerView(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        LayoutInflater inflate = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.view_multi_answer, this, true);

        ivLeft = (ImageView) view.findViewById(R.id.iv_left);

        answerContainer = (LinearLayout) view.findViewById(R.id.ll_answer_container);
        tvMultiAnswerTip = (TextView) view.findViewById(R.id.tv_multi_answer_tip);
        multiAnswerContainer = (LinearLayout) view.findViewById(R.id.ll_multi_answer_container);

        updateView();
    }

    protected void updateView() {
        multiAnswerContainer.setVisibility(View.VISIBLE);
        tvMultiAnswerTip.setVisibility(View.VISIBLE);
        multiAnswerContainer.removeAllViews();
    }

    @Override
    protected void updateUi() {
        BaseResponse<AnswerEntity> response = (BaseResponse<AnswerEntity>) msgEntity.getContent();
        AnswerEntity answer = response.getData();
        List<AnswerItem> list = answer.getList();
        for (int i = 0; i < list.size(); i++) {
            TextView textView1 = genTextView(answer, i);
            multiAnswerContainer.addView(textView1);
        }
    }

    private TextView genTextView(final AnswerEntity data, final int index) {
        final AnswerItem item = data.getList().get(index);
        TextView textView = new TextView(getContext());
        textView.setText(item.getQuestion());
        textView.setTextColor(getContext().getResources().getColorStateList(R.color.color_multi_answer_item));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        int left = DensityUtils.dip2Px(getContext(), 5);
        int top = DensityUtils.dip2Px(getContext(), 3);
        textView.setPadding(left, top, 0, top);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkInterface != null) {
                    linkInterface.answerLink(data, index);
                }
            }
        });
        return textView;
    }
}
