package com.silion.androidproject.network.simplenet.core;

import android.util.Log;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.httpstacks.HttpStack;
import com.silion.androidproject.network.simplenet.httpstacks.HttpStackFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by silion on 2017/2/24.
 */

public class RequestQueue {
    private static final String TAG = "RequestQueue";
    // 线程安全的请求队列
    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<Request<?>>();
    // 请求的序列号生成器
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);
    // 默认的核心数 为CPU格式+1
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    // CPU核心数 + 1个分发线程数
    private int mDispatcherNums = DEFAULT_CORE_NUMS;
    // NetworkExecutor，执行网络请求的线程
    private NetWorkExecutor[] mDispatchers = null;
    // Http请求的真正执行者
    private HttpStack mHttpStack;

    public RequestQueue(int coreNums, HttpStack httpStack) {
        mDispatcherNums = coreNums;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    /**
     * 启动NetworkExecutor
     */
    private final void startNetworkExecutors() {
        mDispatchers = new NetWorkExecutor[mDispatcherNums];
        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetWorkExecutor(mRequestQueue, mHttpStack);
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
     * 不能重复添加请求
     *
     * @param request
     */
    public void addRequest(Request<?> request) {
        if (!mRequestQueue.contains(request)) {
            request.setSerialNum(this.generateSerialNumber());
            mRequestQueue.add(request);
        } else {
            Log.d(TAG, "### 请求队列中已经含有");
        }
    }

    public void clear() {
        mRequestQueue.clear();
    }

    public BlockingQueue<Request<?>> getAllRequests() {
        return mRequestQueue;
    }

    /**
     * 为每个请求生成一个系列号
     *
     * @return 序列号
     */
    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
