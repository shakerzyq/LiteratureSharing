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
public class workadapter extends BaseAdapter {
    private ArrayList<work> mData;
    private Context mContext;
    public interface OnremoveListnner{
        void  ondelect(int i);
    }
    private OnremoveListnner onremoveListnner;

    public void setOnremoveListnner(OnremoveListnner onremoveListnner) {
        this.onremoveListnner = onremoveListnner;
    }
    public workadapter(ArrayList<work> mData, Context mContext) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.work_item,parent,false);
        TextView title=(TextView) convertView.findViewById(R.id.work_item_title);
        TextView name=(TextView)convertView.findViewById(R.id.work_item_content);
        TextView date=(TextView)convertView.findViewById(R.id.work_item_date);
        title.setText(mData.get(position).getWorkname());
        name.setText(mData.get(position).getWorkcontent());
        date.setText(mData.get(position).getWorktime());
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onremoveListnner.ondelect(position);
                return true;
            }
        });

        return convertView;
    }
}
