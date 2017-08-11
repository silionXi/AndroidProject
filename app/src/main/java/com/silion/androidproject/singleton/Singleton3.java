package com.silion.androidproject.singleton;

import android.util.Log;

/**
 * Created by silion on 2017/8/11.
 */

public class Singleton3 {
    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return SingletonHolder.sInstance;
    }

    public void doSomething() {
        Log.d("Singleton", "私有静态内部类单例模式");
    }

    private static class SingletonHolder {
        private static final Singleton3 sInstance = new Singleton3();
    }
}
