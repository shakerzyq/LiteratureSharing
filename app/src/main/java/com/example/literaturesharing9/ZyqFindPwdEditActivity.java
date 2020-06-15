package com.example.literaturesharing9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import Utils.*;
import domain.PwdProtect;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ZyqFindPwdEditActivity extends AppCompatActivity {

    private String responseData;
    private RequestBody requestBody;
    private String url="http://10.0.2.2:8081/findpwd";
    boolean result1=true;
    private String url1="http://10.0.2.2:8081/user/";
    private String url2="http://10.0.2.2:8081/user/";
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_find_pwd_edit);

        Bundle bundle = this.getIntent().getExtras();
        account = bundle.getString("account");

        responseData = bundle.getString("responseData");

        TextView question1 = findViewById(R.id.question1);
        TextView question2 = findViewById(R.id.question2);

        final EditText answer1 = findViewById(R.id.answer_1);
        final EditText answer2 = findViewById(R.id.answer_2);

        Button button = findViewById(R.id.findpwd);
        //ArticleForFind appList = gson.fromJson(jsonData, new TypeToken<List<ArticleForFind>>() {}.getType());
        //Gson gson = new Gson();
       // PwdProtect  pwdProtect = gson.fromJson(responseData, new TypeToken<PwdProtect>() {}.getType());
        final PwdProtect pwdProtect = JsonAndObject.toPwdProtect(responseData);

        //将问题内容复制给两个textview
        question1.setText(pwdProtect.getQuestion1());
        question2.setText(pwdProtect.getQuestion2());

        //将答案输入与后台比对

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil httpUtil = new HttpUtil();
                if(!answer1.getText().equals("")&&!answer2.getText().equals("")){
                    if(answer1.getText().toString().equals(pwdProtect.getAnswer1())&&answer2.getText().toString().equals(pwdProtect.getAnswer2())){
                        url1=url1+account;
                        httpUtil.sendOkHttpRequest3(url1,
                                new okhttp3.Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        responseData = response.body().string();
                                        if (!responseData.equals("")) {
                                            Intent intent = new Intent(ZyqFindPwdEditActivity.this, ZyqFindPwdShowActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("responseData", responseData);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }else{
                        url1=url2;
                        Toast.makeText(ZyqFindPwdEditActivity.this,"回答错误",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(ZyqFindPwdEditActivity.this,"输入的答案不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
