package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.view.View;

import com.boc.hopeheatapp.R;

/**
 * @author dwl
 * @date 2018/2/11.
 */
public class ErrorAnswerView extends SingleAnswerView {

    public ErrorAnswerView(Context context) {
        super(context);
    }

    @Override
    protected void updateView() {
        super.updateView();
        statisfactionContainer.setVisibility(View.GONE);
    }

    @Override
    protected void updateUi() {
        tvLeft.setText(R.string.tip_no_result);
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        tvLeft.setText(errorMsg);
    }

}
