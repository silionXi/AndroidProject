package com.silion.androidproject.network.simplenet.requests;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;

/**
 * Created by silion on 2017/2/27.
 */

public class StringRequest extends Request<String> {
    /**
     * @param method
     * @param url
     * @param listener
     */
    public StringRequest(HttpMethod method, String url, RequestListener<String> listener) {
        super(method, url, listener);
    }

    @Override
    public String parseResponse(Response response) {
        return new String(response.getRawData());
    }
}
