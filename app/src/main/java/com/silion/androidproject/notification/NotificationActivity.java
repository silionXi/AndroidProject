package com.silion.androidproject.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;
import com.silion.androidproject.progressdialog.ProgressDialogActivity;
import com.silion.androidproject.recycleview.RecyclerViewActivity;
import com.silion.androidproject.viewpager.ViewPagerActivity;

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
            case R.id.btNotification1: {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("通知")
                        .setContentText("我是通知")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification2)
                        .build();
                manager.notify(1, notification);
                break;
            }
            case R.id.btNotification2: {
                Intent intent = new Intent(this, RecyclerViewActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("通知")
                        .setContentText("点击我试试看")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification2)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager.notify(2, notification);
                break;
            }
            case R.id.btNotification3: {
                Intent intent = new Intent(this, ViewPagerActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("通知")
                        .setContentText("点击我试试看")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("很长长长长长长长长长长长长长长长长长长长长长长长长长文本" +
                                "长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长" +
                                "文本的通知"))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification2)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager.notify(3, notification);
                break;
            }
            case R.id.btNotification4: {
                Intent intent = new Intent(this, ProgressDialogActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("通知")
                        .setContentText("点击我试试看")
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.notification1)))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification2)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager.notify(4, notification);
                break;
            }
            case R.id.btNotification5: {
                Intent intent = new Intent(this, ProgressDialogActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("通知")
                        .setContentText("点击我试试看")
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.notification1)))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification2)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();
                manager.notify(5, notification);
                break;
            }
            case R.id.btNotification6: {
                Intent intent = new Intent(this, ForegroundService.class);
                startService(intent);
            }
            default:
                break;
        }
    }
}
