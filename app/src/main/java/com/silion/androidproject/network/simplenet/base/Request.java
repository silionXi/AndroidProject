package com.silion.androidproject.network.simplenet.base;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silion on 2017/2/24.
 */

public abstract class Request<T> implements Comparable<Request<T>> {
    private static final String TAG = "Request";

    public enum HttpMethod {
        GET("GET"),
        POST("PHST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String mHttpMethod = "";

        HttpMethod(String method) {
            mHttpMethod = method;
        }


        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE;
    }

    // 默认的编码方式
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    // Default Content-Tpye
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    // 请求序列号
    protected int mSerialNum = 0;
    // 优先级默认设置为NORMAL
    protected Priority mPriority = Priority.NORMAL;
    // 是否取消该请求
    protected boolean isCancel = false;
    // 该请求是否应该缓存
    private boolean mShouldCache = true;
    // 请求Listener
    protected RequestListener<T> mRequestListener;
    // 请求的URL
    private String mUrl = "";
    // 请求的方法
    HttpMethod mHttpMethod = HttpMethod.GET;
    // 请求的header
    private Map<String, String> mHeaders = new HashMap<>();
    // 请求参数
    private Map<String, String> mBodyParams = new HashMap<>();

    /**
     * @param method
     * @param url
     * @param listener
     */
    public Request(HttpMethod method, String url, RequestListener<T> listener) {
        mHttpMethod = method;
        mUrl = url;
        mRequestListener = listener;
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    // 从原生的网络请求中解析结果，子类必须复写
    public abstract T parseResponse(Response response);

    // 处理Response，该方法需要运行在UI线程，且子类不可以复写
    public final void deliveryResponse(Response response) {
        // 解析得到请求结果
        T result = parseResponse(response);
        if (mRequestListener != null) {
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unknow error";
            Log.e(TAG, "### 执行回调 : stCode = " + stCode + ", result : " + result + ", error : " + msg);
            mRequestListener.onComplete(stCode, result, msg);
        }
    }

    // 返回POST或者PUT请求时的Body参数字节数组
    public byte[] getBody() {
        Map<String, String> params = getParam();
        if (params != null && params.size() > 0) {
            return encodParameters(params, getParamsEncoding());
        }
        return null;
    }

    private byte[] encodParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }

    //用于对请求的排序处理，根据优先级和加入到队列的序号进行排序
    @Override
    public int compareTo(Request<T> o) {
        Priority myPriority = this.getPriority();
        Priority oPriority = o.getPriority();
        //如果优先级相等，那么按照添加到队列的序列号顺序来执行
        return myPriority.equals(oPriority) ? this.getSerialNum() - o.getSerialNum() : myPriority.ordinal() - oPriority.ordinal();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mHeaders == null) ? 0 : mHeaders.hashCode());
        result = prime * result + ((mHttpMethod == null) ? 0 : mHttpMethod.hashCode());
        result = prime * result + ((mBodyParams == null) ? 0 : mBodyParams.hashCode());
        result = prime * result + ((mPriority == null) ? 0 : mPriority.hashCode());
        result = prime * result + (mShouldCache ? 1231 : 1237);
        result = prime * result + ((mUrl == null) ? 0 : mUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Request<?> other = (Request<?>) obj;
        if (mHeaders == null) {
            if (other.mHeaders != null)
                return false;
        } else if (!mHeaders.equals(other.mHeaders))
            return false;
        if (mHttpMethod != other.mHttpMethod)
            return false;
        if (mBodyParams == null) {
            if (other.mBodyParams != null)
                return false;
        } else if (!mBodyParams.equals(other.mBodyParams))
            return false;
        if (mPriority != other.mPriority)
            return false;
        if (mShouldCache != other.mShouldCache)
            return false;
        if (mUrl == null) {
            if (other.mUrl != null)
                return false;
        } else if (!mUrl.equals(other.mUrl))
            return false;
        return true;
    }

    public int getSerialNum() {
        return mSerialNum;
    }

    public void setSerialNum(int serialNum) {
        mSerialNum = serialNum;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void cancel() {
        isCancel = true;
    }

    public boolean isShouldCache() {
        return mShouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        mShouldCache = shouldCache;
    }

    public RequestListener<T> getRequestListener() {
        return mRequestListener;
    }

    public void setRequestListener(RequestListener<T> requestListener) {
        mRequestListener = requestListener;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        mHttpMethod = httpMethod;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getParam() {
        return mBodyParams;
    }

    public void setParam(Map<String, String> bodyParam) {
        mBodyParams = bodyParam;
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public boolean isHttps() {
        return mUrl.startsWith("https");
    }

    public interface RequestListener<T> {
        // 请求完成的回调
        public void onComplete(int stCode, T response, String errMsg);
    }
}
