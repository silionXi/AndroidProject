package com.silion.androidproject.network.simplenet.core;

import android.os.Handler;
import android.os.Looper;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;

import java.util.concurrent.Executor;

/**
 * Created by silion on 2017/2/27.
 */

public class ResponseDelivery implements Executor {
    /**
     * 主线程的hander
     */
    Handler mResponseHandler = new Handler(Looper.getMainLooper());

    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable responRunnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };

        execute(responRunnable);
    }

    @Override
    public void execute(Runnable command) {
        mResponseHandler.post(command);
    }
}
