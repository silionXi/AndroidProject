package com.silion.androidproject.otto;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.silion.androidproject.MyApplication;

public class SmsService extends Service {
    private static final String TAG = "SmsService";
    private SmsReceiver mRecesiver;

    public SmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "silion SmsService onCreate");
        mRecesiver = new SmsReceiver();
        MyApplication.getBus().register(mRecesiver); // 给SmsReceiver广播接收者注册Bus
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "silion SmsService onDestroy");
        if (mRecesiver != null) {
            MyApplication.getBus().unregister(mRecesiver); // 取消注册
        }
    }
}
