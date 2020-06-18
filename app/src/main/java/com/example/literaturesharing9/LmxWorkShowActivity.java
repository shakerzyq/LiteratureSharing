package com.example.literaturesharing9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.literaturesharing9.entity.Comment;
import com.example.literaturesharing9.entity.Work;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Utils.HttpUtil;
import Utils.JsonAndObject;
import domain.User;
import domain.WorkForFind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LmxWorkShowActivity extends AppCompatActivity {

    private String workid;
    private String userid;
    private String username;

    private String pinglun;

    private String date;
    private RequestBody requestBody;

    private String url2="http://10.0.2.2:8081/commitConmment";
    private String url3="http://10.0.2.2:8081/commitConmment";

    private String url="http://10.0.2.2:8081/dianzan";
    private String url1="http://10.0.2.2:8081/dianzhan";

    private String url4="http://10.0.2.2:8081/work/";
    private String url4_="http://10.0.2.2:8081/work/";

    private HttpUtil httpUtil = new HttpUtil();


    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private TextView likenumview;
    private ImageView likeview;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);



        Bundle bundle = this.getIntent().getExtras();
        userid = bundle.getString("userid");
        workid=bundle.getString("workid");
        String workcontent=bundle.getString("workcontent");
        String worktype=bundle.getString("worktype");
        String worktime=bundle.getString("worktime");
        int workzan=bundle.getInt("workzan");
        String workname = bundle.getString("workname");

        /*workname = bundle.getString("workname");*/
        username = bundle.getString("writername");

        TextView titleview = findViewById(R.id.workname1);
        TextView writernameview = findViewById(R.id.writername1);
        likenumview = findViewById(R.id.likenum);
        likeview = findViewById(R.id.like);
        TextView typeview = findViewById(R.id.type);
        TextView timeview = findViewById(R.id.createtime);
        TextView contentview = findViewById(R.id.contentwork);
        editText = findViewById(R.id.comment);
        ImageView showcomment = findViewById(R.id.pinglun);

        titleview.setText(workname);
        writernameview.setText(username);
        likenumview.setText(workzan);
        typeview.setText(worktype);
        timeview.setText(worktime);
        contentview.setText(workcontent);





       //sendRequestWithOkHttp2(workid);

        //点赞监听器
        likeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  num = likenumview.getText().toString();
                int n = Integer.parseInt(num)+1;
                likenumview.setText(n);
                likeview.setImageResource(R.drawable.zan5);
                sendRequestWithOkHttp(n);

            }
        });

        //写评论
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinglun=editText.getText().toString();
                date =df.format(new Date());

                if (pinglun!=null){
                    Comment comment = new Comment(workid,userid,pinglun,"",date,0);
                    requestBody = JsonAndObject.toJson(comment);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtil.sendOkHttpRequest1(url2,requestBody,new Callback(){
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseData=response.body().string();
                                    if(!responseData.equals("")){
                                        User user = JsonAndObject.toUser(responseData);
                                        showToastInThread(LmxWorkShowActivity.this, "评论成功");
                                    }else{
                                        showToastInThread(LmxWorkShowActivity.this, "评论失败");
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
                    Toast.makeText(LmxWorkShowActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //跳转到显示评论界面
        showcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LmxWorkShowActivity.this,LmxCommentShowActivity.class);
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

    //用于点赞
    private void sendRequestWithOkHttp(final int n) {
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

   /* private void sendRequestWithOkHttp2(final String workid) {
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
                        Work work = parseJSONWithGSON(responseData);
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
*/
    //将json转为对象
    private Work parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        Work appList = gson.fromJson(jsonData, new TypeToken<Work>() {}.getType());
        return appList;
    }

    /*public void showResponse(final Work work){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleview.setText(work.getWorkName());
                writernameview.setText(username);
                typeview.setText(work.getType());
                timeview.setText(work.getWorkTime());
                contentview.setText(work.getWorkContent());
                likenumview.setText(work.getWorkZan());
            }
        });
    }*/
}
