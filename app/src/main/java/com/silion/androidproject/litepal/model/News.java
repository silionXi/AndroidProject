package com.silion.androidproject.litepal.model;

import java.util.Date;

/**
 * Created by silion on 2017/3/7.
 */

public class News {
    /**
     * id这个字段可写可不写，不写LitePal会自动生成一个主键id
     */
    private int id;
    private String title;
    private String content;
    private Date publishDate;
    private int commentCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
