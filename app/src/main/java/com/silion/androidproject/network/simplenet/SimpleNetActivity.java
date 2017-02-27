package com.silion.androidproject.network.simplenet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.silion.androidproject.R;
import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.core.RequestQueue;
import com.silion.androidproject.network.simplenet.requests.StringRequest;

public class SimpleNetActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_net);
        tvResult = (TextView) findViewById(R.id.tvResult);
        mQueue = SimpleNet.newRequestQueue();
    }

    public void sendStringRequest(View view) {
        StringRequest request = new StringRequest(Request.HttpMethod.GET, "http://www.qq.com/", new Request.RequestListener<String>() {
            @Override
            public void onComplete(int stCode, String response, String errMsg) {
                tvResult.setText(Html.fromHtml(response));
            }
        });
        mQueue.addRequest(request);
    }
}
