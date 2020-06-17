package com.example.literaturesharing9.UserMain;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.literaturesharing9.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
public class drawer extends FragmentActivity implements View.OnClickListener{
    private com.example.literaturesharing9.UserMain.user user=null;
    private FirstFragment fg1;
    private SecondFragment fg2;
    private ThirdFragment fg3;
    private FourFragment fg4;
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private RelativeLayout thirdLayout;
    private RelativeLayout fourthLayout;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int dark = 0xff000000;
    private String flag="2";
    private FragmentManager fragmentManager;
    DrawerLayout draw;
    Button button;
    Button button1;
    LinearLayout zhu;
    private String id;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (msg.what) {
                case 1:
                    ArrayList<work> data = (ArrayList<work>) msg.obj;
                    if (fg2 == null) {
                        fg2 = new SecondFragment(data);
                        fragmentTransaction.add(R.id.content, fg2);
                    } else {
                        fragmentTransaction.show(fg2);
                    }
                    fragmentTransaction.commit();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        draw=(DrawerLayout)findViewById(R.id.draw);
        zhu=(LinearLayout)findViewById(R.id.draw_left_menu);
        fragmentManager = getSupportFragmentManager();
        InitView();
    }

    public void InitView(){
        firstImage = (ImageView) findViewById(R.id.first_image);
        secondImage = (ImageView) findViewById(R.id.second_image);
        thirdImage = (ImageView) findViewById(R.id.third_image);
        fourthImage = (ImageView) findViewById(R.id.fourth_image);
        firstText = (TextView) findViewById(R.id.first_text);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        fourthText = (TextView) findViewById(R.id.fourth_text);
        firstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        secondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        thirdLayout = (RelativeLayout) findViewById(R.id.third_layout);
        fourthLayout = (RelativeLayout) findViewById(R.id.fourth_layout);
        firstLayout.setOnClickListener(drawer.this);
        secondLayout.setOnClickListener(drawer.this);
        thirdLayout.setOnClickListener(drawer.this);
        fourthLayout.setOnClickListener(drawer.this);
        getUser("1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setChioceItem(0);
                break;
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            default:
                break;
        }
    }




    public void clearChioce(){
        firstText.setTextColor(gray);
        firstLayout.setBackgroundColor(whirt);
        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(whirt);
        thirdText.setTextColor(gray);
        thirdLayout.setBackgroundColor(whirt);
        fourthText.setTextColor(gray);
        fourthLayout.setBackgroundColor(whirt);

    }

    private void removeFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.remove(fg1);
            fg1=null;
        }
        if (fg2 != null) {
            fragmentTransaction.remove(fg2);
            fg2=null;
        }
        if (fg3 != null) {
            fragmentTransaction.remove(fg3);
            fg3=null;
        }
        if (fg4 != null) {
            fragmentTransaction.remove(fg4);
            fg4=null;
        }
    }

    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        removeFragments(fragmentTransaction);
        switch (index) {
            case 0:
                firstText.setTextColor(dark);
                firstLayout.setBackgroundColor(gray);
                if (fg1 == null) {
                    fg1 = new FirstFragment(user);
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
                    fragmentTransaction.show(fg1);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                secondText.setTextColor(dark);
                secondLayout.setBackgroundColor(gray);
                flag="1";
                getWork();
                fragmentTransaction.commit();
                break;
            case 2:
                thirdText.setTextColor(dark);
                thirdLayout.setBackgroundColor(gray);
                if (fg3 == null) {
                    fg3 = new ThirdFragment(user);
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                fragmentTransaction.commit();
                break;
            case 3:
                fourthText.setTextColor(dark);
                System.out.println(user);
                fourthLayout.setBackgroundColor(gray);
                flag="2";
                if (fg4 == null) {
                    fg4 = new FourFragment(user);
                    fragmentTransaction.add(R.id.content, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                fragmentTransaction.commit();
                break;
        }
       // fragmentTransaction.commit(); // 提交
    }
    public class getTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            //依次获取用户名，密码与路径
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
                    if(flag.equals("1")) {
                        Gson gson = new Gson();  //引用谷歌的json包
                        System.out.println(data);
                        ArrayList<work> worklist = gson.fromJson(data, new TypeToken<ArrayList<work>>() {
                        }.getType());
                        Message msg = new Message();
                        msg.what=1;
                        msg.obj = worklist;
                        mHandler.sendMessage(msg);
                    }
                    else{
                        Gson gson=new Gson();  //引用谷歌的json包
                        user=gson.fromJson(data,new TypeToken<user>(){}.getType());
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
    public void getWork(){
        String path="http://10.0.2.2:8081/jdbc/getWork?id="+1;
        getTask task=new getTask();
        task.execute(path);

    }

    public void getUser(String id){
        String path="http://10.0.2.2:8081/jdbc/user?id="+id;
        getTask task=new getTask();
        task.execute(path);
    }
}


