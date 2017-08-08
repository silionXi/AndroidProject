package com.silion.simplenet.config;

/**
 * 一些常规的http配置类
 *
 * @author silion
 */

public abstract class HttpConfig {
    public int conTimeOut = 10000; // 10 * 1000;
    public int ioTimeOut = 60000; // 60 * 1000;
    public String uesrAgent = "default";
}
