package com.example.literaturesharing9.UserMain;

import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.literaturesharing9.R;
public class FourFragment extends Fragment {
    private com.example.literaturesharing9.UserMain.user user;
    private String msg;
    private TextView account;
    private TextView name;
    private TextView sex;
    private TextView birthday;
    private TextView label;
    private ImageView img;
    public FourFragment(user user){
        this.user=user;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg4,container, false);
        account=view.findViewById(R.id.account);
        name=view.findViewById(R.id.name);
        sex=view.findViewById(R.id.sex);
        birthday=view.findViewById(R.id.birthday);
        label=view.findViewById(R.id.label);
        img=(ImageView)view.findViewById(R.id.head_image);
        init();
        return  view;
    }

    public void init(){
        account.setText(user.getUserid());
        name.setText(user.getUsername());
        sex.setText(user.getSex());
        birthday.setText(user.getBirthday());
        label.setText(user.getAutograph());
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
        //bitmap.recycle();
    }
}

