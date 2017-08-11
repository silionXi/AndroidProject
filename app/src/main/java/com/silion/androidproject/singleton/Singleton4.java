package com.silion.androidproject.singleton;

import android.util.Log;

/**
 * 枚举单例
 */

public enum Singleton4 {
    INSTANCE;

    public void doSomething() {
        Log.d("Singleton", "枚举单例");
    }
}
