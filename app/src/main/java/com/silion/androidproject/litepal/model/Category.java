package com.silion.androidproject.litepal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silion on 2017/3/7.
 */

public class Category {
    private int id;
    private String name;
    /**
     * News和Category建立多对多关系
     */
    private List<News> newsList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
