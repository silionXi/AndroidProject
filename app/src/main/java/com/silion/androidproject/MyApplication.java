package com.silion.androidproject;

import android.app.Application;
import android.content.Context;

/**
 * Created by silion on 2017/1/23.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
