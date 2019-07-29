package com.boc.hopeheatapp.fragement;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.MarkupListAdapter;
import com.boc.hopeheatapp.model.AreaEntity;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.VictimBaseEntity;
import com.boc.hopeheatapp.model.VictimEntity;
import com.boc.hopeheatapp.service.biz.VictimLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MarkupFragment extends BaseFragment {


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

    @Nullable
    @BindView(R.id.tv_title_right)
    protected TextView tvMarkup;

    @Nullable
    @BindView(R.id.tv_area)
    protected TextView tvArea;

    @Nullable
    @BindView(R.id.tv_type)
    protected TextView tvType;

    @Nullable
    @BindView(R.id.tv_count)
    protected TextView tvCount;


    @BindView(R.id.listview)
    protected LoadMoreListView mListView;

    private MarkupListAdapter adapter;

    private int mStartNo;
    private boolean mNeedRetry = true;
    private boolean mIsEnd = false;

    public static MarkupFragment newInstance() {
        MarkupFragment fragment = new MarkupFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_markup;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initView();

        initData();

        addListener();
    }

    private void initView() {
        // 手动调用,通知系统去测量
//        mSwipeRefreshLayout.measure(0, 0);
//        mSwipeRefreshLayout.setRefreshing(true);


        mListView.setDividerHeight(0);

        adapter = new MarkupListAdapter(getContext());
        mListView.setAdapter(adapter);


        btnBack.setVisibility(View.INVISIBLE);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("在线标记");

        tvMarkup.setVisibility(View.VISIBLE);
        tvMarkup.setText("标记");
    }

    private void initData() {
        requestAreaData();
        //requestData(true);
    }

    private void addListener() {
        mListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                if (mIsEnd) {
                    mListView.setLoadCompleted();
                } else {
                    requestData(false);
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onclickedItem((VictimEntity) adapter.getItem(position));
            }
        });

        tvMarkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<VictimEntity> victims = adapter.getItems();
                if (victims != null && victims.size() > 0) {
                    int count = 0;
                    for (VictimEntity entity : victims) {
                        if (entity.isChecked()) {
                            ++count;
                        }
                    }

                    if (count > 0) {
                        openMenu(count);
                    }
                }
            }
        });
    }

    private void onclickedItem(VictimEntity item) {
        ActivityJumper.startVictimDetailActivity(getContext(), item.getVictimId());
    }

    private void requestAreaData() {
        if (mNeedRetry) {
            mNeedRetry = false;
            UserEntity user = UserManager.getInstance().getUser();
            if (user != null) {
                String doctorId = UserEntity.TYPE_DOCTOR.equals(user.getRoleType()) ? user.getRoleId() : "";
                String volunteerId = TextUtils.isEmpty(doctorId) ? user.getRoleId() : "";
                VictimLoader victimLoader = new VictimLoader();
                victimLoader.getAresInfo(doctorId, volunteerId).subscribe(new Subscriber<AreaEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mNeedRetry = true;
                    }

                    @Override
                    public void onNext(AreaEntity areaEntity) {
                        if (areaEntity != null) {
                            tvArea.setText(areaEntity.getAddressCode());
                            if ("G".equals(areaEntity.getDisasterType())) {
                                tvType.setText("地质灾害");
                            } else if ("M".equals(areaEntity.getDisasterType())) {
                                tvType.setText("气象灾害");
                            } else if ("O".equals(areaEntity.getDisasterType())) {
                                tvType.setText("海洋灾害");
                            } else if ("E".equals(areaEntity.getDisasterType())) {
                                tvType.setText("生态环境灾害");
                            }

                            requestData(true);
                        }
                    }
                });
            }
        }
    }

    private void requestData(boolean force) {
        if (force) {
            mStartNo = 0;
            adapter.clearItems();
            mIsEnd = false;
        }
        UserEntity user = UserManager.getInstance().getUser();
        if (user != null) {
            mNeedRetry = false;

            VictimLoader victimLoader = new VictimLoader();
            victimLoader.getCoachedLis(user.getRoleId(), tvArea.getText().toString(), mStartNo + 1).subscribe(new Subscriber<VictimBaseEntity>() {
                @Override
                public void onCompleted() {
                    mListView.setLoadCompleted();
                }

                @Override
                public void onError(Throwable throwable) {
                    if (mStartNo == 0) {
                        mNeedRetry = true;
                    }
                }

                @Override
                public void onNext(VictimBaseEntity victimBaseEntity) {
                    if (victimBaseEntity != null ) {
                        List<VictimEntity> entities = victimBaseEntity.getVictims();
                        if (entities != null && entities.size() > 0) {
                            adapter.addItems(entities);
                        }

                        mStartNo += victimBaseEntity.getNumId();

                        tvCount.setText(mStartNo + "人");

                        mIsEnd = victimBaseEntity.isEndFlag();
                    }
                }
            });
        }
    }

    private void markup(boolean online) {
        List<VictimEntity> victims = adapter.getItems();
        if (victims != null && victims.size() > 0) {
            int count = 0;
            List<String> victimIds = new ArrayList<String>();
            for (VictimEntity entity : victims) {
                if (entity.isChecked()) {
                    victimIds.add(entity.getVictimId());
                }
            }

            if (victimIds.size() > 0) {
                 UserEntity user = UserManager.getInstance().getUser();
                 if (user != null) {
                     VictimLoader victimLoader = new VictimLoader();

                     String doctorId = UserEntity.TYPE_DOCTOR.equals(user.getRoleType()) ? user.getRoleId() : "";
                     String volunteerId = TextUtils.isEmpty(doctorId) ? user.getRoleId() : "";

                     StringBuilder sb = new StringBuilder();
                     for (String str : victimIds) {
                         sb.append(str);
                         sb.append(",");
                     }
                     sb.deleteCharAt(sb.length() - 1);

                     victimLoader.markCoached(doctorId, volunteerId, online ? "O" : "D", victimIds.size(), sb.toString()).subscribe(new Subscriber<Void>() {
                         @Override
                         public void onCompleted() {

                         }

                         @Override
                         public void onError(Throwable throwable) {

                         }

                         @Override
                         public void onNext(Void aVoid) {
                             requestData(true);
                         }
                     });
                 }
            }
        }
    }

    private void openMenu(int num) {
        View popView = LayoutInflater.from(getContext()).inflate(
                R.layout.markup_popup_menu, null);
        final PopupWindow popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ((TextView)popView.findViewById(R.id.tv_markup_as)).setText(String.format(getResources().getString(R.string.victim_select_num), num));

        popView.findViewById(R.id.online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markup(true);

                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        popView.findViewById(R.id.offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markup(false);

                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        popView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        // 设置弹出动画
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 顯示在根佈局的底部
        popupWindow.showAtLocation(getView(), Gravity.BOTTOM | Gravity.LEFT, 0,0);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mNeedRetry) {
            requestAreaData();
        }
    }
}
