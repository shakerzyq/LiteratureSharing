package com.example.literaturesharing9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ZyqTestActivity extends AppCompatActivity {

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_test);

        TextView textView = findViewById(R.id.logintest);
        Button button = findViewById(R.id.wenku);


        Bundle bundle = this.getIntent().getExtras();
        userid = bundle.getString("userid");

        textView.setText(userid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZyqTestActivity.this,ZyqLibraryActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("userid",userid);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
}
