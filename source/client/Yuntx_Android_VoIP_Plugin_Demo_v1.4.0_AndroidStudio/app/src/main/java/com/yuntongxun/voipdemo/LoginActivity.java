package com.yuntongxun.voipdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.plugin.common.AppMgr;
import com.yuntongxun.plugin.common.ClientUser;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.ECPreferences;
import com.yuntongxun.plugin.common.common.utils.EasyPermissionsEx;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.common.common.utils.ToastUtil;
import com.yuntongxun.voipdemo.service.MyService;

public class LoginActivity extends AppCompatActivity {
    private EditText et_account;
    private Button btn_login, btn_start_service, btn_stop_service, btn_bind_service, btn_unbind_service;
    private ProgressBar proBar;
    private BroadcastReceiver mSDKNotifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SDKCoreHelper.isLoginSuccess(intent)) {
                String pushToken = ECPreferences.getSharedPreferences().getString("pushToken", null);
                LogUtil.d(TAG, "SDK connect Success ,reportToken:" + pushToken);
                if (!TextUtils.isEmpty(pushToken)) {
                    ECDevice.reportHuaWeiToken(pushToken);
                }
                Intent action = new Intent(LoginActivity.this, MainActivity.class);
                action.putExtra("userid", AppMgr.getUserId());
                startActivity(action);
                finish();

                String str1 = intent.toUri(Intent.URI_INTENT_SCHEME);
                String str2 = action.toUri(Intent.URI_INTENT_SCHEME);
                LogUtil.d(TAG, "uri1:" + str1 + ",uri2:" + str2);

            } else {
                int error = intent.getIntExtra("error", 0);
                if (error == SdkErrorCode.CONNECTING) return;
                LogUtil.e(TAG, "登入失败[" + error + "]");
                ToastUtil.showMessage("登入失败 == " + error);
                proBar.setVisibility(View.GONE);
            }
        }
    };
    private static final String TAG = MainActivity.class.getSimpleName();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.e(TAG, "onServiceConnected" + service + "==" + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("LoginActivity onCreate: " + getIntent().toUri(Intent.URI_INTENT_SCHEME));
        setContentView(R.layout.activity_login);
        proBar = (ProgressBar) findViewById(R.id.probar);
        et_account = (EditText) findViewById(R.id.et_account);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_start_service = (Button) findViewById(R.id.btn_start_service);
        btn_stop_service = (Button) findViewById(R.id.btn_stop_service);
        btn_bind_service = (Button) findViewById(R.id.btn_bind_service);
        btn_unbind_service = (Button) findViewById(R.id.btn_unbind_service);
        btn_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MyService.class);
                startService(intent);
            }
        });
        btn_stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(LoginActivity.this, MyService.class));
            }
        });
        btn_bind_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MyService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        btn_unbind_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_account.getText().toString();

                if (TextUtils.isEmpty(account)) {
                    return;
                }
                proBar.setVisibility(View.VISIBLE);
                ClientUser.UserBuilder builder = new ClientUser.UserBuilder(account, "张" + (account.length() > 2 ? account.substring(0, 2) : account));
                // 以下参数均为可选
                SDKCoreHelper.login(builder.build());
                //设置自为debug模式
                LogUtil.setDebugMode(true);
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SDKCoreHelper.ACTION_SDK_CONNECT);
        intentFilter.addAction(SDKCoreHelper.ACTION_LOGOUT);
        registerReceiver(mSDKNotifyReceiver, intentFilter);
        if (AppMgr.getClientUser() != null) {
            LogUtil.d(TAG, "SDK auto connect...");
            SDKCoreHelper.init(getApplicationContext());
            //设置自为debug模式
            LogUtil.setDebugMode(true);
        }
        initPermissions();

    }

    public static final String rationale = "需要访问存储设置、相机、麦克风、读取通讯录的权限";
    public static final String[] needPermissionsInit = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_PHONE_STATE};
    public static final int PERMISSIONS_REQUEST_INIT = 0x16;


    private void initPermissions() {
        if (EasyPermissionsEx.hasPermissions(this, needPermissionsInit)) {
            LogUtil.d("btnClicked: hasPermissions");
        } else {
            EasyPermissionsEx.requestPermissions(LoginActivity.this, rationale, PERMISSIONS_REQUEST_INIT, needPermissionsInit);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");
        unregisterReceiver(mSDKNotifyReceiver);
    }
}
