package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.service.api.UserService;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.util.ToastUtils;

import rx.Subscriber;


/**
 * 频道列表界面
 *
 * @author dwl
 * @date 2018/5/28.
 */
public class CommentActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;
    private RatingBar rationBar;
    private TextView tvMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);

        initView();

        initData();

        addListener();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.comment_title);
        btnTitleRight.setVisibility(View.VISIBLE);
        btnTitleRight.setText(R.string.submit);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        rationBar = (RatingBar) findViewById(R.id.rationBar);
        tvMessage = (TextView) findViewById(R.id.comment_msg);
    }

    private void initData() {
        tvTitle.setText(R.string.comment_title);
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

        rationBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ration = (int) rationBar.getRating();
                if (ration < 3) {
                    tvMessage.setText(R.string.yawp);
                } else if (ration < 5) {
                    tvMessage.setText(R.string.good);
                } else {
                    tvMessage.setText(R.string.wonderful);
                }
            }
        });


        findViewById(R.id.ll_title_right_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "W";
                int ration = (int) rationBar.getRating();
                if (ration < 3) {
                    msg = "Y";
                } else if (ration < 5) {
                    msg = "G";
                }
                UserLoader userService = new UserLoader();
                userService.uploadComment("test001", "doc001", msg).subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showShort(getApplicationContext(), R.string.error_tips);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        ToastUtils.showShort(getApplicationContext(), R.string.success_tips);
                        finish();
                    }
                });
            }
        });
    }

}
