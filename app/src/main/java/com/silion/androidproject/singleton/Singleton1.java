package com.silion.androidproject.singleton;

import android.util.Log;

/**
 * 饿汉单例模式
 */

public class Singleton1 {
    private static final Singleton1 sInstance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return sInstance;
    }

    public void doSomething() {
        Log.d("Singleton", "饿汉单例模式");
    }
}
