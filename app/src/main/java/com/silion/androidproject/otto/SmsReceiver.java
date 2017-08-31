package com.silion.androidproject.otto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.silion.androidproject.MyApplication;

/**
 * Created by silion on 2017/8/30.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "silion SmsReceiver onReceive");
        Bundle bundle = intent.getExtras();
        //获取链路层的协议数据单元
        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];
        // Create messages for each incoming PDU
        for (int n = 0; n < messages.length; n++) {
            sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }
        for (SmsMessage msg : sms) {
            //TODO: 这里应该加上你自己的过滤条件，比如手机号，短信内容
            //尽可能的拦截短信，这个命令在MIUI，flyme上都没有用
            abortBroadcast();
            Log.d(TAG, "silion SmsReceiver Bus post");
            MyApplication.getBus().post(msg);
        }
    }
}
