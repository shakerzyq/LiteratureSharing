package com.example.literaturesharing9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.literaturesharing9.entity.Comment;
import com.example.literaturesharing9.entity.CommentShow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.HttpUtil;
import domain.WorkForFind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LmxCommentShowActivity extends AppCompatActivity {


    //连接后台查询评论的网址
    private String url="http://10.0.2.2:8081/comment/";
    private String url1="http://10.0.2.2:8081/comment/";

    private ListView listView;

    private String workid;
    List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();

    private HttpUtil httpUtil = new HttpUtil();

    private SimpleAdapter adapter;
    private Map<String,Object> map;

    private String writername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmx_activity_comment_show);
        Bundle bundle = this.getIntent().getExtras();
        workid = bundle.getString("workid");
        //writername = bundle.getString("writername");
        listView = findViewById(R.id.listview3);
        sendRequestWithOkHttp2(workid);

    }

    //将json转为对象
    private List<CommentShow> parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<CommentShow> appList = gson.fromJson(jsonData, new TypeToken<List<CommentShow>>() {}.getType());
        return appList;
    }
    private void sendRequestWithOkHttp2(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                url = url+type;
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                //adapter.notifyDataSetChanged();
                httpUtil.sendOkHttpRequest3(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData = response.body().string();
                        List<CommentShow> comments = parseJSONWithGSON(responseData);
                        //显示UI界面，调用的showResponse方法
                        showResponse(comments);
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

    private void showResponse(final List<CommentShow> response) {
        //在子线程中更新UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<response.size();i++){

                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("username",response.get(i).getUsername());
                    map.put("time",response.get(i).getTime());
                    map.put("comment",response.get(i).getContent());
                    listitem.add(map);
                }
                //创建适配器
                adapter = new SimpleAdapter(LmxCommentShowActivity.this, listitem, R.layout.lmx_listviewmodelforcomment
                        , new String[]{"username", "time","comment"}, new int[]{R.id.username, R.id.timecomment,R.id.commectcontent});

                listView.setAdapter(adapter);
                registerForContextMenu(listView);
            }


            // openContextMenu(listView);
            // listView.setAdapter(adapter1);
              /*  listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        registerForContextMenu(listView);
                        openContextMenu(listView);
                        return false;
                    }
                });*/


        });
    }

    @Override
    public void onBackPressed() {
        LmxCommentShowActivity.this.finish();
    }

}
