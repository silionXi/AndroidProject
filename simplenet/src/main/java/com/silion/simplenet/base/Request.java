package com.silion.simplenet.base;

import com.silion.lsllibrary.utils.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求类,注意GET和DELETE不能传递请求参数,因为其请求的性质所致,用户可以将参数构建到url后传递进来到Request中.
 *
 * @author silion
 */
public abstract class Request<T> implements Comparable<Request<T>> {
    /**
     * Default encoding for POST or PUT parameters. See
     * {@link #getParamsEncoding()}.
     */
    public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    /**
     * Default Content-type
     */
    public final static String HEADER_CONTENT_TYPE = "Content-Type";
    /**
     * 请求序列号
     */
    protected int mSerialNum = 0;
    /**
     * 优先级默认设置为Normal
     */
    protected Priority mPriority = Priority.NORMAL;
    /**
     * 是否取消该请求
     */
    protected boolean isCancel = false;
    /**
     * 该请求是否应该缓存
     */
    private boolean mShouldCache = true;
    // 请求Listener
    private RequestListener mListener;
    // 请求的方法
    private HttpMethod mHttpMethod = HttpMethod.GET;
    // 请求的Url
    private String mUrl;
    // 请求头
    private Map<String, String> mRequestHeaders = new HashMap<>();
    // 请求参数属性
    private Map<String, String> mBodyProperty = new HashMap<>();
    // 请求参数
    private Map<String, String> mBodyParams = new HashMap<>();

    /**
     * Http请求方法枚举, 这里只有GET, POST, DELETE, PUT 4种
     */
    public enum HttpMethod {
        GET, POST, DELETE, PUT
    }

    /**
     * 请求优先级枚举
     */
    public enum Priority {
        LOW, NORMAL, HIGH, IMMEDIATELY
    }

    public Request(HttpMethod httpMethod, String url, RequestListener listener) {
        mHttpMethod = httpMethod;
        mUrl = url;
        mListener = listener;
    }

    public void addHeader(String name, String value) {
        mRequestHeaders.put(name, value);
    }

    /**
     * 处理Response,该方法运行在UI线程.
     *
     * @param response
     */
    public void deliveryResponse(Response response) {
        if (mListener != null) {
            if (response.isSuccess()) {
                T result = parseResponse(response.getResult());
                mListener.onSuccess(result);
            } else {
                mListener.onExecption(response.getResult());
            }
        }
    }

    // 从原生的网络请求中解析结果, 子类必须覆盖
    protected abstract T parseResponse(String response);

    public boolean isHttps() {
        return mUrl.startsWith("https");
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        mHttpMethod = httpMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Map<String, String> getRequestHeaders() {
        return mRequestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        mRequestHeaders = requestHeaders;
    }

    public Map<String, String> getBodyProperty() {
        return mBodyProperty;
    }

    public void setBodyProperty(Map<String, String> bodyProperty) {
        mBodyProperty = bodyProperty;
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

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public boolean isShouldCache() {
        return mShouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        mShouldCache = shouldCache;
    }

    public byte[] getBodyParams() {
        String body = JsonUtil.objectToJsonString(mBodyParams);
        try {
            return body.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置请求参数
     * 如果请求方式为GET, 把参数拼接到url
     *
     * @param bodyParams
     */
    public void setBodyParams(Map<String, String> bodyParams) {
        switch (mHttpMethod) {
            case GET:
                mUrl = mUrl + mapToParamString(bodyParams);
                break;
            case POST:
                mBodyParams = bodyParams;
                break;
            default:
                break;
        }
    }

    private String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    private String mapToParamString(Map<String, String> bodyParams) {
        StringBuilder sb = new StringBuilder();
        if (bodyParams != null & !bodyParams.isEmpty()) {
            sb.append("?");
        }
        int i = 0;
        for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
            if (i > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            try {
                sb.append(URLEncoder.encode(entry.getValue(), getParamsEncoding()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Request<T> o) {
        Priority priority = o.getPriority();
        return mPriority.equals(priority) ? mSerialNum - o.getSerialNum() : mPriority.ordinal() - priority.ordinal();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mRequestHeaders == null) ? 0 : mRequestHeaders.hashCode());
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
        if (mRequestHeaders == null) {
            if (other.mRequestHeaders != null)
                return false;
        } else if (!mRequestHeaders.equals(other.mRequestHeaders))
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

    /**
     * 网络请求listener, 会被执行在UI线程
     */
    interface RequestListener<T> {
        void onSuccess(T response);

        void onExecption(String errorMsg);
    }
}
