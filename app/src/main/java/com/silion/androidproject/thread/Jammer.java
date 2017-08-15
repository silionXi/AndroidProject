package com.silion.androidproject.thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by silion on 2017/8/14.
 */

public class Jammer implements Runnable {
    private BlockingQueue<Toast> butteredQueue, finishedQueue;

    public Jammer(BlockingQueue<Toast> butteredQueue, BlockingQueue<Toast> finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = butteredQueue.take();
                toast.jam();
                Log.d("生产者-消费者模式-队列", toast.toString());
                finishedQueue.put(toast);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("生产者-消费者模式-队列", "Jammer off");
    }
}
