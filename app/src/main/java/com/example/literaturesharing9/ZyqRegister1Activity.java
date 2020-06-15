package com.example.literaturesharing9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import Utils.HttpUtil;
import domain.User;
import okhttp3.*;
import okhttp3.Response;

public class ZyqRegister1Activity extends AppCompatActivity {

    private String account=null;
    private String responseData;
    public  static int i=0;
    private TextView textView;
    private String url="http://10.0.2.2:8081/user/";
    private String url1="http://10.0.2.2:8081/user/";
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        final EditText editText = findViewById(R.id.account);
        Button button = findViewById(R.id.account_b);

        textView = findViewById(R.id.toast);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=editText.getText().toString();


                User user = new User(account);
                if (account.length()==11) {


                    if (!user.getUserid().equals("")) {
                        url=url+user.getUserid();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtil httpUtil = new HttpUtil();
                                httpUtil.sendOkHttpRequest3(url, new okhttp3.Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        responseData = response.body().string();
                                        if (responseData.equals("")) {
                                            Intent intent = new Intent(ZyqRegister1Activity.this, ZyqRegister2Activity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("account", account);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            showToastInThread(ZyqRegister1Activity.this,"该账号已存在");
                                            responseData=new String();
                                            url=url1;
                                        }
                                    }
                                });
                            }
                        }).start();
                    }
                }else{
                    Toast.makeText(ZyqRegister1Activity.this,"请输入11位的电话号码",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // 实现在子线程中插入textview
    private void InsertTextInThread(final Context context, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    // 实现在子线程中显示Toast
    private void showToastInThread(final Context context, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
