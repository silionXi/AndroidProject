package com.silion.lsllibrary.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by silion on 2017/7/4.
 */

public class BitmapUtil {
    /**
     * 将一个Bitmap保存为文件
     *
     * @param bitmap 需要保存的bitmap
     * @param path 保存的文件路径例如：/sdcard/screenshot.png
     */
    public static void saveBitmap2File(Bitmap bitmap, String path) {
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }
}
