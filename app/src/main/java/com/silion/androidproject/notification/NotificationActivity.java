package com.silion.androidproject.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;
import com.silion.androidproject.progressdialog.ProgressDialogActivity;
import com.silion.androidproject.recycleview.RecyclerViewActivity;
import com.silion.androidproject.viewpager.ViewPagerActivity;

/**
 * Created by silion on 2017/1/18.
 * 参考Android群英传P296 Notification
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
                        .setContentTitle("普通通知")
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
                        .setContentTitle("可以点击的通知")
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
                        .setContentTitle("长文本通知")
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
                        .setContentTitle("大图片通知")
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
                        .setContentTitle("最重要程度通知")
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
                break;
            }
            case R.id.btNotification7: {
                Notification.Builder builder = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.notification2)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentTitle("悬挂式通知")
                        .setContentTitle("我是悬挂式通知");

                Intent push = new Intent();
                push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                push.setClass(this, RecyclerViewActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setFullScreenIntent(pendingIntent, true);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(6, builder.build());
                break;
            }
            case R.id.btNotification8: {
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
                contentView.setTextViewText(R.id.textView, "我可以展开的哟");

                RemoteViews bigContentView = new RemoteViews(getPackageName(), R.layout.notification_expanded);
                bigContentView.setTextViewText(R.id.textView, "我展开了哟");

                Notification.Builder builder = new Notification.Builder(this);
                builder.setSmallIcon(R.drawable.notification2);
                builder.setAutoCancel(true);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification1));
                Notification notification = builder.build();
                notification.contentView = contentView;
                notification.bigContentView = bigContentView;

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(8, notification);
                break;
            }
            case R.id.btNotification9: {
                int id = 9;
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgNotification);
                Notification.Builder builder = new Notification.Builder(this)
                        .setContentTitle("显示等级的通知");
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rbPublic: {
                        id = 9;
                        builder.setVisibility(Notification.VISIBILITY_PUBLIC) // 表明在任何情况下都显示
                                .setContentText("Public")
                                .setSmallIcon(R.drawable.notification2);
                        break;
                    }
                    case R.id.rbPrivate: {
                        id = 10;
                        builder.setVisibility(Notification.VISIBILITY_PRIVATE) // 表明只有当没有锁屏的时候回显示
                                .setContentText("Private")
                                .setSmallIcon(R.drawable.notification2);
                        break;
                    }
                    case R.id.rbSecret: {
                        id = 11;
                        builder.setVisibility(Notification.VISIBILITY_SECRET) // 表明在PIM, PASSWORD等安全锁和没有锁屏的情况下才能够显示
                                .setContentText("Secret")
                                .setSmallIcon(R.drawable.notification2);
                        break;
                    }
                    default:
                        break;
                }

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(id, builder.build());
                break;
            }
            default:
                break;
        }
    }
}
