package com.silion.androidproject.network.simplenet.base;


import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by silion on 2017/2/24.
 */

public class Response extends BasicHttpResponse {
    // 返回零长度的数组或者集合，而不是null,可以避免调用api的客户端进行不必要的非null判断
    public byte[] mRawData = new byte[0];

    public Response(StatusLine statusline) {
        super(statusline);
    }

    public Response(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
    }

    @Override
    public void setEntity(HttpEntity entity) {
        super.setEntity(entity);
        mRawData = entityToBytes(getEntity());
    }

    public byte[] getRawData() {
        return mRawData;
    }

    public int getStatusCode() {
        return getStatusLine().getStatusCode();
    }

    public String getMessage() {
        return getStatusLine().getReasonPhrase();
    }

    private byte[] entityToBytes(HttpEntity entity) {
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
