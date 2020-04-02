package com.example.miles.myapplication.network;

import com.example.miles.myapplication.utils.DESv;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用途：
 * 作者：Created by Miles Wang on 2018/12/25
 * 邮箱：icy-star@qq.com
 **/
public class MyCallBack extends Callback {

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        String result = DESv.decryptDESwithBase64(response.body().string(), "salt");
        return result;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(Object response, int id) {

    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total , int id)
    {

    }
}
