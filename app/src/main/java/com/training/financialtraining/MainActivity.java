package com.training.financialtraining;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebview;
    private NetworkChangeReceiver networkChangeReceiver;
    private LinearLayout noNetLayout;
    boolean loadWeb = false; //是否已经加载wenview,加载过一次就true
    boolean haveNet = true;  //是否有网,用于检测不到网络,2秒后默认有网加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        networkListen();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!loadWeb && haveNet){
                    loadWeb = true;
                    initWebView();
                }
            }
        },2000);
    }

    /**
     * 网络变化
     */
    private void networkListen() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    private void initView() {
        mWebview = (WebView) findViewById(R.id.webview);
        noNetLayout = (LinearLayout) findViewById(R.id.nonet);
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
                    } else {
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
        mWebview.loadUrl(Constants.HTML_URL);

    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                if (noNetLayout != null && !loadWeb) {//有网
                    noNetLayout.setVisibility(View.GONE);
                    initWebView();
                    loadWeb = true;
                    haveNet = true;
                }

            } else {
                if (noNetLayout != null && !loadWeb) {
                    noNetLayout.setVisibility(View.VISIBLE);
                    haveNet = false;
                }
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkChangeReceiver != null)
            unregisterReceiver(networkChangeReceiver);
    }
}
