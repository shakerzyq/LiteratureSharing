package com.example.literaturesharing9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.literaturesharing9.entity.Comment;
import com.example.literaturesharing9.entity.Work;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Utils.HttpUtil;
import Utils.JsonAndObject;
import cz.msebera.android.httpclient.client.cache.Resource;
import domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LmxShowWorkActivity extends AppCompatActivity {



    private String url4="http://10.0.2.2:8081/work/";
    private String url4_="http://10.0.2.2:8081/work/";

    private String url="http://10.0.2.2:8081/dianzan/";
    private String url1="http://10.0.2.2:8081/dianzhan";

    private String url2="http://10.0.2.2:8081/commitConmment";
    private String url3="http://10.0.2.2:8081/commitConmment";
    private HttpUtil httpUtil = new HttpUtil();

    private TextView likenumview;

    private EditText editText;

    private String workid;
    private String userid;
    private String writername;
    private String workcontent;
    private String worktype;
    private String worktime;
    private int workzan;
    private String workname;
    private Work work;
    private String pinglun;
    private int n;
    private int i=0;//用于点赞记录


    private TextView lmxtitle;
    private TextView  lmxname;
    private TextView lmxtype;
    private TextView lmxtime;
    private TextView lmxcontent;
    private TextView lmxzan;
    private String date;
    private RequestBody requestBody;
    private TextView likeview;
    private Button commentbutton;


    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmx_activity_show_work);


        Bundle bundle = this.getIntent().getExtras();
        userid = bundle.getString("userid");
        String writerid = bundle.getString("writerid'");
        workid=bundle.getString("workid");
        String workname = bundle.getString("workname");
        writername = bundle.getString("writername");


        //声明控件
        lmxtitle = findViewById(R.id.lmxtitle);
        lmxname = findViewById(R.id.lmxname);
        lmxtype = findViewById(R.id.lmxtype);
        lmxtime = findViewById(R.id.lmxcreatetime);
        lmxcontent = findViewById(R.id.lmxcontentwork);
        lmxzan = findViewById(R.id.lmxzan);
        ImageView showcomment = findViewById(R.id.pinglun);
        likeview=findViewById(R.id.lmxlike);
        editText=findViewById(R.id.comment);
        commentbutton=findViewById(R.id.commentbutton);

        //初始搜索信息
        sendRequestWithOkHttp2(workid);

        //点赞监听器
        likeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i==1){
                    Toast.makeText(LmxShowWorkActivity.this,"只能点一次赞",Toast.LENGTH_SHORT).show();
                    i++;
                }
                else if (i==0){
                    String  num = lmxzan.getText().toString();
                    n = Integer.parseInt(num)+1;
                    lmxzan.setText(String.valueOf(n));
                    likeview.setTextColor(Color.parseColor("#EA0909"));
                    sendRequestWithOkHttp();
                    Toast.makeText(LmxShowWorkActivity.this,"点赞成功",Toast.LENGTH_SHORT).show();
                    i++;
                }
            }
        });

        //写评论
        commentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinglun=editText.getText().toString();
                date =df.format(new Date());

                if (!pinglun.equals("")){
                    Comment comment = new Comment(workid,userid,pinglun,"无",date,0);
                    requestBody = JsonAndObject.toJson(comment);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtil.sendOkHttpRequest1(url2,requestBody,new Callback(){
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseData=response.body().string();
                                    if(!responseData.equals("")){
                                        setEditTextNull();
                                        showToastInThread(LmxShowWorkActivity.this, "评论成功");
                                    }else{
                                        showToastInThread(LmxShowWorkActivity.this, "评论失败");
                                    }
                                }
                                @Override
                                public void onFailure(Call call,IOException e){
                                    //在这里进行异常情况处理
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(LmxShowWorkActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //跳转到显示评论界面
        showcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LmxShowWorkActivity.this,LmxCommentShowActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("workid",workid);
                intent.putExtras(bundle1);
                startActivity(intent);
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
    //实现在子线程中改变字体颜色
    private void setEditTextNull() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText("");
            }
        });
    }

    //内容显示的okhttp方法
    private void sendRequestWithOkHttp2(final String workid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                url4 = url4+workid;
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                //adapter.notifyDataSetChanged();
                httpUtil.sendOkHttpRequest3(url4, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData = response.body().string();
                        //work = parseJSONWithGSON(responseData);
                        work = JsonAndObject.toWork(responseData);
                        //显示UI界面，调用的showResponse方法
                        showResponse(work);
                        url4=url4_;
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }

    //点赞的okhttp方法
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                url = url+n+","+workid;
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                httpUtil.sendOkHttpRequest3(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData = response.body().string();
                        //显示UI界面，调用的showResponse方法
                        url=url1;
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }

    //将json转为对象
    private Work parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        Work appList = gson.fromJson(jsonData, new TypeToken<Work>() {}.getType());
        return appList;
    }

    //将搜索出的内容显示到控件上
    public void showResponse(final Work work){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lmxtitle.setText(work.getWorkName());
                lmxname.setText(writername);
                lmxtype.setText(work.getType());
                lmxtime.setText(work.getWorkTime());
                lmxcontent.setText(work.getWorkContent());
                lmxzan.setText(String.valueOf(work.getWorkZan()));
            }
        });
    }

    @Override
    public void onBackPressed() {
       LmxShowWorkActivity.this.finish();
    }
}
