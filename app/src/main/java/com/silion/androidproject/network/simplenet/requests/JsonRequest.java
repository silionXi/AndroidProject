package com.silion.androidproject.network.simplenet.requests;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by silion on 2017/2/24.
 */

public class JsonRequest extends Request<JSONObject> {
    /**
     * @param method
     * @param url
     * @param listener
     */
    public JsonRequest(HttpMethod method, String url, RequestListener listener) {
        super(method, url, listener);
    }

    /**
     * 将Response的结果转化为JSONObject
     *
     * @param response
     * @return
     */
    @Override
    public JSONObject parseResponse(Response response) {
        String jsonString = new String(response.getRawData());
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
