package com.silion.androidproject.recycleview;

/**
 * Created by LiangShilong on 2016/12/29.
 */

public class Christmas {
    private String mName;
    private int mImageId;

    public Christmas(String name, int imageId) {
        mName = name;
        mImageId = imageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    @Override
    public String toString() {
        return "Christmas{" +
                "mName='" + mName + '\'' +
                ", mImageId=" + mImageId +
                '}';
    }
}
