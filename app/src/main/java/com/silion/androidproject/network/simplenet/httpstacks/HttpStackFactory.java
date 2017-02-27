package com.silion.androidproject.network.simplenet.httpstacks;

import android.os.Build;

/**
 * Created by silion on 2017/2/24.
 */

public final class HttpStackFactory {
    private static final int GINGERBREAD_SDK_NUM = 9;

    public static HttpStack createHttpStack() {
        int runtimeSDKApi = Build.VERSION.SDK_INT;
        if (runtimeSDKApi >= GINGERBREAD_SDK_NUM) {
            return new HttpUrlConnStack();
        }

        return new HttpClientStack();
    }
}
