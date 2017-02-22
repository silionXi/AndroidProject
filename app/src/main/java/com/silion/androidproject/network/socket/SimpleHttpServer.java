package com.silion.androidproject.network.socket;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silion on 2017/2/22.
 */

public class SimpleHttpServer extends Thread {
    private static final String TAG = "SimpleHttpServer";
    public static final int HTTP_PORT = 8000;
    private ServerSocket mServerSocket = null;

    public SimpleHttpServer() {
        try {
            mServerSocket = new ServerSocket(HTTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mServerSocket == null) {
            throw new RuntimeException("服务器socket初始化失败");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Log.d(TAG, "等待连接中...");
                new DeliverThread(mServerSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class DeliverThread extends Thread {
        private Socket mClienSocket;
        private BufferedReader mIn;
        private PrintStream mOut;
        private String mHttpMethod;
        private String mPath;
        private String mHttpVersion;
        private boolean isParseHeader = false;
        private String mBoundary;
        private Map<String, String> mHeader = new HashMap<>();
        private Map<String, String> mParams = new HashMap<>();

        public DeliverThread(Socket sock) {
            mClienSocket = sock;
        }

        @Override
        public void run() {
            Log.d(TAG, "DeliverThread : " + this.getId());

            try {
                mIn = new BufferedReader(new InputStreamReader(mClienSocket.getInputStream()));
                mOut = new PrintStream(mClienSocket.getOutputStream());
                parseRequest();
                handleResponse();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    mIn.close();
                    mOut.close();
                    mClienSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleResponse() {
            try {
                sleep(1000);
                mOut.println("HTTP/1.1 200 OK");
                mOut.println("Content-Tpye: application/json");
                mOut.println();
                mOut.println("{\"stCode\":\"sucess\"}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void parseRequest() {
            String line;
            try {
                int lineNum = 0;
                while ((line = mIn.readLine()) != null) {
                    if (lineNum == 0) {
                        parseRequestLine(line);
                    }
                    if (isEnd(line)) {
                        break;
                    }
                    if (lineNum != 0 && !isParseHeader) {
                        parseRequestHeader(line);
                    }
                    if (isParseHeader) {
                        parseRequestParams(line);
                    }
                    lineNum++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void parseRequestParams(String params) throws IOException {
            if (params.equals("--" + mBoundary)) {
                String contentDisposition = mIn.readLine();
                String paramName = parseSecondField(contentDisposition);
                mIn.readLine();
                String paramValue = mIn.readLine();
                mParams.put(paramName, paramValue);
                Log.i(TAG, "请求参数 " + paramName + "=" + paramValue);
            }
        }

        private void parseRequestHeader(String header) {
            if (header.equals("")) {
                isParseHeader = true;
                Log.d(TAG, "header解析完成");
                return;
            } else if (header.contains("boundary")) {
                mBoundary = parseSecondField(header);
                Log.d(TAG, "分隔符：" + mBoundary);
            } else {
                parseHeaderParam(header);
            }
        }

        private void parseHeaderParam(String header) {
            String[] keyValue = header.split(":");
            mHeader.put(keyValue[0].trim(), keyValue[1].trim());
            Log.d(TAG, "请求头 " + keyValue[0].trim() + ": " + keyValue[1].trim());
        }

        private String parseSecondField(String header) {
            String[] headerArray = header.split(";");
            parseHeaderParam(headerArray[0]);
            if (headerArray.length > 1) {
                return headerArray[1].split("=")[1];
            }
            return "";
        }

        private boolean isEnd(String line) {
            return line.equals("--" + mBoundary + "--");
        }

        private void parseRequestLine(String requestLine) {
            String[] tempString = requestLine.split(" ");
            mHttpMethod = tempString[0];
            mPath = tempString[1];
            mHttpVersion = tempString[2];

            Log.v(TAG, "请求方式：" + mHttpMethod);
            Log.v(TAG, "子路径：" + mPath);
            Log.v(TAG, "http版本：" + mHttpVersion);
        }
    }
}
