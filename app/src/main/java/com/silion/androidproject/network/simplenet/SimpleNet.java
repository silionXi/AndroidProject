package com.silion.androidproject.network.simplenet;

import com.silion.androidproject.network.simplenet.core.RequestQueue;
import com.silion.androidproject.network.simplenet.httpstacks.HttpStack;

/**
 * Created by silion on 2017/2/27.
 */

public final class SimpleNet {
    /**
     * 创建一个请求队列,NetworkExecutor数量为默认的数量
     *
     * @return
     */
    public static RequestQueue newRequestQueue() {
        return newRequestQueue(RequestQueue.DEFAULT_CORE_NUMS);
    }

    /**
     * 创建一个请求队列,NetworkExecutor数量为coreNums
     *
     * @param coreNums
     * @return
     */
    public static RequestQueue newRequestQueue(int coreNums) {
        return newRequestQueue(coreNums, null);
    }

    /**
     * 创建一个请求队列,NetworkExecutor数量为coreNums
     *
     * @param coreNums  线程数量
     * @param httpStack 网络执行者
     * @return
     */
    public static RequestQueue newRequestQueue(int coreNums, HttpStack httpStack) {
        RequestQueue queue = new RequestQueue(Math.max(0, coreNums), httpStack);
        queue.start();
        return queue;
    }
}
