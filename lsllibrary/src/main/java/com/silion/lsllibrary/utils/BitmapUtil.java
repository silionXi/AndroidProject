package com.silion.lsllibrary.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

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
     * @param path   保存的文件路径例如：/sdcard/screenshot.png
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

    public static Bitmap decodeSampleBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSimpleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(resources, resId, options);
        return bitmap;
    }

    private static int calculateInSimpleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 2;
        }

        int simpleSize = 1;

        int width = options.outWidth / 2;
        int height = options.outHeight / 2;

        while (width / simpleSize >= reqWidth && height / simpleSize >= reqHeight) {
            simpleSize = simpleSize * 2;
        }

        return simpleSize;
    }
}
