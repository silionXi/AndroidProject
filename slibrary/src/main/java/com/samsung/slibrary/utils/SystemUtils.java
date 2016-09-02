package com.samsung.slibrary.utils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by silion on 2016/9/1.
 */
public class SystemUtils {

    private SystemUtils() {
        throw new AssertionError();
    }

    public static boolean isRootedByFile() {
        boolean isRoot = false;
        try {
            if (isFileExists("/system/bin/su") || isFileExists("/system/xbin/su") ||
                    isFileExists("/system/app/superuser.apk") || isFileExists("/system/app/SuperSU.apk")) {
                isRoot = true;
            } else {
                if(isFileExists("data/data/com.noshufou.android.su")) {
                    isRoot = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRoot;
    }

    public static boolean isFileExists(String path) {
        return new File(path).exists();
    }

    public static String getBuildPDA() {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class);
            return (String) method.invoke(clazz, "ro.build.PDA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
