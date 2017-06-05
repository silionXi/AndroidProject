package com.silion.androidproject.network.simplenet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.silion.androidproject.R;
import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.core.RequestQueue;
import com.silion.androidproject.network.simplenet.entity.MultipartEntity;
import com.silion.androidproject.network.simplenet.requests.MultipartRequest;
import com.silion.androidproject.network.simplenet.requests.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;

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

    /**
     * 发送MultipartRequest,可以传字符串参数、文件、Bitmap等参数,这种请求为POST类型
     */
    public void sendMultipartRequest(View view) {
        // 2、创建请求
        MultipartRequest multipartRequest = new MultipartRequest("你的url",
                new Request.RequestListener<String>() {
                    @Override
                    public void onComplete(int stCode, String response, String errMsg) {
                        // 该方法执行在UI线程
                    }
                });

        // 3、添加各种参数
        // 添加header
        multipartRequest.addHeader("header-name", "value");
        // 通过MultipartEntity来设置参数
        MultipartEntity multi = multipartRequest.getMultipartEntity();
        // 文本参数
        multi.addStringPart("location", "模拟的地理位置");
        multi.addStringPart("type", "0");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model01);
        // 直接从上传Bitmap
        multi.addBinaryPart("images", bitmapToBytes(bitmap));
        // 上传文件
        multi.addFilePart("imgfile", new File("storage/emulated/0/test.jpg"));
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onDestroy() {
        mQueue.stop();
        super.onDestroy();
    }
}
