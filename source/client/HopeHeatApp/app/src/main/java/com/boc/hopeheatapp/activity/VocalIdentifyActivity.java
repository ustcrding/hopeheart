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
import com.boc.hopeheatapp.vocal.VocalConst;
import com.boc.hopeheatapp.vocal.VocalIdentifier;
import com.boc.hopeheatapp.vocal.VocalVerifyListener;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.event.EventResigterSuccess;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Subscriber;

/**
 * 按组进行识别页面
 *
 * @author dwl
 * @date 2018/2/13.
 */
public class VocalIdentifyActivity extends TitleColorActivity {

    private static final String TAG = "VocalIdentifyActivity";
    private View btnBack;
    private TextView tvTitle;
    private TextView btnTitleRight;

    private TextView tvVerifyNum;

    private AbsVocalVerifier vocalVerifier;
    private String verifyNum;
    private String groupid = VocalConst.DEFAULT_GROUP_ID;
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

        initData();
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
        //vocalVerifier = new VocalVerifier(this);

        vocalVerifier = new VocalIdentifier(this);

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
        ToastUtils.dismissToast();
        vocalVerifier.startSpeech(groupid, verifyNum, new VocalVerifyListener() {
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
                ToastUtils.showShort(VocalIdentifyActivity.this, tip);
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
        vocalVerifier.cancle();
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

    public void onEvent(EventResigterSuccess event) {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "onEvent | EventResigterSuccess");
        }
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
