package com.training.financialtraining;

import android.app.Application;

import com.igexin.sdk.PushManager;
import com.training.financialtraining.push.PushService;
import com.training.financialtraining.push.ReceiveIntentService;

/**
 * Created by shxioyang on 2018/8/23.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initPush();
    }
    private void initPush() {
        // 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        // 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), ReceiveIntentService.class);
    }
}
