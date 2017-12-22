package com.silion.lsllibrary.logger;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by silion on 2017/12/22.
 */

public class Logger {
    /**
     * 1. 在Application进行初始化
     * MyApplication.onCreate() - Logger.init();
     */
    public static void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(7)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Hides internal method calls up to offset. Default 5
                // .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("silion")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    public static void d(Object object) {
        com.orhanobut.logger.Logger.d(object);
        com.orhanobut.logger.Logger.d("debug");
        com.orhanobut.logger.Logger.e("error");
        com.orhanobut.logger.Logger.w("warning");
        com.orhanobut.logger.Logger.v("verbose");
        com.orhanobut.logger.Logger.i("information");
        com.orhanobut.logger.Logger.wtf("wtf!!!!");
    }
}
