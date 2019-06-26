package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.view.View;

/**
 * 单条答案，没有赞踩按钮的界面
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class SingleAnswerNoZcView extends SingleAnswerView {

    public SingleAnswerNoZcView(Context context) {
        super(context);

        needShowLikeContainer = false;
    }

    @Override
    protected void updateView() {
        super.updateView();

        statisfactionContainer.setVisibility(View.GONE);
    }
}
