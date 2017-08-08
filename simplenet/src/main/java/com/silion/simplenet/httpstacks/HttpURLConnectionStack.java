package com.silion.simplenet.httpstacks;

import com.silion.simplenet.base.Request;
import com.silion.simplenet.base.Response;
import com.silion.simplenet.config.HttpUrlConnectionConfig;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * 使用HttpURLConnection进行网络请求
 *
 * @author silion
 */
public class HttpURLConnectionStack implements HttpStack {
    HttpUrlConnectionConfig mConfig = HttpUrlConnectionConfig.getConfig();

    @Override
    public Response performRequest(Request request) {
        String strUrl = request.getUrl();
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(strUrl).openConnection();
            connection.setRequestMethod(request.getHttpMethod().name());
            connection.setConnectTimeout(mConfig.conTimeOut);
            connection.setReadTimeout(mConfig.ioTimeOut);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            setRequestHeaders(connection, request);

            setRequestBody(connection, request);

            configHttps(request);

            return getResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    private Response getResponse(HttpURLConnection connection) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        Response response = null;
        try {
            int statusCode = connection.getResponseCode();
            if (isSuccessful(statusCode)) {
                response.setSuccess(true);
                inputStream = connection.getInputStream();
            } else {
                response.setSuccess(false);
                inputStream = connection.getErrorStream();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            response.setResult(sb.toString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean isSuccessful(int statusCode) {
        return (statusCode >= HttpURLConnection.HTTP_OK && statusCode <= HttpURLConnection.HTTP_BAD_REQUEST);
    }

    private void configHttps(Request request) {
        if (request.isHttps()) {
            SSLSocketFactory sslFactory = mConfig.getSslSocketFactory();
            // 配置https
            if (sslFactory != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslFactory);
                HttpsURLConnection.setDefaultHostnameVerifier(mConfig.getHostnameVerifier());
            }
        }
    }

    private void setRequestBody(HttpURLConnection connection, Request request) {
        // 如果是POST请求, 提交参数
        if (request.getHttpMethod() == Request.HttpMethod.POST) {
            connection.setDoOutput(true);
            byte[] bodyParams = request.getBodyParams();
            BufferedOutputStream bufferedOutputStream = null;
            try {
                bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
                bufferedOutputStream.write(bodyParams);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void setRequestHeaders(HttpURLConnection connection, Request request) {
        Map<String, String> map = request.getRequestHeaders();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }


}
