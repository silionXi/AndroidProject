package com.silion.androidproject.litepal.model;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * 需要继承DataSupport才可以进行CRUD操作
 * <p>
 * Created by silion on 2017/3/7.
 */

public class Comment extends DataSupport {
    private int id;
    private String content;
    private Date publishDate;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
