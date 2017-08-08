package com.silion.simplenet.httpstacks;

import android.os.Build;

/**
 * 根据api版本选择HttpClient或者HttpURLConnection
 *
 * @author silion
 */

public class HttpStackFactory {

    public static HttpStack createHttpStack() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            return new HttpClientStack();
        } else {
            return new HttpURLConnectionStack();
        }
    }
}
