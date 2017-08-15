package com.silion.androidproject.thread;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by silion on 2017/8/14.
 */

public class Toast {
    public enum Status {
        DRY, BUTTERED, JAMMED
    }

    private Status mStatus = Status.DRY;
    private final int id;

    public Toast(int id) {
        this.id = id;
    }

    public void butter() {
        mStatus = Status.BUTTERED;
    }

    public void jam() {
        mStatus = Status.JAMMED;
    }

    public Status getStatus() {
        return mStatus;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Toast" + id + ":" + mStatus;
    }
}

