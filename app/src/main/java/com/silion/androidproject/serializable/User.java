package com.silion.androidproject.serializable;

import java.io.Serializable;

/**
 * 实现Serializable接口，仅仅起标识这个类可被序列化的作用
 * <p>
 * Created by silion on 2016/9/9.
 */
public class User implements Serializable {
    // 可序列化对象的版本，进行序列化或者反序列化时，版本需要一致
    private static final long serialVersionUID = -8284949931281996242L;
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
