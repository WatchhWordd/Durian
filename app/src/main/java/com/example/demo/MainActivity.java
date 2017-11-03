package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.data.net.bean.UserInfo;
import com.example.demo.presentation.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginJump;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginJump = (Button) findViewById(R.id.id_jump_login);
        loginJump.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        textView = (TextView) findViewById(R.id.id_text_view);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        UserInfo userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
        textView.setText(userInfo.getName());
    }
}
