package com.silion.androidproject.thread;

import android.util.Log;

/**
 * Created by silion on 2017/8/11.
 */

public class Factory implements Runnable {
    private Product mProduct = null;

    public Factory(Product product) {
        mProduct = product;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                mProduct.setName("产品A", "产品A");
            } else {
                mProduct.setName("产品B", "产品B");
            }
        }
        Log.d("生产者-消费者模式","------------------------------------------------生产结束");
    }
}
