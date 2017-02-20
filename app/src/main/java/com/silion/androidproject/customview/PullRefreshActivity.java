package com.silion.androidproject.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.silion.androidproject.MyApplication;
import com.silion.androidproject.R;

import java.util.ArrayList;
import java.util.List;

public class PullRefreshActivity extends AppCompatActivity {
    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        setListView();
    }

    private void setListView() {
        final RefreshListView refreshLayout = new RefreshListView(this);

        refreshLayout.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MyApplication.getContext(), "正在刷新...", Toast.LENGTH_SHORT).show();

                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });

        // 不设置的话到底部不会自动加载
        refreshLayout.setOnLoadListener(new OnLoadListener() {

            @Override
            public void onLoadMore() {
                Toast.makeText(getApplicationContext(), "正在加载更多...", Toast.LENGTH_SHORT)
                        .show();

                refreshLayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        refreshLayout.loadCompelete();
                    }
                }, 1500);
            }
        });

        setContentView(refreshLayout);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            datas.add("自定义下拉刷新View" + i);
        }
    }
}
