package com.training.financialtraining.net;

import android.content.Context;
import android.os.UserManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shxioyang on 2018/8/23.
 */

public class OkHttp {
    private static final String TAG = "okhttp";

    public void get() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });


    }

    public void post() {
        //    请求条件：platform=2&gifttype=2&compare=60841c5b7c69a1bbb3f06536ed685a48
        //    请求参数：page=1&code=news&pageSize=20&parentid=0&type=1
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBodyPost)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });


    }
}
