package com.silion.androidproject.network.simplenet.httpstacks;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;
import com.silion.androidproject.network.simplenet.config.HttpUrlConnConfig;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by silion on 2017/2/24.
 */

public class HttpUrlConnStack implements HttpStack {
    /**
     * 配置Https
     */
    HttpUrlConnConfig mConfig = HttpUrlConnConfig.getConfig();

    @Override
    public Response performRequest(Request<?> request) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = createUrlConnection(request.getUrl());
            setRequestHeaders(urlConnection, request);
            setRequestParams(urlConnection, request);
            configHttps(request);
            return fetchResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private HttpURLConnection createUrlConnection(String url) throws IOException {
        URL newURL = new URL(url);
        URLConnection urlConnection = newURL.openConnection();
        urlConnection.setConnectTimeout(mConfig.connTimeOut);
        urlConnection.setReadTimeout(mConfig.soTimeOut);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(false);
        return (HttpURLConnection) urlConnection;
    }

    private void configHttps(Request<?> request) {
        if (request.isHttps()) {
            SSLSocketFactory sslSocketFactory = mConfig.getSslSocketFactory();
            if (sslSocketFactory != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
                HttpsURLConnection.setDefaultHostnameVerifier(mConfig.getHostnameVerifier());
            }
        }
    }

    private void setRequestHeaders(HttpURLConnection connection, Request<?> request) {
        Set<String> headersKeys = request.getHeaders().keySet();
        for (String headerName : headersKeys) {
            connection.addRequestProperty(headerName, request.getHeaders().get(headerName));
        }
    }

    protected void setRequestParams(HttpURLConnection connection, Request<?> request) throws IOException {
        Request.HttpMethod method = request.getHttpMethod();
        connection.setRequestMethod(method.toString());
        byte[] body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty(Request.HEADER_CONTENT_TYPE, request.getBodyContentType());
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body);
            dataOutputStream.close();
        }
    }

    private Response fetchResponse(HttpURLConnection connection) throws IOException {
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        StatusLine responseStatus = new BasicStatusLine(protocolVersion, responseCode, connection.getResponseMessage());
        Response response = new Response(responseStatus);
        response.setEntity(entityFromURLConnection(connection));
        addHeadersToResponse(response, connection);
        return response;
    }

    private HttpEntity entityFromURLConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = connection.getErrorStream();
        }

        // TODO: 2017/2/27 GZIP
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());

        return entity;
    }

    private void addHeadersToResponse(BasicHttpResponse response, HttpURLConnection connection) {
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                Header h = new BasicHeader(header.getKey(), header.getValue().get(0));
                response.addHeader(h);
            }
        }
    }
}
