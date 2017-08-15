package com.silion.androidproject.thread;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by silion on 2017/8/11.
 */

public class Product {
    private static final String TAG = "生产者-消费者模式";
    private String longName;
    private String shortName;
    private boolean isEmpty = true;
    private int count = 1;  //用来标志当前资源的id

    public synchronized void setName(String longName, String shortName) {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "setssssssssssssss 生产" + longName + count + "..." + shortName);
        this.longName = longName + count++;
        SystemClock.sleep(50);
        this.shortName = shortName;
        isEmpty = false;
        notifyAll();
    }

    public synchronized void getName() {
        while (isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SystemClock.sleep(50);
        Log.d(TAG, "getgggggggggggggg 消费" + longName + "..." + shortName);
        isEmpty = true;
        notifyAll();
    }
}
