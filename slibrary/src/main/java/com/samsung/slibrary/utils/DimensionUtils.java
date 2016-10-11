package com.samsung.slibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.silion.slibrary.R;

/**
 * Created by silion on 2016/9/29.
 */
public class DimensionUtils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
