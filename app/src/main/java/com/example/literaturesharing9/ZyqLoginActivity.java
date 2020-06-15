package com.example.literaturesharing9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.literaturesharing9.UserMain.drawer;
import com.google.gson.JsonObject;

import java.io.IOException;

import Utils.HttpUtil;
import Utils.JsonAndObject;
import domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
*create by 周杨清 on 2020/6/7
*介绍: 登录的activity
 */
public class ZyqLoginActivity extends AppCompatActivity {

    private RequestBody requestBody;
    private String userid;
    private String password;
    boolean result1=true;
    private EditText editText;
    private EditText editText1;
    private Button button;

    private String url="http://10.0.2.2:8081/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.zhuche);
        TextView textView1 = findViewById(R.id.forgrt);
        editText = findViewById(R.id.yonghu);
        editText1 = findViewById(R.id.password);
        Intent intent=new Intent(ZyqLoginActivity.this, drawer.class);
        startActivity(intent);
       /* Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);*/

       /* editText.addTextChangedListener(textWatcher);
        editText1.addTextChangedListener(textWatcher);

        button = findViewById(R.id.denglu);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZyqLoginActivity.this, ZyqRegister1Activity.class);
                startActivity(intent);
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZyqLoginActivity.this,ZyqFindPwdActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = editText.getText().toString();
                password = editText1.getText().toString();
                if(editText.getText().toString().length()!=11){
                    Toast.makeText(ZyqLoginActivity.this,"账号格式错误",Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User(userid,password);
                    requestBody = JsonAndObject.toJson(user);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtil.sendOkHttpRequest1(url,requestBody,new Callback(){
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                        String responseData=response.body().string();
                                    if(!responseData.equals("")){
                                        User user = JsonAndObject.toUser(responseData);
                                        if(user.getUserstatus()==0){
                                            Intent intent = new Intent(ZyqLoginActivity.this,ZyqTestActivity.class);
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putString("userid",userid);
                                            intent.putExtras(bundle1);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            showToastInThread(ZyqLoginActivity.this, "该账号已被封禁");
                                        }
                                    }else{
                                        showToastInThread(ZyqLoginActivity.this, "账号或密码错误");
                                    }

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
        });*/
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

    /*
          使用Gson解析response的JSON数据
          本来总共是有三步的，一、二步在方法调用之前执行了
        */
    private String getStatus(JsonObject responseBodyJSONObject) {
        /* 使用Gson解析response的JSON数据的第三步
           通过JSON对象获取对应的属性值 */
        String status = responseBodyJSONObject.get("status").getAsString();
        // 登录成功返回的json为{ "status":"success", "data":null }
        // 只获取status即可，data为null
        return status;
    }

    /**
    *create by 周杨清 on 2020/6/7
    *介绍: 监听编辑框的状态,全都输入方可点击按钮,否则无响应
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //如果编辑框长度大于零就是可用状态
            if(editText.length()>0&&editText1.length()>0){
                button.setEnabled(true);

            }else{
                button.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
