package com.example.literaturesharing9.UserMain;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.literaturesharing9.R;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private com.example.literaturesharing9.UserMain.user user;
    private EditText nameupdate;
    private EditText autographupdate;
    private ImageView img;
    private Spinner year;
    private Spinner month;
    private Spinner day;
    private Spinner sex;
    private Button save;
    private String yeartwo;
    private String monthtwo;
    private String daytwo;
    private String sextwo;
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
    public FirstFragment(user user){
        this.user=user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);
        nameupdate=(EditText)view.findViewById(R.id.nameupdate);
        autographupdate=(EditText)view.findViewById(R.id.autographupdate);
        year=(Spinner)view.findViewById(R.id.spin_year);
        month=(Spinner)view.findViewById(R.id.spin_month);
        day=(Spinner)view.findViewById(R.id.spin_day);
        sex=(Spinner)view.findViewById(R.id.spin_sex);
        save=(Button)view.findViewById(R.id.save);
        img=(ImageView)view.findViewById(R.id.update_image);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        init();
        return view;
    }

    public void init(){
        nameupdate.setHint(user.getUsername());
        autographupdate.setHint(user.getAutograph());
        String []birthday=user.getBirthday().split("-");
        int year_position=Integer.parseInt(birthday[0])-1990;
        int month_position=Integer.parseInt(birthday[1])-1;
        int day_position=Integer.parseInt(birthday[2])-1;
        year.setSelection(year_position);
        month.setSelection(month_position);
        day.setSelection(day_position);
        if(user.getSex().equals("男")){
            sex.setSelection(0);
        }else{
            sex.setSelection(1);
        }
        //圆形图片
        //头像制作成圆形
        BitmapDrawable drawable=(BitmapDrawable)img.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        int width = drawable.getIntrinsicWidth();;
        System.out.println(width);
        Bitmap resultmap=Bitmap.createBitmap(width,width,Bitmap.Config.ARGB_8888);
        Paint paint=new Paint();
        Canvas canvas=new Canvas(resultmap);
        canvas.drawCircle(width/2,width/2,width/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        img.setImageBitmap(resultmap);
        System.out.println(getContext());
        System.out.println(getContext());
        //bitmap.recycle();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spin_year:
                yeartwo=parent.getItemAtPosition(position).toString();
                break;
            case R.id.spin_month:
                monthtwo=parent.getItemAtPosition(position).toString();
                break;
            case R.id.spin_day:
                daytwo=parent.getItemAtPosition(position).toString();
                break;
            case R.id.spin_sex:
                sextwo=parent.getItemAtPosition(position).toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                String param="user="+data;
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
                        msg.obj = "保存成功";
                        mHandler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what=0;
                        msg.obj = "保存失败";
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
        yeartwo=year.getSelectedItem().toString();
        monthtwo=month.getSelectedItem().toString();
        daytwo=day.getSelectedItem().toString();
        sextwo=sex.getSelectedItem().toString();
        String birthday=yeartwo+"-"+monthtwo+"-"+daytwo;
        if(nameupdate.getText().toString().equals("")&&autographupdate.getText().toString().equals("")&&birthday.equals(user.getBirthday())&&
            sextwo.equals(user.getSex())) {
            Message msg = new Message();
            msg.what=0;
            msg.obj = "未做修改，无法保存！";
            mHandler.sendMessage(msg);
            return;
        }

        if(!nameupdate.getText().toString().equals("")){
            user.setUsername(nameupdate.getText().toString());
        }
        if(!autographupdate.getText().toString().equals("")){
            user.setAutograph(autographupdate.getText().toString());
        }
        user.setBirthday(birthday);
        user.setSex(sextwo);
        Gson gson=new Gson();
        String user1=gson.toJson(this.user);
        System.out.println(user1);
        String path="http://10.0.2.2:8081/jdbc/updateUser";
        getTask task=new getTask();
        task.execute(path,user1);
    }
}
