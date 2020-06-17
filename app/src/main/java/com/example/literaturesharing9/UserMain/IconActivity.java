package com.example.literaturesharing9.UserMain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.literaturesharing9.R;
import java.util.ArrayList;

public class IconActivity extends Activity {
    private Context mContext;
    private GridView grid_photo;
    private IconAdapter mAdapter ;
    private ArrayList<Icon> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_main);
        mContext = IconActivity.this;
        grid_photo = (GridView) findViewById(R.id.grid_photo);

        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.image, "图标1"));
        mData.add(new Icon(R.drawable.image1, "图标1"));
        mData.add(new Icon(R.drawable.image2, "图标1"));
        mData.add(new Icon(R.drawable.image3, "图标1"));
        mData.add(new Icon(R.drawable.image4, "图标1"));
        mData.add(new Icon(R.drawable.image5, "图标1"));
        mAdapter = new IconAdapter(mData,mContext);
        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

