package com.silion.simplenet.core;

import android.util.Log;

import com.silion.simplenet.base.Request;
import com.silion.simplenet.httpstacks.HttpStack;
import com.silion.simplenet.httpstacks.HttpStackFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请求队列, 使用优先队列,使得请求可以按照优先级进行处理. [ Thread Safe ]
 *
 * @author silion
 */

public class RequestQueue {
    private static final String TAG = "RequestQueue";
    // 使用阻塞队列(<Android开发进阶> P88)实现请求队列 [ Thread-safe ]
    private BlockingQueue<Request> mRequestQueue = new PriorityBlockingQueue<>();
    // 请求的序列化生成器
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);
    // HTTP请求真正执行者
    private HttpStack mHttpStack;

    // 默认的核心数
    private int mDispatcherNums;
    // NetworkExecutor,执行网络请求的线程
    private NetworkExecutor[] mDispatchers;

    public RequestQueue(int coreNum, HttpStack httpStack) {
        mDispatcherNums = coreNum;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    public void startNetworkExecutors() {
        mDispatchers = new NetworkExecutor[mDispatcherNums];
        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetworkExecutor(mRequestQueue, mHttpStack);
            mDispatchers[i].start();
        }
    }

    public void start() {
        stop();
        startNetworkExecutors();
    }

    /**
     * 停止NetworkExecutor
     */
    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].quit();
            }
        }
    }

    /**
     * 添加一个请求到请求队列
     *
     * @param request
     */
    public void addRequest(Request request) {
        if (mRequestQueue.contains(request)) {
            Log.e(TAG, "addRequest 不能添加重复的请求");
        } else {
            request.setSerialNum(this.generateSerialNumber());
            mRequestQueue.add(request);
        }
    }

    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }

    public void clear() {
        mRequestQueue.clear();
    }

    public BlockingQueue<Request> getAllRequests() {
        return mRequestQueue;
    }
}
