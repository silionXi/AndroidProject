package com.silion.androidproject;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by silion on 2017/1/23.
 */

public class MyApplication extends LitePalApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
