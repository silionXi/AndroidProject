package com.silion.androidproject.litepal.model;

import java.util.Date;

/**
 * Created by silion on 2017/3/7.
 */

public class Comment {
    private int id;
    private String content;
    private Date publishDate;

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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
