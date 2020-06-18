package com.example.literaturesharing9;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * 加载界面
 */
public class ZyqLaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_launch);

        Handler x = new Handler();
        x.postDelayed(new splashhandler(),3000);
    }

    class splashhandler implements Runnable{


        @Override
        public void run() {
            //Intent intent = new Intent(ZyqLaunchActivity.this,ZyqLoginActivity.class);
            startActivity(new Intent(getApplication(),ZyqLoginActivity.class));
            ZyqLaunchActivity.this.finish();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.zyq_activity_launch,menu);
        return true;
    }
}


