package com.silion.androidproject.thread;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by silion on 2017/8/11.
 */

public class ThreadActvity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.sync:
                Product product = new Product();
                Factory factory = new Factory(product);
                Custom custom = new Custom(product);
                new Thread(factory).start();
                new Thread(factory).start();
                new Thread(factory).start();
                new Thread(custom).start();
                new Thread(custom).start();
                new Thread(custom).start();
                break;
            case R.id.blocking:
                BlockingQueue<Toast> dryQueue = new LinkedBlockingQueue<>(),
                        butteredQueue = new LinkedBlockingQueue<>(),
                        finishedQueue = new LinkedBlockingQueue<>();
                ExecutorService executor = Executors.newCachedThreadPool();
                executor.execute(new Toaster(dryQueue));
                executor.execute(new Butterer(dryQueue, butteredQueue));
                executor.execute(new Jammer(butteredQueue, finishedQueue));
                executor.execute(new Eater(finishedQueue));
                SystemClock.sleep(5000);
                executor.shutdownNow();
                break;
        }
    }
}
