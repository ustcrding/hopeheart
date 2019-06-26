package com.boc.hopeheatapp.widget;

import android.content.Context;
import android.widget.RelativeLayout;

import com.boc.hopeheatapp.adapter.EvaluateInterface;
import com.boc.hopeheatapp.adapter.LinkInterface;
import com.boc.hopeheatapp.model.MsgEntity;

/**
 * 答案view
 *
 * @author dwl
 * @date 2018/2/11.
 */
public abstract class AnswerView extends RelativeLayout {

    private static final String TAG = "AnswerView";

    protected MsgEntity msgEntity;

    protected EvaluateInterface evaluateInterface;

    protected LinkInterface linkInterface;

    public AnswerView(Context context) {
        super(context);

        initView();
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 设置消息
     *
     * @param msg
     */
    public void setMsg(MsgEntity msg) {
        this.msgEntity = msg;

        updateData();
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg
     */
    public void setErrorMsg(String errorMsg) {
    }

    /**
     * 当设置数据后，更新ui
     */
    protected void updateUi() {

    }

    private void updateData() {
        if (msgEntity == null) {
            return;
        }

        updateUi();
    }

    /**
     * 设置满意度评价按钮
     *
     * @param evaluateInterface
     */
    public void setEvaluateInterface(EvaluateInterface evaluateInterface) {
        this.evaluateInterface = evaluateInterface;
    }

    /**
     * 设置多条答案点击回掉按钮
     *
     * @param linkInterface
     */
    public void setLinkInterface(LinkInterface linkInterface) {
        this.linkInterface = linkInterface;
    }
}
