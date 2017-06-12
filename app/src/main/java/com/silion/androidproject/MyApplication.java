package com.silion.androidproject;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePalApplication;

/**
 * Created by silion on 2017/1/23.
 */

public class MyApplication extends LitePalApplication {
    private static Context mContext;
    private static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }
}
