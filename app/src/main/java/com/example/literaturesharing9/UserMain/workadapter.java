package com.example.literaturesharing9.UserMain;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.literaturesharing9.R;

import java.util.ArrayList;

public class workadapter extends RecyclerView.Adapter<workadapter.MyViewHolder>{
    private ArrayList<work> list;
    private Context mcontext;
    private AlertDialog.Builder builder;
    private View itemView;
    public interface OnremoveListnner{
        void  ondelect(int i);
    }
    private OnremoveListnner onremoveListnner;

    public void setOnremoveListnner(OnremoveListnner onremoveListnner) {
        this.onremoveListnner = onremoveListnner;
    }
    public workadapter(ArrayList<work> list,Context mcontext){
        this.list=list;
        this.mcontext=mcontext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        TextView date;
        TextView lovenumber;
        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.work_item_title);
            content=(TextView) itemView.findViewById(R.id.work_item_content);
            date=(TextView) itemView.findViewById(R.id.work_item_date);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView= LayoutInflater.from(mcontext).inflate(R.layout.work_item,parent,false);
        MyViewHolder view=new MyViewHolder(itemView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        work work=list.get(position);
        final int index=position;
        holder.title.setText(work.getWorkname());
        holder.content.setText(work.getWorkcontent());
        holder.date.setText(work.getWorktime());
        holder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onremoveListnner.ondelect(index);
                return true;
            }
        });
        holder.date.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onremoveListnner.ondelect(index);
                return true;
            }
        });
        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onremoveListnner.ondelect(index);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
