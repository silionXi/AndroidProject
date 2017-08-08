package com.silion.simplenet.cache;

import android.util.LruCache;

import com.silion.simplenet.base.Response;

/**
 * Created by silion on 2017/8/8.
 */

public class LruMemCache implements Cache<String, Response> {
    /**
     * Reponse缓存
     */
    private LruCache<String, Response> mResponseCache;

    public LruMemCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String, Response>(cacheSize) {
            @Override
            protected int sizeOf(String key, Response value) {
                return value.getResult().getBytes().length / 1024;
            }
        };
    }

    @Override
    public Response get(String key) {
        return mResponseCache.get(key);
    }

    @Override
    public void put(String key, Response value) {
        mResponseCache.put(key, value);
    }

    @Override
    public Response remove(String key) {
        return mResponseCache.remove(key);
    }
}
