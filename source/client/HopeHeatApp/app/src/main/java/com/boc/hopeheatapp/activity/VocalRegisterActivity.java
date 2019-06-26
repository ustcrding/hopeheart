package com.boc.hopeheatapp.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.vocal.VocalDownloadPwdListener;
import com.boc.hopeheatapp.vocal.VocalRegistListener;
import com.boc.hopeheatapp.vocal.VocalRegister;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.event.EventRegisterVocalSuccess;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.log.Logger;

import de.greenrobot.event.EventBus;
import rx.Subscriber;

/**
 * 声纹注册页面
 *
 * @author dwl
 * @date 2018/2/22.
 */
public class VocalRegisterActivity extends TitleColorActivity {

    private static final String TAG = "VocalRegisterActivity";
    // title 左侧返回按钮
    private View btnBack;

    // title正文
    private TextView tvTitle;

    // title右侧按钮
    private TextView btnTitleRight;

    // 验证码框
    private TextView tvVerifyNum;

    // 训练剩余次数框
    private TextView tvLeftTime;

    // 请说话按钮
    private Button btnSpeechVoiceMic;

    //重试按钮
    private TextView btnRetry;

    // 下载状态
    private TextView tvDownloadStatus;

    // 麦克风波形图
    private ImageView ivVolume;

    private VocalRegister vocalRegister;
    private String authid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vocal_register);
        initView();
        addListener();

        initData();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        btnTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvLeftTime = (TextView) findViewById(R.id.tv_left_time);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_vocal_register);

        btnBack.setVisibility(View.VISIBLE);
        btnTitleRight.setVisibility(View.INVISIBLE);

        btnSpeechVoiceMic = (Button) findViewById(R.id.btn_speech_voice_mic);

        btnRetry = (TextView) findViewById(R.id.btn_retry);

        tvDownloadStatus = (TextView) findViewById(R.id.tv_download_status);
    }

    /**
     * 初始化view
     */
    private void initView() {
        initTitle();

        tvVerifyNum = (TextView) findViewById(R.id.tv_verify_num);
        ivVolume = (ImageView) findViewById(R.id.iv_volume);

        btnSpeechVoiceMic.setEnabled(false);
    }

    private void addListener() {
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

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedRetry();
            }
        });
    }

    private void initData() {
        //authid = getIntent().getStringExtra(ActivityJumper.EXTRA_AUTHID);
        UserEntity userEntity = UserManager.getInstance().getUser();
        if (userEntity == null) {
            if (Logger.isDebugable()) {
                Logger.d(TAG, "initData | userEntity is null!");
            }
            ToastUtils.showShort(VocalRegisterActivity.this, R.string.public_tip_login_first);
            finish();
            return;
        }

        authid = "" + userEntity.getUserId();
        vocalRegister = new VocalRegister(getApplicationContext(), authid);
        downloadVolumnPwd();
    }

    public void onClickedBack() {
        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        vocalRegister.cancelOperation();
    }

    public void startSpeech() {
        showPopupWindow();
        register();

        ToastUtils.dismissToast();
    }

    public void stopSpeech() {
        dismissPopupWindow();
        vocalRegister.stopSpeech();
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

    private void downloadVolumnPwd() {
        vocalRegister.downloadPwd(new VocalDownloadPwdListener() {
            @Override
            public void onDownloadSuccess(String numPwd, String[] numPwdSegs) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "onDownloadSuccess | numPwd = " + numPwd + ", numPwdSegs = " + numPwdSegs[0]);
                }

                if (numPwdSegs == null || numPwdSegs.length == 0) {
                    if (Logger.isDebugable()) {
                        Logger.d(TAG, "onDownloadSuccess | numPwdSegs is null, return");
                    }

                    ToastUtils.showLong(VocalRegisterActivity.this, R.string.tip_download_vocal_pwd_failed);
                    btnSpeechVoiceMic.setEnabled(false);
                    showDownloadFailed();
                    return;
                }
                String tip = String.format(getString(R.string.tip_left_times_format), 1, numPwdSegs.length);
                showDownloadSuccess();
                tvLeftTime.setVisibility(View.VISIBLE);
                tvLeftTime.setText(tip);
                tvVerifyNum.setText(numPwdSegs[0]);
                btnSpeechVoiceMic.setEnabled(true);

            }

            @Override
            public void onDownloadError(int code, String msg) {
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "onDownloadError | code = " + code + ", msg = " + msg);
                }
                ToastUtils.showLong(VocalRegisterActivity.this, msg);
                btnSpeechVoiceMic.setEnabled(false);

                showDownloadFailed();
            }
        });
    }

    private void register() {
        vocalRegister.register(authid, new VocalRegistListener() {
            @Override
            public void onRegisterSuccess() {
                //ToastUtils.showLong(getApplication(), R.string.tip_vocal_register_success);
                ToastUtils.showShort(getApplication(), R.string.tip_vocal_registing);
                registerVocal();
            }

            @Override
            public void onRegisterFailed(int code, String msg) {
                ToastUtils.showLong(getApplication(), msg);
            }

            @Override
            public void onNextSegs(int nowTimes, int leftTimes, String numPwdSeg) {
                String tip = String.format(getString(R.string.tip_left_times_format), nowTimes, nowTimes + leftTimes);
                tvLeftTime.setText(tip);
                tvVerifyNum.setText(numPwdSeg);
            }

            @Override
            public void onRegisterVoloum(int volume) {

            }
        });
    }

    private void registerVocal() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "registerVocal | userId = " + authid + ", authid = " + authid + ", groupId = " + vocalRegister.getGroupId());
        }

        UserLoader userLoader = new UserLoader();
        userLoader.registerVocal(authid, vocalRegister.getGroupId()).subscribe(new Subscriber<Void>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showLong(getApplication(), throwable.getMessage());
            }

            @Override
            public void onNext(Void object) {
                handleRegisterSuccess();
            }
        });
    }

    private void handleRegisterSuccess() {
        if (Logger.isDebugable()) {
            Logger.d(TAG, "handleRegisterSuccess | register vocal success");
        }

        ToastUtils.showShort(getApplication(), R.string.tip_vocal_register_success);

        UserEntity userEntity = UserManager.getInstance().getUser();
        //更新注册信息
        userEntity.setAuthId(authid);
        userEntity.setGroupId(vocalRegister.getGroupId());
        UserManager.getInstance().setUser(userEntity);
        EventBus.getDefault().post(new EventRegisterVocalSuccess());
        finish();
    }

    private void onClickedRetry() {
        showDownloadStatus();
        downloadVolumnPwd();
    }

    private void showDownloadStatus() {
        tvVerifyNum.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
        tvDownloadStatus.setVisibility(View.VISIBLE);
    }

    private void showDownloadFailed() {
        tvVerifyNum.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
        tvDownloadStatus.setVisibility(View.GONE);
    }

    private void showDownloadSuccess() {
        tvVerifyNum.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.GONE);
        tvDownloadStatus.setVisibility(View.GONE);
    }
}
