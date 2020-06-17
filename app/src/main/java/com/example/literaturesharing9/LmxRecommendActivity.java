package com.example.literaturesharing9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.literaturesharing9.entity.Work;
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

public class LmxRecommendActivity extends AppCompatActivity {

    private SimpleAdapter adapter;
    private static String WorkTag = "RecommendActivity";
    TextView show1_name;
    TextView show1_content;
    TextView show2_name;
    TextView show2_content;
    TextView show3_name;
    TextView show3_content;
    private String userid;
    private ListView listView;

    private String url="http://10.0.2.2:8081/push";
    private String url1="http://10.0.2.2:8081/push";

    private String url2="http://10.0.2.2:8081/FindWorks/";
    private String url3="http://10.0.2.2:8081/FindWorks/";


    private ArrayList<Work> work_list = new ArrayList<Work>();
    private HttpUtil httpUtil = new HttpUtil();


    List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();


    private Map<String,Object> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        //接收传递过来的userid
        Bundle bundle = this.getIntent().getExtras();
        userid = bundle.getString("userid");


        listView = findViewById(R.id.listview2);
        sendRequestWithOkHttp();

        TextView textView = findViewById(R.id.tuijian);
        TextView textView1 = findViewById(R.id.wenku);
        TextView textView2 = findViewById(R.id.chuangzuo);
        TextView textView3 = findViewById(R.id.yonghucenter);

        //刷新推荐
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });

        //进入文库
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LmxRecommendActivity.this,ZyqLibraryActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("userid",userid);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        //进入创作中心
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //进入用户中心
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


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
                        List<WorkForFind> works1 = parseJSONWithGSON(responseData);
                        for(WorkForFind workForFind:works1){
                            System.out.println(workForFind);
                        }

                        //显示UI界面，调用的showResponse方法
                        showResponse(works1);

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
    private List<WorkForFind> parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<WorkForFind> appList = gson.fromJson(jsonData, new TypeToken<List<WorkForFind>>() {}.getType());
        return appList;
    }
    private void sendRequestWithOkHttp2(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                url2 = url2+type;
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                //adapter.notifyDataSetChanged();
                httpUtil.sendOkHttpRequest3(url2, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData = response.body().string();
                        List<WorkForFind> works = parseJSONWithGSON(responseData);
                        //显示UI界面，调用的showResponse方法
                        showResponse(works);
                        url2=url3;
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();
    }

    private void showResponse(final List<WorkForFind> response) {
        //在子线程中更新UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<response.size();i++){

                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("workname",response.get(i).getWorkname());
                    map.put("writername",response.get(i).getUsername());
                    map.put("workid",response.get(i).getWorkid());
                    map.put("writerid",response.get(i).getUserid());
                    listitem.add(map);
                }
                //创建适配器
                adapter = new SimpleAdapter(LmxRecommendActivity.this, listitem, R.layout.zyq_listviewmodel
                        , new String[]{"workname", "writername"}, new int[]{R.id.title, R.id.writername});

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        map = (Map<String, Object>) parent.getItemAtPosition(position);
                        //Toast.makeText(HistoryActivity.this,map.get("name").toString(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LmxRecommendActivity.this, LmxShowWorkActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("writername", map.get("writername").toString());
                        bundle.putString("workname", map.get("workname").toString());
                        bundle.putString("workid", map.get("workid").toString());
                        bundle.putString("writerid", map.get("writerid").toString());
                        bundle.putString("userid", userid);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //finish();
                    }
                });
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
}
