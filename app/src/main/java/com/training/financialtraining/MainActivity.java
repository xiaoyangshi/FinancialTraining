package com.training.financialtraining;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
    }

    private void initView() {
        mWebview = (WebView) findViewById(R.id.webview);
    }

    private void initWebView() {
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) {
                    return false;
                }
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    }
                    else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }

            }
        });
        mWebview.setOverScrollMode(View.OVER_SCROLL_NEVER);

        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        // 开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setAllowFileAccessFromFileURLs(true);
//            mWebview.loadUrl();
        mWebview.loadUrl("http://baidu.com");

    }

    @Override
    public void onBackPressed() {
        boolean b = mWebview.canGoBack();
        if (b) {
            mWebview.goBack();
        } else {
            exit();
        }
    }

    long exitTime;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mWebview != null) {
                mWebview.onResume();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mWebview != null) {
                mWebview.onPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
