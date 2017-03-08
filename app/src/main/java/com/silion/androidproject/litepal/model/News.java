package com.silion.androidproject.litepal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    /**
     * News和Introduction建立一对一关系
     */
    private Introduction introduction;
    /**
     * News和Comment建立一对多关系
     */
    private List<Comment> commentList = new ArrayList<>();
    /**
     * News和Category建立多对多关系
     */
    private List<Category> categoryList = new ArrayList<>();

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
