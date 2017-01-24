package com.silion.androidproject.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class AlarmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btStartService: {
                Intent intent = new Intent(this, LongRunningService.class);
                startService(intent);
                break;
            }
            case R.id.btStopService: {
                Intent intent = new Intent(this, LongRunningService.class);
                stopService(intent);
                break;
            }
            default:
                break;
        }
    }
}
