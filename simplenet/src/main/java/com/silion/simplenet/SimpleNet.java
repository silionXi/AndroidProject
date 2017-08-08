package com.silion.simplenet;

import com.silion.simplenet.core.RequestQueue;
import com.silion.simplenet.httpstacks.HttpStack;

/**
 * 参考<Android开发进阶>P130 实现SimpleNet框架
 * SimpleNet 入口
 *
 * @author silion
 */

public class SimpleNet {
    // CPU核心数 + 1个分发线程数
    private static int DEFAULT_CORE_NUM = Runtime.getRuntime().availableProcessors() + 1;

    public static RequestQueue newRequest() {
        return newRequest(DEFAULT_CORE_NUM);
    }

    public static RequestQueue newRequest(int coreNum) {
        return newRequest(coreNum, null);
    }

    /**
     * 创建一个请求队列,NetworkExecutor数量为coreNums
     *
     * @param coreNum   线程数量
     * @param httpStack 网络执行者
     * @return 返回队列
     */
    public static RequestQueue newRequest(int coreNum, HttpStack httpStack) {
        RequestQueue requestQueue = new RequestQueue(coreNum, httpStack);
        requestQueue.start();
        return requestQueue;
    }
}
