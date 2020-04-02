package com.example.miles.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miles.myapplication.Requests.GetApk;
import com.example.miles.myapplication.myAsync.HttpException;
import com.example.miles.myapplication.network.MyCallBack;
import com.example.miles.myapplication.utils.DESv;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button btn_hello;
    TextView tv_content;

    private Context mContext;

    private final static int TEST_CODE_1 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_hello = findViewById(R.id.btn_hello);
        tv_content = findViewById(R.id.tv_content);
        btn_hello.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        if (btn_hello.getId() == v.getId()) {
//            GetApk.GetInstance().getToApk(strUrl,cb);
            request(TEST_CODE_1);
            showProgressBubble(mContext);
        }
    }


    @Override
    public Object doInBackground(int requsetCode) {
        switch (requsetCode){
            case TEST_CODE_1:{
                return GetApk.GetInstance(mContext).getToApk();
            }
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode){
            case TEST_CODE_1:{
                final String str = (String) result;
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_content.setText(str);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideProgressBubble();
            }break;
        }
    }

    MyCallBack cb = new MyCallBack() {
        @Override
        public String parseNetworkResponse(Response response, int id) throws Exception {
            String str = response.body().string();
            try {
                final String s = DESv.decryptDESwithBase64(str, "salt");
                Log.i("adr 4.0 https",s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_content.setText(s);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };
}
