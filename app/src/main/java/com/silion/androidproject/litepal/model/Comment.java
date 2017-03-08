package com.silion.androidproject.litepal.model;

/**
 * Created by silion on 2017/3/7.
 */

public class Comment {
    private int id;
    private String content;
    /**
     * News和Comment建立一对多关系
     */
    private News news;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
