package com.silion.androidproject.thread;

import android.util.Log;

/**
 * Created by silion on 2017/8/11.
 */

public class Custom implements Runnable {
    private Product mProduct;

    public Custom(Product product) {
        mProduct = product;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            mProduct.getName();
        }
        Log.d("生产者-消费者模式","------------------------------------------------消费结束");
    }
}
