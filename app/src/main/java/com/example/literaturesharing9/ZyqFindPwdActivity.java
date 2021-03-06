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
import okhttp3.Call;
import okhttp3.Response;


/**
 * 核实账号
 */
public class ZyqFindPwdActivity extends AppCompatActivity {

    private String account=null;
    private String responseData="";
    public  static int i=0;
    private TextView textView;
    private String url="http://10.0.2.2:8081/findpwd/";
    private String url1="http://10.0.2.2:8081/findpwd/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_find_pwd);

        final EditText editText = findViewById(R.id.userid);
        Button button = findViewById(R.id.userid_b);

        textView = findViewById(R.id.toast1);

        //核实账号的监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=editText.getText().toString();

                HttpUtil httpUtil = new HttpUtil();
                User user = new User(account);
                if (account.length()==11) {
                    if (!user.getUserid().equals("")) {
                        url=url+user.getUserid();
                        httpUtil.sendOkHttpRequest3(url,
                                new okhttp3.Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        responseData = response.body().string();
                                        if (responseData.length()>0) {
                                            Intent intent = new Intent(ZyqFindPwdActivity.this, ZyqFindPwdEditActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("responseData",responseData);
                                            bundle.putString("account", account);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }else{
                                            showToastInThread(ZyqFindPwdActivity.this, "该账号不存在");
                                        }
                                    }
                                });
                    }
                }else{
                    textView.setText("请输入电话号码");
                }
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
