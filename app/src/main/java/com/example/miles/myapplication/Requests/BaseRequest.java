package com.example.miles.myapplication.Requests;

import android.content.Context;

import com.example.miles.myapplication.BaseActivity;
import com.example.miles.myapplication.utils.DESv;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.IOException;

import okhttp3.Response;

/**
 * 用途：
 * 作者：Created by Miles Wang on 2018/12/26
 * 邮箱：icy-star@qq.com
 **/
public class BaseRequest {

    protected static Context mContext;
    protected PostFormBuilder call;
    protected MyRequestParameters myRequestParameters;
    protected String finalParam;

    /**
     * 初始化PostFormBuilder   post请求
     * 写入基本参数
     */
    protected void postInit(){
        myRequestParameters = new MyRequestParameters();
        call = OkHttpUtils.post();
        call.addParams("app","adr");
    }

    /**
     * 获取处理后的RequestParams对象
     * @param params
     * @return
     */
    public String getFinalParams(MyRequestParameters params){
        //TODO 这里处理公共参数，签名等操作
        MyRequestParameters params1 = new MyRequestParameters();
        try {
            finalParam= DESv.encryptDESwithBase64(params.MaptoJSONStringtest(), "salt");//加密处理
        }catch (Exception e){

        }
        return finalParam;
    }

    /**
     * 添加参数
     * @param key
     * @param value
     */
    protected void setParameter(String key,String value){
        myRequestParameters.put(key,value);
    }

    /**
     * 执行同步请求
     * @return
     * @throws IOException
     */
    protected String getResult() throws IOException {
        return getResult();
    }

    /**
     * 获取结果
     * @param url
     * @return
     * @throws IOException
     */
    protected String getResult(String url) throws IOException {
        getFinalParams(myRequestParameters);
        call.addParams("param",finalParam);
        Response result = call.url(url)
                .build()
                .execute();
        return decode(result.body().string());
    }

    /**
     * 解密结果
     * @param str
     * @return
     */
    protected String decode(String str){
        try {
            return DESv.decryptDESwithBase64(str, "salt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "解析失败";
    }
}
