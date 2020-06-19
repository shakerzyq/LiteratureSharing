package com.example.literaturesharing9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import Utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZyqUserNumActivity extends AppCompatActivity {

    private HttpUtil httpUtil = new HttpUtil();
    private String url="http://10.0.2.2:8081/usernum";
    private TextView textView;

    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_user_num);

        Bundle bundle = this.getIntent().getExtras();
        account = bundle.getString("account");
        password = bundle.getString("password");

        textView = findViewById(R.id.numUser);
        Button button = findViewById(R.id.numbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZyqUserNumActivity.this, ZyqLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sendRequestWithOkHttp();
    }

    //统计用户数
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                httpUtil.sendOkHttpRequest3(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData = response.body().string();
                        //显示UI界面，调用的showResponse方法
                        setEditTextNull(responseData);
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }

    //实现在子线程中改变text
    private void setEditTextNull(final String responseData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(responseData);
            }
        });
    }
}
