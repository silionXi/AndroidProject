package com.silion.androidproject;

import android.app.Application;
import android.content.Context;

import com.silion.androidproject.otto.MainThreadBus;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.otto.Bus;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePalApplication;

/**
 * Created by silion on 2017/1/23.
 */

public class MyApplication extends LitePalApplication {
    private static Context mContext;
    private static RefWatcher mRefWatcher;
    private static Bus mBus = new MainThreadBus();

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
        } else {
            mRefWatcher = LeakCanary.install(this);

        }
        ZXingLibrary.initDisplayOpinion(this);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public static Bus getBus() {
        return mBus;
    }
}
