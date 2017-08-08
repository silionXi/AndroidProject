package com.silion.simplenet.base;


/**
 * 返回结果类
 *
 * @author silion
 */

public class Response {
    private boolean mIsSuccess;
    private String mResult;

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setSuccess(boolean success) {
        mIsSuccess = success;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }
}
