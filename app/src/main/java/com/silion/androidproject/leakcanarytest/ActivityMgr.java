package com.silion.androidproject.leakcanarytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silion on 2017/8/11.
 */

public class ActivityMgr {
    private static ActivityMgr sInstance = new ActivityMgr();
    private List<Activity> mActivities = new ArrayList<>();

    private ActivityMgr() {
    }

    public static ActivityMgr getInstance() {
        return sInstance;
    }

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }
}
