package com.silion.androidproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.silion.androidproject.otto.MainThreadBus;
import com.silion.lsllibrary.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.otto.Bus;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePalApplication;

/**
 * Created by silion on 2017/1/23.
 */

public class MyApplication extends LitePalApplication {
    private static final String TAG = "MyApplication";
    private static MyApplication mApplication = null;
    private static Context mContext;
    private static RefWatcher mRefWatcher;
    private static Bus mBus = new MainThreadBus();

    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onCreated");
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onDestroyed");
        }
    };

    /**
     * silion_comment:
     * 不应该在构造方法里初始化一些需要Context引用操作得到的数据，例如：
     * getResources(), getPackageName, getSystemService()等
     * 因为在{@link #attachBaseContext(Context)}}才传入Context给mBase
     */
    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * silion_comment:
         * 正确打开Application单例模式的姿势
         * 1. 定义静态成员变量：private static MyApplication mApplication = null;
         * 2. 在onCreate赋值：mApplication = this;
         * 3. 静态方法提供实例：{@link #getApplication()}
         */
        mApplication = this;

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

        initLeakCanary();

        // 初始化Logger 日志打印
        Logger.init();
        Logger.d("hello");

        ZXingLibrary.initDisplayOpinion(this);
        mContext = getApplicationContext();
    }

    /**
     * 初始化内存泄漏检测工具LeakCanary
     * 1. build.gradle
     * dependencies {
     *   debugCompile 'com.squareup.leakcanary:leakcanary-android:1.5.4'
     *   releaseCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5.4'
     * }
     * 2.初始化
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
        } else {
            mRefWatcher = LeakCanary.install(this);

        }
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        super.onTerminate();
    }

    public static MyApplication getApplication() {
        return mApplication;
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
