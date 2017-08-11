package com.silion.androidproject.singleton;

import android.util.Log;

import java.io.ObjectStreamException;

/**
 * DCL双重检查锁定单例模式
 */

public class Singleton2 {
    private volatile static Singleton2 sInstance = null;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (sInstance == null) {
            synchronized (Singleton2.class) {
                if (sInstance == null) {
                    sInstance = new Singleton2();
                }
            }
        }

        return sInstance;
    }

    public void doSomething() {
        Log.d("Singleton", "DCL双重检查锁定单例模式");
    }

    /**
     * 杜绝单例对象在被反序列化时重新生成对象
     *
     * @return
     * @throws ObjectStreamException
     */
    public Object readResolve() throws ObjectStreamException {
        return sInstance;
    }
}
