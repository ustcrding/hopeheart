package com.boc.hopeheatapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.ApiConfig;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.model.event.EventLogout;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.ToastUtils;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.PreferenceItemView;
import com.boc.hopeheatapp.widget.SwitchItemView;

import de.greenrobot.event.EventBus;

/**
 * 设置界面
 *
 * @author dwl
 * @date 2018/1/23.
 */
public class SettingActivity extends TitleColorActivity {

    private View btnBack;
    private TextView tvTitle;

    // tts开关
    private SwitchItemView viewTtsSwitch;

    // 语音导航（aiui实现）
    private PreferenceItemView viewVoiceNaviByAiui;

    // 声纹识别
    private PreferenceItemView viewVocalVerify;

    // 声纹注册
    private PreferenceItemView viewVocalRegister;

    // app切换
    private PreferenceItemView viewAppSwitch;

    // 关于我们
    private PreferenceItemView viewAbout;

    //注销登录
    private TextView btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        initView();
        addListener();
    }

    private void initTitle() {
        btnBack = findViewById(R.id.top_back_container);
        tvTitle = (TextView) findViewById(R.id.top_title_text);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.title_setting);
        btnBack.setVisibility(View.VISIBLE);
    }

    private void initView() {
        initTitle();
        viewTtsSwitch = (SwitchItemView) findViewById(R.id.view_tts_switch);

        viewTtsSwitch.setTitle(getString(R.string.tts_speak_switch));
        viewTtsSwitch.setSwitch(BocSettings.getInstance().getBoolean(BocSettings.KEY_TTS_SWITH));
        viewTtsSwitch.setSwitchLogo(R.drawable.icon_yuyinbobao);

        viewVoiceNaviByAiui = (PreferenceItemView) findViewById(R.id.view_voice_navi_by_aiui);
        viewVoiceNaviByAiui.setIconAndName(-1, getString(R.string.item_voice_navi_by_aiui));

        viewVocalVerify = (PreferenceItemView) findViewById(R.id.view_vocal_verify);
        viewVocalVerify.setIconAndName(-1, getString(R.string.item_vocal_verify));

        viewVocalRegister = (PreferenceItemView) findViewById(R.id.view_vocal_register);
        viewVocalRegister.setIconAndName(R.drawable.icon_shengbozhuce, getString(R.string.item_vocal_register));

        viewAppSwitch = (PreferenceItemView) findViewById(R.id.view_app_switch);
        viewAppSwitch.setIconAndName(-1, getString(R.string.item_app_switch));

        viewAbout = (PreferenceItemView) findViewById(R.id.view_about);
        viewAbout.setIconAndName(R.drawable.icon_guanyuwomen, getString(R.string.item_setting_about));

        btnLogout = (TextView) findViewById(R.id.btn_logout);
    }

    private void addListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedBack();
            }
        });

        viewTtsSwitch.setOnToggleChanged(new SwitchItemView.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                BocSettings.getInstance().setSetting(BocSettings.KEY_TTS_SWITH, on);
                TtsManager.getInstance(getApplicationContext()).stop();
            }
        });

        viewVoiceNaviByAiui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityJumper.startVoiceNaviByAiui(SettingActivity.this);
            }
        });

        viewVocalVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityJumper.startVocalVerifyActivity(SettingActivity.this);
                ActivityJumper.startVocalIdentifyActivity(SettingActivity.this);
            }
        });

        viewVocalRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = UserManager.getInstance().getUser();
                if (userEntity == null) {
                    ToastUtils.showLong(getApplicationContext(), R.string.public_tip_login_first);
                    return;
                }
                ActivityJumper.startVocalRegisterActivity(SettingActivity.this);
            }
        });

        viewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startAboutActivity(SettingActivity.this);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedLogout();
            }
        });

        viewAppSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAppSwitch();
            }
        });
    }

    public void onClickedBack() {
        finish();
    }

    public void onClickedLogout() {
        EventBus.getDefault().post(new EventLogout());
        finish();
    }

    private void handleAppSwitch() {
        String appId = BocSettings.getInstance().getString(BocSettings.APP_ID);
        if (StringUtil.isEmpty(appId)) {
            appId = "NL7P32Wz"; // 写死网标
        }
        final String[] appIds = getResources().getStringArray(R.array.app_ids);

        int selected = 0;
        for (int i = 0; i < appIds.length; ++i) {
            String item = appIds[i];
            if (StringUtil.equals(item, appId)) {
                selected = i;
                break;
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(R.array.app_names, selected, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String appId = appIds[which];
                        BocSettings.getInstance().setSetting(BocSettings.APP_ID, appId);
                        ApiConfig.setAppId(appId);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
