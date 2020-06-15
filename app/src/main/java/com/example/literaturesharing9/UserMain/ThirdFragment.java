package com.example.literaturesharing9.UserMain;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.literaturesharing9.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;

public class ThirdFragment extends Fragment {
    private com.example.literaturesharing9.UserMain.user user;
    private EditText workname;
    private Spinner worktype;
    private EditText workcontent;
    private Button button;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    Toast.makeText(getContext(), (String) msg.obj,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    public ThirdFragment(user user){
        this.user=user;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg3,container, false);
        workname=(EditText)view.findViewById(R.id.workname);
        worktype=(Spinner)view.findViewById(R.id.worktype);
        workcontent=(EditText)view.findViewById(R.id.workcontent);
        button=(Button)view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        return view;
    }

    public class getTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            //依次获取用户名，密码与路径
            String path = params[0].toString();
            String data= params[1].toString();
            try {
                //获取网络上get方式提交的整个路径
                URL url = new URL(path);
                String param="work="+data;
                //打开网络连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置提交方式
                conn.setRequestMethod("POST");
                //设置网络超时时间
                conn.setConnectTimeout(5000);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(param.getBytes());
                //获取结果码
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream inputStream=conn.getInputStream();
                    // 调用自己写的NetUtils() 将流转成string类型
                    String data1= acceptJSON.readString(inputStream);
                    if(data1.equals("1")){
                        Message msg = new Message();
                        msg.what=0;
                        msg.obj = "发表成功，等待系统审核";
                        mHandler.sendMessage(msg);
                    }else {
                        Message msg = new Message();
                        msg.what=1;
                        msg.obj = "发表成功，等待系统审核";
                        mHandler.sendMessage(msg);
                    }
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

    public void save(){
        work work=new work();
        String name=workname.getText().toString();
        String temp=worktype.getSelectedItem().toString();
        int type=1;
        if(temp.equals("小说")){
            type=2;
        }
        if(temp.equals("散文")){
            type=3;
        }
        String content=workcontent.getText().toString();
        String userid=user.getUserid();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        String time=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day)+" "+String.valueOf(hour)+":"
                +String.valueOf(minute);
        work.setWorkname(name);
        work.setWorkcontent(content);
        work.setWorktime(time);
        work.setUserid(userid);
        work.setType(type);
        work.setWorkzan(0);
        Gson gson=new GsonBuilder().serializeNulls().create();
        String work1=gson.toJson(work);
        String path="http://10.0.2.2:8081/jdbc/addwork";
        getTask task=new getTask();
        task.execute(path,work1);
    }
}
