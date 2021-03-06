package com.silion.androidproject.materialdesign;

import java.io.Serializable;

/**
 * Created by silion on 2017/1/23.
 */

public class Person implements Serializable {
    private String mName;
    private int mImageId;

    public Person(String name, int imageId) {
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
}
