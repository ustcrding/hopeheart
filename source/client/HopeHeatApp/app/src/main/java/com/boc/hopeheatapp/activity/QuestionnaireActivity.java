package com.boc.hopeheatapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.model.ConsultEntity;
import com.boc.hopeheatapp.model.DoctorEntity;
import com.boc.hopeheatapp.model.EvaluationEntity;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.json.JsonUtils;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscriber;

/**
 * 问卷调查页面
 *
 * @author dwl
 * @date 2019/6/29.
 */
public class QuestionnaireActivity extends TitleColorActivity implements View.OnClickListener {
    private static final String TAG = "QuestionnaireActivity";
    private WebView mWebView;
    private String mUrl;
    private ProgressBar mProgressBar;
    private TextView mtvTitle;
    private View mBtnBack;
    private String mTitle;
    private boolean overrideInNewActivity = true;
    private String mStatus;
    private String mVictimId;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        parseIntent(getIntent());
        initUI();
        if (TextUtils.isEmpty(mUrl)) {
            Toast.makeText(this, R.string.url_invalid, Toast.LENGTH_SHORT).show();
        } else {
            mWebView.loadUrl(mUrl);
        }
    }

    private void initUI() {
        View backBtn = findViewById(R.id.top_goback_ibtn);
        backBtn.setVisibility(View.VISIBLE);
        //backBtn.setOnClickListener(this);

        mtvTitle = (TextView) findViewById(R.id.top_title_text);
        mtvTitle.setVisibility(View.VISIBLE);
        mBtnBack = (View) findViewById(R.id.top_back_container);
        mBtnBack.setOnClickListener(this);
        if (StringUtil.isNotBlank(mTitle)) {
            mtvTitle.setText(mTitle);
        } else {
            mtvTitle.setText(R.string.psychology_test);
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_webview);
        mWebView = (WebView) findViewById(R.id.webview_browser);

        initWebView(mWebView);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(WebView webView) {
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                //
                if (Logger.isDebugable()) {
                    Logger.d(TAG, "shouldOverrideUrlLoading | url = " + url);
                }
                if (overrideInNewActivity) {
                    ActivityJumper.startBrowserActivityWithTitle(QuestionnaireActivity.this, url, null, overrideInNewActivity);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgress(100);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress(0);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                showProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                if (null != mtvTitle && StringUtil.isEmpty(mTitle)) {
//                    int idx = title.lastIndexOf("/");
//                    if (idx != -1) {
//                        title = title.substring(idx + 1);
//                    }
//                    mtvTitle.setText(title);
//                }
            }
        });

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        //支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLoadWithOverviewMode(true);

        //扩大比例的缩放
        //settings.setUseWideViewPort(true);

        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

        webView.addJavascriptInterface(new WebAppInterface(QuestionnaireActivity.this), "angle");
    }

    private void showProgress(int progress) {
        if (progress == 0) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(0);
        } else if (progress == 100) {
            mProgressBar.setVisibility(View.GONE);
            mProgressBar.setProgress(0);
        } else {
            mProgressBar.setProgress(progress);
        }
    }

    private void parseIntent(Intent intent) {
        if (null == intent) {
            return;
        }

        mUrl = intent.getStringExtra(ActivityJumper.EXTRA_URL);
        mTitle = intent.getStringExtra(ActivityJumper.EXTRA_TITLE);
        overrideInNewActivity = intent.getBooleanExtra(ActivityJumper.EXTRA_OVERRIDE_IN_NEW_ACTICITY, true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back_container:
                onClickedBack();
                break;
            default:
                break;
        }
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();// 返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onClickedBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();// 返回上一页面
        } else {
            if (mWebView != null) {
                mWebView.destroy();
                mWebView = null;
            }

            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void onQuestionnaireFinished(String json) {
            final EvaluationEntity entity = JsonUtils.fromJson(json, EvaluationEntity.class);
            if (entity != null) {
                mStatus = "H";
                if (entity.getScores() < 53) {

                } else if (entity.getScores() < 63) {
                    mStatus = "U";
                } else if (entity.getScores() < 73) {
                    mStatus = "B";
                } else {
                    mStatus = "I";
                }

                final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                final SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
                final Date date = new Date();

                UserEntity user = UserManager.getInstance().getUser();
                if (user != null) {
                    mVictimId = user.getRoleId();
                    mAddress = "";
                    if (!TextUtils.isEmpty(user.getProvince())) {
                        mAddress += user.getProvince();
                    }
                    if (!TextUtils.isEmpty(user.getCity())) {
                        mAddress += user.getCity();
                    }
                }
                final UserLoader userService = new UserLoader();
                userService.uploadEvaluationResult(mVictimId, "1", mStatus, sdf.format(date), sdf2.format(date), mAddress).subscribe(new Subscriber<ConsultEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(final ConsultEntity consultEntity) {
                        userService.queryDoctor(mVictimId, "1", consultEntity != null ? consultEntity.getVictimtestId() : "1", mStatus, sdf.format(date), sdf2.format(date), mAddress).subscribe(new Subscriber<DoctorEntity>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onNext(DoctorEntity doctorEntity) {
                                if (consultEntity != null) {
                                    BocSettings.getInstance().setSetting(BocSettings.VICTIM_TEST_ID, consultEntity.getVictimtestId());
                                }
                                if (doctorEntity != null) {
                                    BocSettings.getInstance().setSetting(BocSettings.DOCTOR_ID, doctorEntity.getDoctorId());
                                    BocSettings.getInstance().setSetting(BocSettings.DOCTOR_PHONE, doctorEntity.getDoctorPhone());
                                }
                                gotoResult(entity.getResult(), entity.getScores());
                            }
                        });
                    }
                });


            }
        }
    }

    private void gotoResult(String result, int score) {
        ActivityJumper.startEvaluationResultActivity(QuestionnaireActivity.this, result, score);
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        finish();
    }
}
