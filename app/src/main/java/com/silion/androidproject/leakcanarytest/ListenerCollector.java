package com.silion.androidproject.leakcanarytest;

import android.view.View;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by silion on 2018/2/13.
 */

public class ListenerCollector {
    /**
     * 这里是错误用法，GC无法回收
     * GC后，需要再进行WeakHashMap操作(put, get, size...)才会进行回收，并且只回收key而已
     */
    public static WeakHashMap<View, MyView.OnTestListener> map = new WeakHashMap<>();

    public static void addListener(View view, MyView.OnTestListener listener) {
        map.put(view, listener);
    }

    public static void clearListener() {
        map.clear();
    }
}
