package com.example.literaturesharing9;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import Utils.HttpUtil;
import domain.WorkForFind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 文库，显示文章
 */
public class ZyqLibraryActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter adapter;
    List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
    private Map<String,Object> map;
    private HashSet<String> set = new HashSet<String>();
    private String userid;
    private String url="http://10.0.2.2:8081/FindWorksByType/";
    private String url1="http://10.0.2.2:8081/FindWorksByType/";
    private String url2="http://10.0.2.2:8081/FindWorks/";
    private String url3="http://10.0.2.2:8081/FindWorks/";

    //声明控件
    private TextView poetry;
    private TextView prose;
    private TextView littlenovel;
    private TextView travelnoties;
    private ImageButton imageButton;
    private EditText editText;
    private String id;
    private TextView[] list = {poetry,prose,littlenovel,travelnoties};

    //okhttp工具类
    private HttpUtil httpUtil = new HttpUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_library);

        listView = findViewById(R.id.listview1);

        imageButton = findViewById(R.id.finding);
        editText = findViewById(R.id.findedit);

        //接受跳转过来的id
        Intent intent=getIntent();
        userid=intent.getStringExtra("id");
        list[0] = findViewById(R.id.poetry);
        list[1] = findViewById(R.id.prose);
        list[2] = findViewById(R.id.littlenovel);
        list[3] = findViewById(R.id.travelnotes);

        //诗歌
        list[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(list[0]);
                listitem = new ArrayList<Map<String,Object>>();
                sendRequestWithOkHttp(list[0].getText().toString());

            }
        });

        //散文
        list[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(list[1]);
                listitem = new ArrayList<Map<String,Object>>();
                sendRequestWithOkHttp(list[1].getText().toString());

            }
        });

        //小说
        list[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitem = new ArrayList<Map<String,Object>>();
                sendRequestWithOkHttp(list[2].getText().toString());
                setStatus(list[2]);
               // list[2].setBackgroundColor(Color.parseColor("#000000"));
            }
        });

        //游记
        list[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(list[3]);
                listitem = new ArrayList<Map<String,Object>>();
                sendRequestWithOkHttp(list[3].getText().toString());
            }
        });

        //通过文字搜索
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitem = new ArrayList<Map<String,Object>>();
                sendRequestWithOkHttp2(editText.getText().toString());
                for(TextView s:list){
                        s.setBackgroundColor(Color.parseColor("#000000"));
                        s.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });
        sendRequestWithOkHttp(list[0].getText().toString());
    }

    //设置按键的背景和文字颜色的方法
    public void setStatus(TextView textView){
        for(TextView s:list){
            if (s.equals(textView)){
                s.setBackgroundColor(Color.parseColor("#ffffff"));
                s.setTextColor(Color.parseColor("#000000"));
            }
            else{
                s.setBackgroundColor(Color.parseColor("#000000"));
                s.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    }
    //开局查询诗歌
    private void sendRequestWithOkHttp(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                url = url+type;
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
                httpUtil.sendOkHttpRequest3(url2, new okhttp3.Callback() {
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
                adapter = new SimpleAdapter(ZyqLibraryActivity.this, listitem, R.layout.zyq_listviewmodel
                        , new String[]{"workname", "writername"}, new int[]{R.id.title, R.id.writername});

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        map = (Map<String, Object>) parent.getItemAtPosition(position);
                        //Toast.makeText(HistoryActivity.this,map.get("name").toString(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ZyqLibraryActivity.this, LmxShowWorkActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("writername", map.get("writername").toString());
                        bundle.putString("workname", map.get("workname").toString());
                        bundle.putString("workid", map.get("workid").toString());
                        bundle.putString("writerid", map.get("writerid").toString());
                        bundle.putString("userid", userid);
                        intent.putExtras(bundle);
                        startActivity(intent);
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

    //添加返回监听
    @Override
    public void onBackPressed() {
        ZyqLibraryActivity.this.finish();
    }
}
