package com.example.literaturesharing9.UserMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.literaturesharing9.R;
import java.util.ArrayList;


public class IconAdapter extends BaseAdapter {
    private ArrayList<Icon> mData;
    private Context mContext;
    public IconAdapter(ArrayList<Icon> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.icon_item,parent,false);
        ImageView id=(ImageView) convertView.findViewById(R.id.img_icon);
        TextView name=(TextView)convertView.findViewById(R.id.txt_icon);
        id.setImageResource(mData.get(position).getiId());
        name.setText(mData.get(position).getiName());
        return convertView;
    }
}
