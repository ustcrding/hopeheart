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
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;

import org.json.JSONObject;

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
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onClickedBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回上一页面
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWebView.onResume();
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
            Logger.d(TAG, "onQuestionnaireFinished | " + json);
           Toast.makeText(mContext, json.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
