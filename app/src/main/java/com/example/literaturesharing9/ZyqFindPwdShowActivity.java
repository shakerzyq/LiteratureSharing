package com.example.literaturesharing9;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import Utils.JsonAndObject;
        import domain.User;

/**
 * 显示密码
 */
public class ZyqFindPwdShowActivity extends AppCompatActivity {

    private String responseDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zyq_activity_find_pwd_show);

        Bundle bundle = this.getIntent().getExtras();
        responseDate = bundle.getString("responseData");

        TextView textView = findViewById(R.id.showpwd);
        Button button = findViewById(R.id.showpwdbt);

        final User user = JsonAndObject.toUser(responseDate);

        textView.setText(user.getPassword());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZyqFindPwdShowActivity.this, ZyqLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
