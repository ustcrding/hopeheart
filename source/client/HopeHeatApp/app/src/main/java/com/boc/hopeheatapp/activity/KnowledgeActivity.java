package com.boc.hopeheatapp.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.CatalogListEntity;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.service.biz.CatalogLoader;
import com.boc.hopeheatapp.util.phone.DensityUtils;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.channel.ChannelAdapter;
import com.boc.hopeheatapp.widget.channel.GridItemClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 频道列表界面
 *
 * @author dwl
 * @date 2018/5/28.
 */
public class KnowledgeActivity extends TitleColorActivity {
    public static final int TYPE_RESCUE = 1;
    public static final int TYPE_PSYCHOLOGY = 2;

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private ChannelEntity channelEntity;

    private TextView tvKnowledgeNmae;
    private EditText etKnowledgeDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_knowledge);

        initView();

        channelEntity = getIntent().getParcelableExtra(ActivityJumper.EXTRA_CHANNEL_ENTITY);

        initData();
        addListener();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.rescue_knowledge);
        btnBack.setVisibility(View.VISIBLE);
        btnTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        tvKnowledgeNmae = (TextView) findViewById(R.id.knowledge_name);
        etKnowledgeDetail = (EditText)findViewById(R.id.knowledge_text);
    }

    private void initData() {
        tvTitle.setText(R.string.title_channel_list);

        initChannel();
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


        findViewById(R.id.ll_knowledge_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        findViewById(R.id.psychology_evaluation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "file:////android_asset/test.html";
                ActivityJumper.startQuestionnaireCompleteActivity(KnowledgeActivity.this, url);
            }
        });
    }

    private void initChannel() {
        requestChannel();
    }

    private void requestChannel() {

    }

    private void showMenu() {
        final View popipWindow_view = getLayoutInflater().inflate(R.layout.popup_menu,null,false);
        //创建Popupwindow 实例，200，LayoutParams.MATCH_PARENT 分别是宽高
        final PopupWindow popupWindow = new PopupWindow(popipWindow_view, DensityUtils.dp2px(this,300), ViewGroup.LayoutParams.MATCH_PARENT,true);
        //popupWindow.setAnimationStyle(R.style.AnimationFade);
        //点击其他地方消失
        popipWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popipWindow_view != null && popipWindow_view.isShown()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        Button button1 = (Button) popipWindow_view.findViewById(R.id.bt_ensure);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popipWindow_view != null && popipWindow_view.isShown()) {
                    popupWindow.dismiss();
                }
            }
        });

        Button button2 = (Button) popipWindow_view.findViewById(R.id.bt_cancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popipWindow_view != null && popipWindow_view.isShown()) {
                    popupWindow.dismiss();
                }
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.RIGHT | Gravity.TOP, 0, 0);
    }
}
