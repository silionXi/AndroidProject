package com.silion.lsllibrary.utils;

import com.google.gson.Gson;

/**
 * Created by silion on 2017/8/4.
 */

public class JsonUtil {
    public JsonUtil() {
    }

    public static String objectToJsonString(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
