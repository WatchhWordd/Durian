package com.durian.demo.presentation.webview;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.durian.demo.R;
import com.durian.demo.base.utils.NetManager;

/**
 * @author zhangyb
 * @description
 * @date 2017/12/25
 */

public class WebActivity extends AppCompatActivity {

    public static final String LOAD_URL = "load_url";
    private TextView titleView;
    private ImageView backView;
    private ImageView shareView;
    private ProgressBar progressBar;
    private WebView webView;
    private String postUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initData();
        initView();
        showWebView();
    }

    private void initData() {
        postUrl = getIntent().getStringExtra(LOAD_URL);
    }

    private void initView() {
        initTab();
        initWebView();
    }

    private void initTab() {
        titleView = (TextView) findViewById(R.id.title_msg);
        backView = (ImageView) findViewById(R.id.back_icon);
        shareView = (ImageView) findViewById(R.id.share_icon);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        backView.setOnClickListener(view -> goBack());
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(this.getCacheDir().getAbsolutePath());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (NetManager.getInstance(this).getNetworkState() == NetManager.NETWORK_NONE) {
            // 根据cache-control决定是否从网络上取数据
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 没网，离线加载，优先加载缓存(即使已经过期)
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new SimpleWebViewClient());
        webView.setWebChromeClient(new SimpleWebChromeClient());
    }

    private void showWebView() {
        webView.loadUrl(postUrl);
    }

    /**
     * 更新页面title
     */
    private void updateTitle() {
        String webTitle = webView.getTitle();
        titleView.setText(webTitle);
    }

    private void updateProgress(int progress) {
        if (progress >= 100) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress);
        }
    }

    private void goBack(){
        if (null != webView && webView.canGoBack()) {
            webView.goBack();//返回上个页面
        } else {
            finish();
        }
    }
    private class SimpleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            updateProgress(5);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            updateTitle();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    private class SimpleWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            updateProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            updateTitle();
        }

    }

}
