package com.silion.androidproject.databinding;

/**
 * Created by silion on 2017/11/9.
 */

public class Task {
    private String mTaskName;

    public Task(String taskName) {
        mTaskName = taskName;
    }

    public void run() {
        android.util.Log.d("silion", "Task.run() mTaskName = " + mTaskName);
    }
}
