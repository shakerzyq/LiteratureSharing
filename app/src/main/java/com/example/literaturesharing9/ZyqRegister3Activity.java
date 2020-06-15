package com.example.literaturesharing9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import Utils.HttpUtil;
import Utils.JsonAndObject;
import domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ZyqRegister3Activity extends AppCompatActivity {

    private String url="http://10.0.2.2:8081/user";
    private String sex=null;//设置所选的性别
    private String responseData="";
    private RequestBody requestBody;
    private String account;
    private String password;
    boolean result1=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        Bundle bundle = this.getIntent().getExtras();
        account = bundle.getString("account");
        password = bundle.getString("password");

        final EditText inputname = findViewById(R.id.inputname);
        final RadioGroup setsex = findViewById(R.id.rgd);
        final EditText inputbirth = findViewById(R.id.inputbirth);
        final EditText inputidea = findViewById(R.id.inputidea);



        Button button = findViewById(R.id.regbt);

        //设置radiobutton的监听器
        setsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio1){
                    sex="男";
                }else{
                    sex="女";
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputname.getText().equals("")&&!inputbirth.getText().equals("")&&!sex.equals("")){
                    User user = new User(account,inputname.getText().toString(),password,
                            inputbirth.getText().toString(),sex,inputidea.getText().toString()
                            );
                    requestBody = JsonAndObject.toJson(user);
                    getResult();
                    if (result1){
                        Toast.makeText(ZyqRegister3Activity.this,"注册成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ZyqRegister3Activity.this,ZyqRegiste4Activity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("account",account);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(ZyqRegister3Activity.this,"抱歉!系统异常,注册失败!",Toast.LENGTH_LONG).show();
                    }



                }else{
                    Toast.makeText(ZyqRegister3Activity.this,"必填的内容不能为空",Toast.LENGTH_SHORT).show();
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
