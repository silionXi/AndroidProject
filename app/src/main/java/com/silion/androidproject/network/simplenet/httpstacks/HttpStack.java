package com.silion.androidproject.network.simplenet.httpstacks;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;

/**
 * Created by silion on 2017/2/24.
 */

public interface HttpStack {
    /**
     * 执行Http请求
     *
     * @param request 待执行的请求
     * @return
     */
    public Response performRequest(Request<?> request);
}
