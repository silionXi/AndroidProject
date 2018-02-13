package com.silion.androidproject.leakcanarytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.R;

/**
 * 权限问题,要先打开Leaks
 */
public class LeakCanaryTestActivity extends AppCompatActivity {

    private MyView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary_test);
        ActivityMgr.getInstance().addActivity(this); // 内存泄漏
        // memoryLeak1();
        // memoryLeak2();
    }

    /**
     * 需要在{@link #onDestroy()} 清除所有监听
     */
    private void memoryLeak2() {
        mView = new MyView(this);
        mView.setOnTestListener(new MyView.OnTestListener() {
            @Override
            public void test() {

            }
        });
        android.util.Log.d("silion_debug", ListenerCollector.map.size() + "");
    }

    private void memoryLeak1() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解决内存泄漏
        ActivityMgr.getInstance().removeActivity(this);
    }
}