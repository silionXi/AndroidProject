package com.silion.androidproject.otto;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.otto.Bus;

/**
 * Created by silion on 2017/8/30.
 * 主线程事件总线，方便在异步任务中回调
 */

public class MainThreadBus extends Bus {
    private static final String TAG = "MainThreadBus";
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // 如果是在主线程，直接调用post发布
            Log.d(TAG, "silion post from UI thread");
            super.post(event);
        } else {
            // 通过handler发送到UI线程进行发布
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "silion post from thread");
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}
