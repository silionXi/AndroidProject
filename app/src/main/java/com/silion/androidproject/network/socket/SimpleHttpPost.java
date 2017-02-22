package com.silion.androidproject.network.socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by silion on 2017/2/22.
 */

public class SimpleHttpPost extends Thread {
    private static final String TAG = "SimpleHttpPost";
    private String mUrl;
    private Socket mSocket;
    private Map<String, String> mParamsMap = new HashMap<>();

    public SimpleHttpPost(String url) {
        mUrl = url;
    }

    public void addParam(String key, String value) {
        mParamsMap.put(key, value);
    }

    @Override
    public void run() {
        try {
            mSocket = new Socket(mUrl, SimpleHttpServer.HTTP_PORT);
            PrintStream out = new PrintStream(mSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

            final String boundary = "simple_http_boundary";
            writeHeader(boundary, out);
            writeParams(boundary, out);
            waitResponse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitResponse(BufferedReader in) {
        try {
            String responseLine = in.readLine();
            while (responseLine == null || !responseLine.contains("HTTP")) {
                responseLine = in.readLine();
            }
            Log.i(TAG, responseLine);
            while ((responseLine = in.readLine()) != null) {
                Log.i(TAG, responseLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeParams(String boundary, PrintStream out) {
        Iterator<String> paramsKeySet = mParamsMap.keySet().iterator();
        while (paramsKeySet.hasNext()) {
            String paraName = paramsKeySet.next();
            out.println("--" + boundary);
            out.println("Content-Disposition: form-data; name=" + paraName);
            out.println();
            out.println(mParamsMap.get(paraName));
        }
        out.println("--" + boundary + "--");
    }

    private void writeHeader(String boundary, PrintStream out) {
        out.println("POST /api/login/ HTTP/1.1");
        out.println("content-length: 123");
        out.println("HOST: " + mUrl + ":" + SimpleHttpServer.HTTP_PORT);
        out.println("Content-Type: multipart/form-data; boundary=" + boundary);
        out.println("User-Agent:android");
        out.println();
    }
}
