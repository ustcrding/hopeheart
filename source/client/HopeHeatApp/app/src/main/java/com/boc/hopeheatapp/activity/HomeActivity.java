package com.boc.hopeheatapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.constant.ExtraAction;
import com.boc.hopeheatapp.conversation.ConversationView;
import com.boc.hopeheatapp.conversation.VoiceNaviView;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.VersionEntity;
import com.boc.hopeheatapp.model.event.EventLogout;
import com.boc.hopeheatapp.navi.NaviInterface;
import com.boc.hopeheatapp.service.biz.RobotLoader;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;

import java.util.Map;

import rx.Subscriber;

/**
 * home Activity
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class HomeActivity extends TitleColorActivity {

    private final String TAG = "HomeActivity";

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private RadioGroup rgFocusGroup;
    // 知识库tab
    private RadioButton rbFocusKb;
    // 语音导航
    private RadioButton rbFocusVoiceNavi;

    // 知识库频道
    private ConversationView kbView;

    // 语音导航频道
    private VoiceNaviView voiceNaviView;

    private Map<String, NaviInterface> navigator;

    private int backPressCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        initView();
        addListener();
        initUpdate();

        handleIntent(getIntent());
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_boc);
        btnBack.setVisibility(View.INVISIBLE);
        btnTitleRight.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        rgFocusGroup = (RadioGroup) findViewById(R.id.rg_focus_group);
        rbFocusKb = (RadioButton) findViewById(R.id.rb_focus_kb);
        rbFocusVoiceNavi = (RadioButton) findViewById(R.id.rb_focus_voice_navi);

        kbView = (ConversationView) findViewById(R.id.cv_kb);
        voiceNaviView = (VoiceNaviView) findViewById(R.id.cv_voice_navi);
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        btnTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedTitleRight();
            }
        });

        rgFocusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_focus_kb) {
                    // 选择知识库
                    changeToKbFocus();
                } else if (checkedId == R.id.rb_focus_voice_navi) {
                    // 选择知识库
                    changeToVoiceNaviFocus();
                }
            }
        });
    }

    private void changeToKbFocus() {
        kbView.setVisibility(View.VISIBLE);
        voiceNaviView.setVisibility(View.GONE);
    }

    private void changeToVoiceNaviFocus() {
        kbView.setVisibility(View.GONE);
        voiceNaviView.setVisibility(View.VISIBLE);
    }

    private void onClickedTitleRight() {
        ActivityJumper.startSettingActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //destroyRecongizer();

        TtsManager.getInstance(getApplicationContext()).stop();

        //kbView.destroy();
    }

    /**
     * 初始化update
     */
    private void initUpdate() {
        RobotLoader loader = new RobotLoader();

        loader.getLastVersion(com.boc.hopeheatapp.util.Environment.getMyVersionCode(this)).subscribe(new Subscriber<BaseResponse<VersionEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "getLastVersion.onError ", throwable);
                }
            }

            @Override
            public void onNext(BaseResponse<VersionEntity> response) {
                if (response.getCode() == BaseResponse.OK) {
                    VersionEntity version = response.getData();
                    if (version.getVersionCode() >
                            com.boc.hopeheatapp.util.Environment.getMyVersionCode(HomeActivity.this)) {
                        ActivityJumper.startUpdateDialog(HomeActivity.this, version);
                    }
                } else {
                    if (Logger.isDebugable()) {
                        Logger.e(TAG, "getLastVersion.onNext " + response.getMessage());
                    }
                }
            }
        });
    }

    private Handler handler = new Handler();

    @Override
    public void onBackPressed() {
        tryExit();
    }

    private void tryExit() {
        backPressCount++;
        if (backPressCount > 1) {
            this.finish();
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressCount = 0;
                }
            }, 2000);
            ToastUtils.showShort(this, R.string.tip_exit_program);
        }
    }

    public void onEvent(EventLogout event) {
        if (event == null) {
            return;
        }

        UserManager.getInstance().logout();

        //跳转到声纹登录页面
        ActivityJumper.startVocalVerifyActivity(this);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        String doAction = intent.getStringExtra(ActivityJumper.EXREA_DO_ACTION);
        if (StringUtil.equals(doAction, ExtraAction.ACTION_REGISTER_VOCAL)) {
            ActivityJumper.startVocalRegisterActivity(getApplicationContext());

            if (Logger.isDebugable()) {
                Logger.d(TAG, "handleIntent | doAction = " + doAction + ", start VocalRegisterActivity");
            }
        }
    }
}
