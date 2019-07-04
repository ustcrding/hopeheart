package com.boc.hopeheatapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.ChatRecyclerAdapter;
import com.boc.hopeheatapp.model.ChatMessageBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 频道列表界面
 *
 * @author dwl
 * @date 2018/5/28.
 */
public class ConsultActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;
    private EditText etMessage;

    private RecyclerView lv_chart;
    private ChatRecyclerAdapter tbAdapter;
    public List<ChatMessageBean> tblist = new ArrayList<ChatMessageBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consult);

        initView();

        initData();

        addListener();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.consult_key);
        btnTitleRight.setVisibility(View.VISIBLE);
        btnTitleRight.setText(R.string.finish);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        lv_chart = (RecyclerView) findViewById(R.id.lv_chart);
        etMessage = (EditText) findViewById(R.id.et_text);

        tbAdapter = new ChatRecyclerAdapter(this, tblist);

        lv_chart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lv_chart.setAdapter(tbAdapter);
    }

    private void initData() {
        tvTitle.setText(R.string.consult_key);

        ChatMessageBean send = new ChatMessageBean();
        send.setType(ChatRecyclerAdapter.FROM_USER_MSG);
        send.setUserContent("您好您好");
        tblist.add(send);


        ChatMessageBean receive = new ChatMessageBean();
        receive.setType(ChatRecyclerAdapter.TO_USER_MSG);
        receive.setUserContent("您好您好");
        tblist.add(receive);

        tbAdapter.notifyDataSetChanged();
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable msg = etMessage.getText();
                if (msg != null && msg.length() > 0) {
                    etMessage.setText("");
                    ChatMessageBean bean = new ChatMessageBean();
                    bean.setType(ChatRecyclerAdapter.TO_USER_MSG);
                    bean.setUserContent(msg.toString());
                    tblist.add(bean);

                    tbAdapter.notifyDataSetChanged();
                }
            }
        });

        findViewById(R.id.ll_title_right_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ConsultActivity.this);
                alertBuilder.setTitle(R.string.tips_title);
                alertBuilder.setMessage(R.string.finish_confirm);
                alertBuilder.setNegativeButton(R.string.ensure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        ActivityJumper.startCommentActivity(getApplicationContext());
                        finish();
                    }
                });
                alertBuilder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

}
