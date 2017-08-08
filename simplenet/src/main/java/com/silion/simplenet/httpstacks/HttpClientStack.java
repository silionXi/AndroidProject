package com.silion.simplenet.httpstacks;

import com.silion.simplenet.base.Request;
import com.silion.simplenet.base.Response;

/**
 * 使用HttpClient进行网络请求, 因为在Android中, HttpClient已被移除, 因此不实现
 *
 * @author silion
 */

public class HttpClientStack implements HttpStack {
    @Override
    public Response performRequest(Request request) {
        return null;
    }
}
