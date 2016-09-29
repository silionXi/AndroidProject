package com.silion.androidproject.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.samsung.slibrary.utils.MD5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by silion on 2016/9/28.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private Context mContext;
    private String mCachePath;
    private LruCache<String, Bitmap> mCache;

    public BitmapCache(Context context) {
        mContext = context;
        mCachePath = mContext.getExternalCacheDir().getPath();
        int maxSize = 50 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        String fileName = MD5Utils.encode(url);
        Bitmap bitmap;

        // 从内存读
        bitmap = mCache.get(fileName);

        // 从本地读
        if (bitmap == null) {
            bitmap = getBitmapFromLocal(fileName);
        }

        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String fileName = MD5Utils.encode(url);

        // 缓存到内存
        mCache.put(fileName, bitmap);

        // 缓存到本地
        putBitmapToLocal(fileName, bitmap);
    }

    private void putBitmapToLocal(String fileName, Bitmap bitmap) {
        File file = new File(mCachePath, fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromLocal(String fileName) {
        File file = new File(mCachePath, fileName);
        if (file.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
