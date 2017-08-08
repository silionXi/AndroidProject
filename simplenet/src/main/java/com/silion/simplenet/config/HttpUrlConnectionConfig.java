package com.silion.simplenet.config;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * 这是针对于使用HttpUrlStack执行请求时为https请求设置的SSLSocketFactory和HostnameVerifier的配置类
 * 参考
 * http://blog.csdn.net/xyz_lmn/article/details/8027334,
 * http://www.cnblogs.com/vus520/archive/2012/09/07/2674725.html
 *
 * @author silion
 */

public class HttpUrlConnectionConfig extends HttpConfig {
    private static HttpUrlConnectionConfig sConfig = new HttpUrlConnectionConfig();

    private SSLSocketFactory mSslSocketFactory = null;
    private HostnameVerifier mHostnameVerifier = null;

    private HttpUrlConnectionConfig() {
    }

    public static HttpUrlConnectionConfig getConfig() {
        return sConfig;
    }

    /**
     * 配置https请求的SSLSocketFactory与HostnameVerifier
     *
     * @param sslSocketFactory
     * @param hostnameVerifier
     */
    public void setHttpsConfig(SSLSocketFactory sslSocketFactory,
                               HostnameVerifier hostnameVerifier) {
        mSslSocketFactory = sslSocketFactory;
        mHostnameVerifier = hostnameVerifier;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return mSslSocketFactory;
    }
}
