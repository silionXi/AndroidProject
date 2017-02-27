package com.silion.androidproject.network.simplenet.cache;

import com.silion.androidproject.network.simplenet.base.Response;

/**
 * Created by silion on 2017/2/27.
 */

public class LruMemCache implements Cache<String, Response> {
    @Override
    public Response get(String key) {
        return null;
    }

    @Override
    public void put(String key, Response value) {

    }

    @Override
    public void remove(String key) {

    }
}
