package com.example.literaturesharing9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import Utils.HttpUtil;
import Utils.JsonAndObject;
import domain.PwdProtect;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ZyqRegiste4Activity extends AppCompatActivity {

    private String account;
    private String question1;
    private String question2;
    private String responseData="";
    private RequestBody requestBody;
    private String url="http://10.0.2.2:8081/pwdprotect";
    boolean result1=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zyq_registe4);

        Bundle bundle = this.getIntent().getExtras();
        account = bundle.getString("account");

        RadioGroup radioGroup1 = findViewById(R.id.que1rgd);
        RadioGroup radioGroup2 = findViewById(R.id.que2rgd);

        final EditText editText1 = findViewById(R.id.answer1);
        final EditText editText2 = findViewById(R.id.answer2);

        Button button = findViewById(R.id.pwdbt);

        //分别对两个groupradio设置监听
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.que1radio1){
                    RadioButton radioButton1 = findViewById(R.id.que1radio1);
                    question1=radioButton1.getText().toString();
                }else if(checkedId == R.id.que1radio2){
                    RadioButton radioButton2 = findViewById(R.id.que1radio2);
                    question1=radioButton2.getText().toString();
                }else {
                    RadioButton radioButton3 = findViewById(R.id.que1radio3);
                    question1 = radioButton3.getText().toString();
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.que2radio1){
                    RadioButton radioButton1 = findViewById(R.id.que2radio1);
                    question2=radioButton1.getText().toString();
                }else if(checkedId == R.id.que2radio2){
                    RadioButton radioButton2 = findViewById(R.id.que2radio2);
                    question2=radioButton2.getText().toString();
                }else {
                    RadioButton radioButton3 = findViewById(R.id.que2radio3);
                    question2 = radioButton3.getText().toString();
                }
            }
        });

        //对提交按钮设置监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText1.getText().equals("")&&!editText2.getText().equals("")){
                    PwdProtect pwdProtect = new PwdProtect(question1,editText1.getText().toString(),question2,editText2.getText().toString(),account);

                    requestBody = JsonAndObject.toJson(pwdProtect);
                    getResult();
                }
                if (result1){
                    Toast.makeText(ZyqRegiste4Activity.this,"提交密保成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ZyqRegiste4Activity.this, ZyqLoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ZyqRegiste4Activity.this,"抱歉!系统异常,提交失败!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getResult() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest1(url,requestBody,new Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData=response.body().string();
                        if(responseData!=null){
                            result1=true;
                        }else
                            result1=false;
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }
}
