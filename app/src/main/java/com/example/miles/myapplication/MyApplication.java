package com.example.miles.myapplication;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 用途：
 * 作者：Created by Miles Wang on 2018/12/25
 * 邮箱：icy-star@qq.com
 **/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initOkHttpUtils();
    }

    /**
     * 初始化OkHttpUtils
     */
    private void initOkHttpUtils(){
        InputStream is = null;
        try {
            is = getAssets().open("https.cer");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{is}, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
