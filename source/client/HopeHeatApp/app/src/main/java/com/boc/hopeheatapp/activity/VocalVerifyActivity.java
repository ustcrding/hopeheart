package com.boc.hopeheatapp.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.vocal.AbsVocalVerifier;
import com.boc.hopeheatapp.vocal.VocalVerifier;
import com.boc.hopeheatapp.vocal.VocalVerifyListener;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.event.EventResigterSuccess;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.json.JsonUtils;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Subscriber;

/**
 * @author dwl
 * @date 2018/2/12.
 */
public class VocalVerifyActivity extends TitleColorActivity {
    private final String TAG = "VocalVerifyActivity";

    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private TextView tvVerifyNum;

    private AbsVocalVerifier vocalVerifier;
    private String verifyNum;
    private String authid = "12";
    private String groupid = "3844707110";
    private LinearLayout btnSpeechVoiceMic;

    // 麦克风波形图
    private ImageView ivVolume;

    // 注册按钮
    private TextView btnRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vocal_verify);
        initView();
        addListener();

        if (Logger.isDebugable()) {
            Logger.d(TAG, "VocalVerifyActivity.onCreate");
        }

        String userInfoJson = BocSettings.getInstance().getString(BocSettings.LOGIN_USER_INFO);
        if (StringUtil.isEmpty(userInfoJson)) {
            handleLoginFirst();
            return;
        }

        UserEntity user = JsonUtils.fromJson(userInfoJson, UserEntity.class);
        if (user == null || user.getAuthId() == null) {
            handleLoginFirst();
            return;
        }

        authid = user.getAuthId();
        initData();
    }

    private void handleLoginFirst() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "handleLoginFirst");
        }

        //Toast.makeText(this, R.string.tip_vocal_login_first, Toast.LENGTH_LONG).show();
        ActivityJumper.startLoginActivity(this);
        finish();
        return;
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_vocal_verify);

        btnBack.setVisibility(View.INVISIBLE);
        btnTitleRight.setVisibility(View.VISIBLE);
        btnTitleRight.setText(R.string.login_by_pwd);

        btnSpeechVoiceMic = (LinearLayout) findViewById(R.id.ll_speech_voice_container);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        tvVerifyNum = (TextView) findViewById(R.id.tv_verify_num);

        ivVolume = (ImageView) findViewById(R.id.iv_volume);

        btnRegister = (TextView) findViewById(R.id.btn_register);
    }

    private void addListener() {
        btnTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedTitleRight();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedBack();
            }
        });

        btnSpeechVoiceMic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchVoiceMic(event);
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedRegister();
            }
        });
    }

    public void onClickedBack() {
        finish();
    }

    public void onClickedTitleRight() {
        ActivityJumper.startLoginActivity(this);
        finish();
    }

    /**
     * 点击注册按钮
     */
    public void onClickedRegister() {
        ActivityJumper.startRegisterActivity(this);
    }

    private void initData() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "initData");
        }

        vocalVerifier = new VocalVerifier(this);
        verifyNum = vocalVerifier.getVerifyNum();
        tvVerifyNum.setText(verifyNum);
    }

    public static final int MSG_CHECK_LONGPRESS = 1000;
    public static final int MSG_START_RECORD = 1001;
    private static final int MSG_CHECK_ANIMATION = 1002;
    private boolean mBeginToTestLongPress;
    private boolean mHasLongPress;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CHECK_LONGPRESS:
                    if (mBeginToTestLongPress) {
                        mHasLongPress = true;
                        sendEmptyMessage(MSG_START_RECORD);
                    }
                    break;
                case MSG_START_RECORD:
                    startSpeech();
                    break;
                case MSG_CHECK_ANIMATION:
                    ivVolume.setBackgroundResource(R.drawable.volume);
                    ((AnimationDrawable) ivVolume.getBackground()).start();
                default:
                    break;
            }
        }
    };

    private boolean onTouchVoiceMic(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mBeginToTestLongPress = true;
                mHandler.sendEmptyMessageDelayed(MSG_CHECK_LONGPRESS, ViewConfiguration.getLongPressTimeout());
                break;
            case MotionEvent.ACTION_UP:
                if (mHasLongPress) {
                    mHasLongPress = false;
                    stopSpeech();
                }
                mBeginToTestLongPress = false;
                mHandler.removeMessages(MSG_CHECK_LONGPRESS);
                stopSpeech();
                break;
            case MotionEvent.ACTION_CANCEL:
                mBeginToTestLongPress = false;
                mHandler.removeMessages(MSG_CHECK_LONGPRESS);
                if (mHasLongPress) {
                    stopSpeech();
                    mHasLongPress = false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void startSpeech() {
        vocalVerifier.startSpeech(authid, verifyNum, new VocalVerifyListener() {
            @Override
            public void onVocalVerifyVolumn(int volumn) {

            }

            @Override
            public void onVocalVerifySuccess(String authid) {
                handleVocalVerifySuccess(authid);
            }

            @Override
            public void onVocalVerifyFailed(int code, String msg) {
                //String tip = getString(R.string.tip_vocal_verify_failed) + ", 失败原因：" + msg;
                String tip = msg;
                ToastUtils.showShort(VocalVerifyActivity.this, tip);
            }
        });

        showPopupWindow();
    }

    private void handleVocalVerifySuccess(String authid) {
        ToastUtils.showShort(getApplication(), R.string.tip_vocal_verify_success);
        loginByVocal(authid);
    }

    public void stopSpeech() {
        vocalVerifier.stopSpeech();

        dismissPopupWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (vocalVerifier != null) {
            vocalVerifier.cancle();
            vocalVerifier = null;
        }
    }

    private void showPopupWindow() {
        if (ivVolume != null) {
            ivVolume.setVisibility(View.VISIBLE);
            ivVolume.setBackgroundResource(R.drawable.volume_init);
            ((AnimationDrawable) ivVolume.getBackground()).start();
            mHandler.sendEmptyMessageDelayed(MSG_CHECK_ANIMATION, 1000);
        }
    }

    private void dismissPopupWindow() {
        if (ivVolume != null) {
            ivVolume.setBackgroundDrawable(null);
            ivVolume.setVisibility(View.GONE);
        }
    }

    /**
     * 接收到注册成功事件
     *
     * @param event
     */
    public void onEvent(EventResigterSuccess event) {
        finish();
    }

    private void loginByVocal(String authid) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "loginByVocal | authid = " + authid);
        }
        UserLoader userLoader = new UserLoader();
        userLoader.loginByVocal(authid).subscribe(new Subscriber<UserEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                handleLoginFailed(throwable);
            }

            @Override
            public void onNext(UserEntity userEntity) {
                handleLoginSuccess(userEntity);
            }
        });
    }

    private void handleLoginSuccess(UserEntity userEntity) {
        if (userEntity == null) {
            ToastUtils.showLong(getApplication(), R.string.login_failed);
            return;
        }

        ToastUtils.showShort(getApplication(), R.string.login_success);
        UserManager.getInstance().setUser(userEntity);
        // 打开home页面
        ActivityJumper.startHomeActivity(getApplicationContext());
        finish();
    }

    public void handleLoginFailed(Throwable throwable) {
        String message = throwable.getMessage();
        if (StringUtil.isNotBlank(message)) {
            ToastUtils.showLong(getApplication(), throwable.getMessage());
        } else {
            ToastUtils.showLong(getApplication(), R.string.login_failed);
            return;
        }
    }

}
