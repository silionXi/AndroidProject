package com.silion.androidproject.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.util.Calendar;
import java.util.Date;

public class IntentActivity extends BaseActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
    }

    public void createAlarm(View view) {
        createAlarm("创建闹钟", 8, 40);
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);

        safeStartActivity(intent);
    }

    public void createTimer(View view) {
        createTimer("创建定时器", 10);
    }

    private void createTimer(String message, int seconds) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        safeStartActivity(intent);
    }

    private void safeStartActivity(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showAlarm(View view) {
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        safeStartActivity(intent);
    }

    public void insertCalendar(View view) {
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DATE, begin.get(Calendar.DATE) + 1);
        insertCalendar("添加日历事件", "地球", begin, end);
    }

    private void insertCalendar(String title, String location, Calendar begin, Calendar end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);

        safeStartActivity(intent);
    }

    public void imageCapture(View view) {
        String targetFileName = "image01";
        imageCapture(targetFileName);
    }

    private void imageCapture(String targetFileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(getExternalCacheDir().getPath() + "/" + targetFileName));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bitmap thumbnail = data.getParcelableExtra("data");
                    ImageView ivThumb = (ImageView) findViewById(R.id.ivThumb);
                    ivThumb.setImageBitmap(thumbnail);
                }
                break;
        }
    }
}
