package com.silion.androidproject.litepal;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.silion.androidproject.R;
import com.silion.androidproject.litepal.model.Comment;
import com.silion.androidproject.litepal.model.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LitePalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);
        SQLiteDatabase database = Connector.getDatabase();
    }

    public void save(View view) {

        // 存储一条数据到news表,返回布尔值表示是否成功
        News news1 = new News();
        news1.setTitle("第1条新闻标题");
        news1.setContent("第1条新闻内容");
        news1.setPublishDate(new Date());
        news1.save();
        if (news1.save()) {
            Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
        }

        // 使用saveThrows()方法来存储数据，一旦存储失败就会抛出一个DataSupportException异常
        News news2 = new News();
        news2.setTitle("第2条新闻标题");
        news2.setContent("第2条新闻内容");
        news2.setPublishDate(new Date());
        news2.saveThrows();

        // 当调用save()方法或saveThrows()方法存储成功之后，LitePal会自动将该条数据对应的id赋值到实体类的id字段上
        Log.d("TAG", "news id is " + news1.getId());
        Log.d("TAG", "news id is " + news2.getId());

        // Comment和News之间是多对一的关系，一条News中是可以包含多条评论的
        Comment comment1 = new Comment();
        comment1.setContent("好评！");
        comment1.setPublishDate(new Date());
        comment1.save();
        Comment comment2 = new Comment();
        comment2.setContent("赞一个");
        comment2.setPublishDate(new Date());
        comment2.save();
        News news3 = new News();
        news3.getCommentList().add(comment1);
        news3.getCommentList().add(comment2);
        news3.setTitle("第3条新闻标题");
        news3.setContent("第3条新闻内容");
        news3.setPublishDate(new Date());
        news3.setCommentCount(news3.getCommentList().size());
        news3.save();

        List<News> newsList = new ArrayList<>();
        News news4 = new News();
        news4.setTitle("第4条新闻标题");
        news4.setContent("第4条新闻内容");
        news4.setPublishDate(new Date());
        newsList.add(news4);
        News news5 = new News();
        news5.setTitle("第5条新闻标题");
        news5.setContent("第5条新闻内容");
        news5.setPublishDate(new Date());
        news5.saveThrows();
        newsList.add(news5);
        DataSupport.saveAll(newsList);
    }
}
