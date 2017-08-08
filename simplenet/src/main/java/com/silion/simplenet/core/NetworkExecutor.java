package com.silion.simplenet.core;

import com.silion.simplenet.base.Request;
import com.silion.simplenet.base.Response;
import com.silion.simplenet.cache.Cache;
import com.silion.simplenet.cache.LruMemCache;
import com.silion.simplenet.httpstacks.HttpStack;

import java.util.concurrent.BlockingQueue;

/**
 * 网络请求Executor,继承自Thread,从网络请求队列中循环读取请求并且执行
 *
 * @author silion
 */

public class NetworkExecutor extends Thread {
    private BlockingQueue<Request> mRequestQueue;
    private HttpStack mHttpStack;
    private ResponseDelivery mDelivery = new ResponseDelivery();
    /**
     * 请求缓存
     */
    private static Cache<String, Response> mReqCache = new LruMemCache();
    /**
     * 是否停止
     */
    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue requestQueue, HttpStack httpStack) {
        mRequestQueue = requestQueue;
        mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                Request request = mRequestQueue.take();
                if (request.isCancel()) {
                    continue;
                }
                Response response;
                if (isUseCache(request)) {
                    response = mReqCache.get(request.getUrl());
                } else {
                    response = mHttpStack.performRequest(request);
                    // 如果该请求需要缓存,那么请求成功则缓存到mResponseCache中
                    if (request.isShouldCache() && response.isSuccess()) {
                        mReqCache.put(request.getUrl(), response);
                    }
                }

                // 分发结果
                mDelivery.deliveryResponse(request, response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isUseCache(Request request) {
        return request.isShouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit() {
        isStop = true;
        interrupt();
    }
}
