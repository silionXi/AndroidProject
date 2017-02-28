package com.silion.androidproject.network.simplenet.requests;

import android.util.Log;

import com.silion.androidproject.network.simplenet.base.Request;
import com.silion.androidproject.network.simplenet.base.Response;
import com.silion.androidproject.network.simplenet.entity.MultipartEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by silion on 2017/2/28.
 */

public class MultipartRequest extends Request<String> {
    MultipartEntity mMultipartEntity = new MultipartEntity();

    /**
     * @param url
     * @param listener
     */
    public MultipartRequest(String url, RequestListener<String> listener) {
        super(HttpMethod.POST, url, listener);
    }

    public MultipartEntity getMultipartEntity() {
        return mMultipartEntity;
    }

    @Override
    public String getBodyContentType() {
        return mMultipartEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 将MultipartEntity中的参数写入到bos中
            mMultipartEntity.writeTo(bos);
        } catch (IOException e) {
            Log.e("", "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    public String parseResponse(Response response) {
        if (response != null && response.getRawData() != null) {
            return new String(response.getRawData());
        }

        return "";
    }
}
