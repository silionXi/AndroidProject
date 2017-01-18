package com.silion.androidproject.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

/**
 * Created by silion on 2017/1/18.
 */

public class NotificationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btNotification1:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("通知")
                        .setContentText("我是通知")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification2)
                        .build();
                manager.notify(1, notification);
                break;
            default:
                break;
        }
    }
}
