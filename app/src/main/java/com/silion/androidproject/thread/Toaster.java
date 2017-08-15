package com.silion.androidproject.thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by silion on 2017/8/14.
 */

public class Toaster implements Runnable {
    private BlockingQueue<Toast> toastQueue;
    private int count = 0;

    public Toaster(BlockingQueue<Toast> dryQueue) {
        toastQueue = dryQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
//                TimeUnit.MILLISECONDS.sleep(500);
                Toast toast = new Toast(count++);
                Log.d("生产者-消费者模式-队列", toast.toString());
                toastQueue.put(toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("生产者-消费者模式-队列", "Toaster off");
    }
}
