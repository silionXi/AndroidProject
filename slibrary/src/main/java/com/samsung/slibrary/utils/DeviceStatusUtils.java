package com.samsung.slibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by silion on 2016/9/7.
 */
public class DeviceStatusUtils {

    /**
     * 网络是否可能用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        return !(networkInfo == null || !networkInfo.isConnectedOrConnecting());
    }
}
