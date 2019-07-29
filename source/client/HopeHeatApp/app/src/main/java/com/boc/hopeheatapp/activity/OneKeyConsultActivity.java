package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.AnswerView;
import com.boc.hopeheatapp.widget.AskView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class OneKeyConsultActivity extends TitleColorActivity {

    /**
     * title 左侧返回按钮
     */
    @Nullable
    @BindView(R.id.top_back_container)
    protected View btnBack;

    /**
     * title正文
     */
    @Nullable
    @BindView(R.id.top_title_text)
    protected TextView tvTitle;

    /**
     * 输入框
     */
    @Nullable
    @BindView(R.id.et_input)
    protected EditText etInput;

    @Nullable
    @BindView(R.id.ll_conversation_container)
    protected LinearLayout conversationContainer;

    @Nullable
    @BindView(R.id.scrollview)
    protected ScrollView scrollView;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_one_key_consult);
        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        initTitle();
    }

    private void initTitle() {
        btnBack.setVisibility(View.VISIBLE);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("一键咨询");
    }

    private void initData() {
        addAnswer("您好，有什么可以帮您！");
    }

    @OnClick(R.id.top_back_container)
    public void onClickTitleBack() {
        finish();
    }

    @OnClick(R.id.btn_send)
    public void onClickedSend() {
        String input  = etInput.getText().toString();

        if (StringUtil.isEmpty(input)) {
            ToastUtils.showLong(getApplicationContext(), "输入内容为空");
            return;
        }

        addAskView(input);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addAnswer("收到，请稍候！");

                etInput.setText("");
            }
        }, 500);
    }

    private void addAskView(String content) {
        MsgEntity msgEntity = MsgEntity.genAskMsg();
        msgEntity.setContent(content);
        AskView askView = new AskView(this);
        askView.setMsg(msgEntity);

        addConversationView(askView);
    }

    private void addAnswer(String content) {
//        AnswerView answerView = AnswerViewFactory.makeAnswerView(this, content);
//        addConversationView(answerView);
    }

    protected void addConversationView(AnswerView answerView) {
        conversationContainer.addView(answerView);
        scrollToBottom();
    }

    protected void addConversationView(AskView askView) {
        conversationContainer.addView(askView);
        scrollToBottom();
    }

    private void scrollToBottom() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
