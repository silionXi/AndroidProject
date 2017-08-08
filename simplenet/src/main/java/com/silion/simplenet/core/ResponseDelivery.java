package com.silion.simplenet.core;

import android.os.Handler;
import android.os.Looper;

import com.silion.simplenet.base.Request;
import com.silion.simplenet.base.Response;

import java.util.concurrent.Executor;

/**
 * 请求结果投递类,将请求结果投递给UI线程
 *
 * @author silion
 */

public class ResponseDelivery implements Executor {
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }

    public void deliveryResponse(final Request request, final Response response) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };
        execute(runnable);
    }
}
