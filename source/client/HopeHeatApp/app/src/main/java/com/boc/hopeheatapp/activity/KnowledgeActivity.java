package com.boc.hopeheatapp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.GridViewAdapter;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.util.phone.DensityUtils;

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

        if (mType == TYPE_RESCUE) {
            tvTitle.setText(R.string.rescue_knowledge);
        } else if (mType == TYPE_PSYCHOLOGY) {
            tvTitle.setText(R.string.psychology_knowledge);
        }
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
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.popup_menu,null,false);
        final PopupWindow popupWindow = new PopupWindow(popupWindow_view, DensityUtils.dp2px(this,300), ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        TextView tvCategory = (TextView) popupWindow_view.findViewById(R.id.tv_category);
        TextView tvSubCategory = (TextView) popupWindow_view.findViewById(R.id.tv_sub_category);

        if (mType == TYPE_RESCUE) {
            tvCategory.setText(R.string.rescue_knowledge_category);
            tvSubCategory.setText(R.string.rescue_knowledge_subcategory);
        } else if (mType == TYPE_PSYCHOLOGY) {
            tvCategory.setText(R.string.psychology_knowledge_category);
            tvSubCategory.setText(R.string.psychology_knowledge_subcategory);
        }

        final GridView category = (GridView) popupWindow_view.findViewById(R.id.category);
        final GridView subCategory = (GridView) popupWindow_view.findViewById(R.id.sub_category);
        final GridViewAdapter categoryAdapter = new GridViewAdapter(this);
        final GridViewAdapter subCategoryAdapter = new GridViewAdapter(this);
        if (mType == TYPE_RESCUE) {
            categoryAdapter.setData(getResources().getStringArray(R.array.category_list), -1);
        } else if (mType == TYPE_PSYCHOLOGY) {
            //TODO
        }
        //subCategoryAdapter.setData(getResources().getStringArray(R.array.sub_category_list), -1);

        category.setAdapter(categoryAdapter);
        subCategory.setAdapter(subCategoryAdapter);

        category.setSelector(new ColorDrawable(Color.TRANSPARENT));
        subCategory.setSelector(new ColorDrawable(Color.TRANSPARENT));


        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setSelection(position);
                categoryAdapter.notifyDataSetChanged();

                if (mType == TYPE_RESCUE) {
                    if (position == 0) {
                        subCategoryAdapter.setData(getResources().getStringArray(R.array.sub_category_list0), -1);
                        subCategoryAdapter.notifyDataSetChanged();
                    } else if (position == 1) {
                        subCategoryAdapter.setData(getResources().getStringArray(R.array.sub_category_list1), -1);
                        subCategoryAdapter.notifyDataSetChanged();
                    } else if (position == 2) {
                        subCategoryAdapter.setData(getResources().getStringArray(R.array.sub_category_list2), -1);
                        subCategoryAdapter.notifyDataSetChanged();
                    } else if (position == 3) {
                        subCategoryAdapter.setData(getResources().getStringArray(R.array.sub_category_list3), -1);
                        subCategoryAdapter.notifyDataSetChanged();
                    }
                } else if (mType == TYPE_PSYCHOLOGY) {
                    //TODO
                }
            }
        });

        subCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subCategoryAdapter.setSelection(position);
                subCategoryAdapter.notifyDataSetChanged();
            }
        });


        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        Button button1 = (Button) popupWindow_view.findViewById(R.id.bt_ensure);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow_view != null && popupWindow_view.isShown()) {
                    popupWindow.dismiss();
                }
            }
        });

        Button button2 = (Button) popupWindow_view.findViewById(R.id.bt_cancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow_view != null && popupWindow_view.isShown()) {
                    popupWindow.dismiss();
                }

                if (categoryAdapter.getSelection() >= 0 && subCategoryAdapter.getSelection() >= 0) {
                    //TODO
                }
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.RIGHT | Gravity.TOP, 0, 0);
    }
}
