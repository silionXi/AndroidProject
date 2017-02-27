package com.silion.androidproject.network.simplenet.core;

import android.util.Log;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;
import com.silion.androidproject.network.simplenet.cache.Cache;
import com.silion.androidproject.network.simplenet.cache.LruMemCache;
import com.silion.androidproject.network.simplenet.httpstacks.HttpStack;

import java.util.concurrent.BlockingQueue;

/**
 * Created by silion on 2017/2/24.
 */

public final class NetWorkExecutor extends Thread {
    private static final String TAG = "NetWorkExecutor";
    /**
     * 网络请求队列
     */
    private BlockingQueue<Request<?>> mRequestQueue;
    /**
     * 网络请求栈
     */
    private HttpStack mHttpStack;
    /**
     * 结果分发器,将结果投递到主线程
     */
    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();
    /**
     * 请求缓存
     */
    private static Cache<String, Response> mReqCache = new LruMemCache();
    /**
     * 是否停止
     */
    private boolean isStop = false;

    public NetWorkExecutor(BlockingQueue<Request<?>> queue, HttpStack httpStack) {
        mRequestQueue = queue;
        mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                final Request<?> request = mRequestQueue.take();
                if (request.isCancel()) {
                    Log.d(TAG, "### 取消执行了");
                    continue;
                }
                Response response;
                if (isUseCache(request)) {
                    // 从缓存中取
                    response = mReqCache.get(request.getUrl());
                } else {
                    // 从网络上获取数据
                    response = mHttpStack.performRequest(request);
                    if (request.isShouldCache() && isSuccess(response)) {
                        mReqCache.put(request.getUrl(), response);
                    }
                }

                // 分发请求结果
                mResponseDelivery.deliveryResponse(request, response);
            }
        } catch (InterruptedException e) {
            Log.w(TAG, "### 请求分发器退出");
        }
    }

    private boolean isSuccess(Response response) {
        return response != null && response.getStatusCode() == 200;
    }

    private boolean isUseCache(Request<?> request) {
        return request.isShouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit() {
        isStop = true;
        interrupt();
    }
}
