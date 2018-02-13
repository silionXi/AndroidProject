package com.silion.androidproject.leakcanarytest;

import android.content.Context;
import android.view.View;

/**
 * Created by silion on 2018/2/13.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        init();
    }

    private void init() {
    }

    public void setOnTestListener(OnTestListener listener) {
        ListenerCollector.addListener(this, listener);
    }

    public void removeListener() {
        ListenerCollector.clearListener();
    }

    public interface OnTestListener {
        void test();
    }


}
