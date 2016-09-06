package com.silion.androidproject.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Jackson提供了一系列注解，方便对JSON序列化和反序列化进行控制，下面介绍一些常用的注解。
 @JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
 @JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy年MM月dd日")
 @JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把eMail属性序列化为mail，@JsonProperty("mail")
 */
public class User {
    private String name;

    @JsonIgnore // 不JSON序列化年龄属性
    private int age;

    @JsonFormat(pattern = "yyyy年MM月dd日") // 格式化日期属性
    private Date birthday;

    @JsonProperty("mail") // 序列化email属性为mail
    private String email;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
