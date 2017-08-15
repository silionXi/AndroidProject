package com.silion.androidproject.thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by silion on 2017/8/14.
 */

public class Butterer implements Runnable {
    private BlockingQueue<Toast> dryQueue, butteredQueue;

    public Butterer(BlockingQueue<Toast> dryQueue, BlockingQueue<Toast> butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = dryQueue.take();
                toast.butter();
                Log.d("生产者-消费者模式-队列", toast.toString());
                butteredQueue.put(toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("生产者-消费者模式-队列", "Butterer off");
    }
}
