package com.silion.androidproject.thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by silion on 2017/8/14.
 */

public class Eater implements Runnable {
    private BlockingQueue<Toast> finishedQueue;
    private int counter = 0;

    public Eater(BlockingQueue<Toast> finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = finishedQueue.take();
                if (toast.getId() != counter++ || toast.getStatus() != Toast.Status.JAMMED) {
                    Log.e("生产者-消费者模式-队列", "出错啦............");
                    System.exit(1);
                } else {
                    Log.d("生产者-消费者模式-队列", "Chomp!" + toast);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("生产者-消费者模式-队列", "Eater off");
    }
}
