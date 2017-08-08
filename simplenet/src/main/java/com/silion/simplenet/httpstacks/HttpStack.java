package com.silion.simplenet.httpstacks;

import com.silion.simplenet.base.Request;
import com.silion.simplenet.base.Response;

/**
 * 执行网络请求的接口
 *
 * @author silion
 */

public interface HttpStack {
    /**
     * 执行HTTP请求
     *
     * @param request HTTP请求
     * @return 返回Response
     */
    Response performRequest(Request request);
}
