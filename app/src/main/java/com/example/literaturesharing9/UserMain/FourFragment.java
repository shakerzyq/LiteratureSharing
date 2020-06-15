package com.example.literaturesharing9.UserMain;

import androidx.fragment.app.Fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.literaturesharing9.R;


public class FourFragment extends Fragment {
    private com.example.literaturesharing9.UserMain.user user;
    private String msg;
    private TextView account;
    private TextView name;
    private TextView sex;
    private TextView birthday;
    private TextView label;
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
        init();
        return  view;
    }

    public void init(){
        account.setText(user.getUserid());
        name.setText(user.getUsername());
        sex.setText(user.getSex());
        birthday.setText(user.getBirthday());
        label.setText(user.getAutograph());
    }
}

