package com.boc.hopeheatapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.VersionEntity;
import com.boc.hopeheatapp.update.DownloadLoader;
import com.boc.hopeheatapp.update.FileCallBack;
import com.boc.hopeheatapp.util.log.Logger;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * @author dwl
 * @date 2018/1/23.
 */
public class UpdateDialog extends Activity {

    private final String TAG = "UpdateDialog";
    private TextView tvUpdateTitle;
    private TextView tvUpdateContent;
    private TextView btnCancel;
    private TextView btnOk;
    private TextView btnQuit;
    private LinearLayout toolbarContainer;
    private ProgressBar progressBar;

    private VersionEntity versionEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_dialog);

        initView();
        addListener();

        versionEntity = getIntent().getParcelableExtra(ActivityJumper.EXTRA_UPDATE_ENTITY);

        if (versionEntity == null) {
            finish();
            return;
        }

        initDate();
    }

    private void initView() {
        tvUpdateTitle = (TextView) findViewById(R.id.tv_update_title);
        tvUpdateContent = (TextView) findViewById(R.id.tv_update_content);
        btnCancel = (TextView) findViewById(R.id.btn_cancel);
        btnOk = (TextView) findViewById(R.id.btn_sure);
        btnQuit = (TextView) findViewById(R.id.btn_quit);

        toolbarContainer = (LinearLayout) findViewById(R.id.ll_toolbar_container);
        progressBar = (ProgressBar) findViewById(R.id.update_progress);
    }

    private void addListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedCancle();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedOk();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedQuit();
            }
        });
    }

    private void onClickedQuit() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private void onClickedOk() {
        DownloadLoader loader = new DownloadLoader();

        String destFileDir = getExternalCacheDir().getPath();
        String destFileName = "BOCROBOT_"+versionEntity.getVersionCode()+".apk";

        Logger.d(TAG, "download dir=" + destFileDir + " file=" + destFileName);

        FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(destFileDir,destFileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
                //Toast.makeText(App.getInstance(),"下载文件成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void progress(long progress, long total) {
                Logger.d(TAG, "progress progress=" + progress + " total=" + total);
                setProgressBarProgress((int)((progress*100)/total));
            }

            @Override
            public void onStart() {
                Logger.d(TAG, "download started");
                showProgressBar();
            }

            @Override
            public void onCompleted() {
                Logger.d(TAG, "download completed");
                installApk(this.getDestFileDir()+"/"+this.getDestFileName());
                finish();
            }

            @Override
            public void onError(Throwable e) {
                //TODO: 对异常的一些处理
                e.printStackTrace();
            }
        };

        loader.download(versionEntity.getVersionUrl(), callBack);
    }

    private void onClickedCancle() {
        finish();
    }

    private void initDate() {
        tvUpdateTitle.setText("发现新版本！");
        tvUpdateContent.setText(versionEntity.getVersionComment());

        if (versionEntity.isForceUpdate()) {
            btnQuit.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
        }
    }

    /**
     * 显示进度条
     */
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);

        tvUpdateTitle.setText(R.string.update_downloading);
        toolbarContainer.setVisibility(View.GONE);
    }

    /**
     * 设置下载进度条进度
     *
     * @param progress
     */
    private void setProgressBarProgress(int progress) {
        progressBar.setProgress(progress);
    }

    /**
     * 安装APK文件
     */
    private void installApk(String filepath) {
        File apkfile = new File(filepath);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        startActivity(i);
    }
}
