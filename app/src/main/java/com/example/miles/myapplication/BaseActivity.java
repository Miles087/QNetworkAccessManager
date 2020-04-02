package com.example.miles.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.miles.myapplication.common.CommonUtils;
import com.example.miles.myapplication.myAsync.AsyncManager;
import com.example.miles.myapplication.myAsync.HttpException;
import com.example.miles.myapplication.myAsync.OnDataListener;
import com.example.miles.myapplication.widget.LoadDialog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 用途：
 * 作者：Created by Miles Wang on 2018/12/25
 * 邮箱：icy-star@qq.com
 **/
public class BaseActivity extends FragmentActivity implements OnDataListener {

    private static LoadDialog loadDialog;
    private AsyncManager mAsyncTaskManager;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //初始化异步框架
        mAsyncTaskManager = AsyncManager.getInstance(mContext);
    }

    /**
     * 请求进度条
     */
    public void showProgressBubble(Context context){
        if(CommonUtils.isNetworkConnected(context)) {
            if (null==loadDialog) {
                synchronized (LoadDialog.class) {
                    if (null==loadDialog) {
                        loadDialog = new LoadDialog(mContext, true,mAsyncTaskManager);
                    }
                }
            }
            if(!loadDialog.isShowing()){
                loadDialog.show();
            }
        }
    }

    /**
     * 请求进度条
     */
    public void hideProgressBubble(){
        if(null!=loadDialog && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    /**
     * 发送请求（需要检查网络）
     *
     * @param requsetCode 请求码
     */
    public void request(int requsetCode) {
        mAsyncTaskManager.request(requsetCode, this);
    }

    /**
     * 发送请求
     *
     * @param requsetCode    请求码
     * @param isCheckNetwork 是否需检查网络，true检查，false不检查
     */
    public void request(int requsetCode, boolean isCheckNetwork) {
        mAsyncTaskManager.request(requsetCode, isCheckNetwork, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Object doInBackground(int requsetCode) throws Exception {
        return null;
    }

    @Override
    public boolean onIntercept(int requestCode, Object result) {
        return false;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        Toast.makeText(this,requestCode+"^^^"+state+"^^^"+result.toString(),Toast.LENGTH_SHORT).show();
    }


    //保存username
    public static void setUsername(Context context,String username) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("username", username).commit();
    }

    public static String getUsername(Context context){
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("username", "").toString();
    }

    public static void setUser_name(Context context,String username) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("user_name", username).commit();
    }

    public static String getUser_name(Context context){
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("user_name", "").toString();
    }

    //获取TOKEN
    public static String  getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("token", "").toString();
    }

    //保存TOKEN
    public static void setToken(Context context,String token) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("token", token).commit();
    }

    //获取UID
    public static String  getUID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("uid", "").toString();
    }
    //获取autoSta
    public static String  getAutoSta(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("autoSta", "");
    }
    //设置UID
    public static void setUID(Context context,String uid) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("uid", uid).commit();
    }

    //获取手势密码
    public static String  getGesture(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("gesture", "").toString();
    }

    //设置手势密码
    public static void setGesture(Context context,String gesture) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("gesture", gesture).commit();
    }

    //清除手势密码
    public static void clearGesture(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().remove("gesture").commit();
    }
    //设置手势密码状态
    public static void setISGesture(Context context,Boolean gestu) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("localGestureState", gestu).commit();
    }

    //设置真实姓名
    public static void setRealName (Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("realName", name).commit();
    }
    //获取真实姓名
    @NonNull
    public static String getRealName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("realName", "").toString();
    }
    //设置银行名
    public static void setBankName (Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("bankName", name).commit();
    }
    //获取银行名
    @NonNull
    public static String getBankName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("bankName", "").toString();
    }
    //设置银行号
    public static void setBankAccount (Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        preferences.edit().putString("bankAccount", name).commit();
    }
    //获取银行号
    @NonNull
    public static String getBankAccount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        return	preferences.getString("bankAccount", "").toString();
    }
}
