package com.silion.androidproject;

/**
 * Created by silion on 2016/8/29.
 */
public class Demo {
    public String mName;
    public Class mClass;

    public Demo(String name, Class aClass) {
        mName = name;
        mClass = aClass;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "mName='" + mName + '\'' +
                ", mClass='" + mClass + '\'' +
                '}';
    }
}
