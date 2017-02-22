package com.silion.androidproject.network.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class SocketActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
    }

    public void startServer(View view) {
        new SimpleHttpServer().start();
    }

    public void postRequest(View view) {
        SimpleHttpPost httpPost = new SimpleHttpPost("127.0.0.1");

        httpPost.addParam("username", "silion");
        httpPost.addParam("pwd", "password");

        httpPost.start();
    }
}
