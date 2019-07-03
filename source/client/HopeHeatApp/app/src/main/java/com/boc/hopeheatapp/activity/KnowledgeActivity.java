package com.boc.hopeheatapp.activity;

import android.graphics.Color;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.GridViewAdapter;
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

    private int mType;

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private ChannelEntity channelEntity;

    private TextView tvKnowledgeNmae;
    private TextView etKnowledgeDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getIntent().getIntExtra(ActivityJumper.EXTRA_TYPE, TYPE_RESCUE);

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
        etKnowledgeDetail = (TextView)findViewById(R.id.knowledge_text);

        if (mType == TYPE_RESCUE) {
            tvKnowledgeNmae.setText(R.string.rescue_knowledge_type);
        } else if (mType == TYPE_PSYCHOLOGY) {
            tvKnowledgeNmae.setText(R.string.psychology_knowledge_type);
        }
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
        final PopupWindow popupWindow = new PopupWindow(popipWindow_view, DensityUtils.dp2px(this,300), ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        final GridView category = (GridView) popipWindow_view.findViewById(R.id.category);
        final GridView subCategory = (GridView) popipWindow_view.findViewById(R.id.sub_category);
        final GridViewAdapter categoryAdapter = new GridViewAdapter(this);
        final GridViewAdapter subCategoryAdapter = new GridViewAdapter(this);
        categoryAdapter.setData(getResources().getStringArray(R.array.category_list), -1);
        subCategoryAdapter.setData(getResources().getStringArray(R.array.sub_category_list), -1);

        category.setAdapter(categoryAdapter);
        subCategory.setAdapter(subCategoryAdapter);

        category.setSelector(new ColorDrawable(Color.TRANSPARENT));
        subCategory.setSelector(new ColorDrawable(Color.TRANSPARENT));


        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setSeclection(position);
                categoryAdapter.notifyDataSetChanged();
            }
        });

        subCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subCategoryAdapter.setSeclection(position);
                subCategoryAdapter.notifyDataSetChanged();
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
