package com.silion.androidproject.leakcanarytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.silion.androidproject.R;

/**
 * 权限问题,要先打开Leaks
 */
public class LeakCanaryTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary_test);
        ActivityMgr.getInstance().addActivity(this);
        LeakClass leakClass = new LeakClass();
        leakClass.start();
    }

    class LeakClass extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(60 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}