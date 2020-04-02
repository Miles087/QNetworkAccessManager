package com.example.miles.myapplication.Requests;

import android.content.Context;

import com.example.miles.myapplication.network.Constants;
import com.example.miles.myapplication.utils.DESv;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用途：
 * 作者：Created by Miles Wang on 2018/12/25
 * 邮箱：icy-star@qq.com
 **/
public class GetApk extends BaseRequest {

    private static GetApk instance;

    public GetApk(){

    }

    /**
     * 单例 饿汉？
     * @return
     */
    public static GetApk GetInstance(Context context){
        mContext = context;
        if (null == instance) {
            synchronized (GetApk.class){
                if (null == instance) {
                    instance = new GetApk();
                }
            }
        }
        return instance;
    }

    /**
     * 获取APP基本信息
     * @return
     */
    public String getToApk(){
        postInit();
        String result = "error";
        try {
            result = getResult(Constants.APP_INDEX);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            result = "地址不对劲";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
