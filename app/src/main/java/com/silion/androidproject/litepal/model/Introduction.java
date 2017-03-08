package com.silion.androidproject.litepal.model;

import org.litepal.crud.DataSupport;

/**
 * 需要继承DataSupport才可以进行CRUD操作
 *
 * Created by silion on 2017/3/7.
 */

public class Introduction extends DataSupport {
    private int id;
    private String guide;
    private String digest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
