package com.example.literaturesharing9.UserMain;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.literaturesharing9.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
public class SecondFragment extends Fragment {
    private AlertDialog.Builder builder;
    private boolean flag = true;
    private String id;
    private GridView recyclerView;
    private ArrayList<work> list;
    private com.example.literaturesharing9.UserMain.workadapter workadapter;
    private String data;
    private callback callback;
    public interface  callback{
        public String setdata(String data);
    }

    public SecondFragment(ArrayList<work>list){
        this.list=list;
    }

    public void setCallback(callback callback){
        this.callback=callback;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg2, container, false);
        recyclerView=(GridView)view.findViewById(R.id.grid_zuopin);
        workadapter=new workadapter(list,getContext());
        recyclerView.setAdapter(workadapter);
        workadapter.setOnremoveListnner(new workadapter.OnremoveListnner() {
            @Override
            public void ondelect(int i) {
                id=list.get(i).getWorkid();
                delete(i);
            }
        });
        return view;
    }
    public void delete(final int index){
        builder = new AlertDialog.Builder(getContext()).setIcon(R.mipmap.ic_launcher).setTitle("")
                .setMessage("确定删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        list.remove(index);
                        workadapter.notifyDataSetChanged();
                        deletefromData();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }
    public class getTask extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] params) {
            //依次获取用户名，密码与路径
        /*String id = params[0].toString();
        String password = params[1].toString();*/
            String path = params[0].toString();



            try {
                //获取网络上get方式提交的整个路径
                URL url = new URL(path);
                //打开网络连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置提交方式
                conn.setRequestMethod("GET");
                //设置网络超时时间
                conn.setConnectTimeout(5000);
                //获取结果码
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream inputStream=conn.getInputStream();
                    // 调用自己写的NetUtils() 将流转成string类型
                    String data= acceptJSON.readString(inputStream);
                    System.out.println("删除成功");
                    //Toast.makeText(note_today.this,json, Toast.LENGTH_SHORT).show();
                    return "";
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void deletefromData(){
        //获取网络上的servlet路径
        String path="http://10.0.2.2:8081/jdbc/deletework?id="+id;
        //调用getTask,把获取到的用户名，密码与路径放入方法中
        getTask task=new getTask();
        task.execute(path);
        // Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}