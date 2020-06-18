package com.example.literaturesharing9.UserMain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.literaturesharing9.LmxShowWorkActivity;
import com.example.literaturesharing9.R;
import java.util.ArrayList;
public class workadapter extends BaseAdapter {
    private ArrayList<work> mData;
    private Context mContext;
    private user user;
    private SecondFragment fg2;
    public interface OnremoveListnner{
        void  ondelect(int i);
    }
    private OnremoveListnner onremoveListnner;

    public void setOnremoveListnner(OnremoveListnner onremoveListnner) {
        this.onremoveListnner = onremoveListnner;
    }
    public workadapter(ArrayList<work> mData, Context mContext,user user,SecondFragment fg2) {
        this.mData = mData;
        this.mContext = mContext;
        this.user=user;
        this.fg2=fg2;
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
        TextView id=(TextView) convertView.findViewById(R.id.work_item_id);
        TextView title=(TextView) convertView.findViewById(R.id.work_item_title);
        TextView name=(TextView)convertView.findViewById(R.id.work_item_content);
        TextView date=(TextView)convertView.findViewById(R.id.work_item_date);
        title.setText(mData.get(position).getWorkname());
        id.setText(mData.get(position).getWorkid());
        name.setText(mData.get(position).getWorkcontent());
        date.setText(mData.get(position).getWorktime());
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onremoveListnner.ondelect(position);
                return true;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id=(TextView) v.findViewById(R.id.work_item_id);
                work works=null;
                int index=0;
                for(int i=0;i<mData.size();i++){
                    if(mData.get(i).getWorkid().equals(id.getText().toString())){
                        index=i;
                        works=(work) mData.get(index);
                    }
                }
                Intent intent = new Intent(mContext, LmxShowWorkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("writername",user.getUsername());
                bundle.putString("workname",works.getWorkname());
                bundle.putString("workid",works.getWorkid());
                bundle.putString("writerid",works.getUserid());
                bundle.putString("userid",user.getUserid());
                intent.putExtras(bundle);
                fg2.startActivity(intent);
                System.out.println("ss");
            }
        });

        return convertView;
    }
}
