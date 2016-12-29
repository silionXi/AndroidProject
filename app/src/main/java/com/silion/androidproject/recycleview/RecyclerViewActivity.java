package com.silion.androidproject.recycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.silion.androidproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Christmas> mChristmasList = new ArrayList<>();
    private ChristmasAdapter mChristmasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        loadChristmas();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mChristmasAdapter = new ChristmasAdapter(mChristmasList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mChristmasAdapter);
    }

    protected void loadChristmas() {
        for (int i = 0; i < 2; i++) {
            Christmas cup = new Christmas("杯子", R.drawable.christmas_cup);
            mChristmasList.add(cup);
            Christmas elk = new Christmas("麋鹿", R.drawable.christmas_elk);
            mChristmasList.add(elk);
            Christmas firewood = new Christmas("柴火", R.drawable.christmas_firewood);
            mChristmasList.add(firewood);
            Christmas gift = new Christmas("礼物", R.drawable.christmas_gift);
            mChristmasList.add(gift);
            Christmas glove = new Christmas("手袜", R.drawable.christmas_glove);
            mChristmasList.add(glove);
            Christmas hat = new Christmas("帽子", R.drawable.christmas_hat);
            mChristmasList.add(hat);
            Christmas hose = new Christmas("袜子", R.drawable.christmas_hose);
            mChristmasList.add(hose);
            Christmas santa = new Christmas("圣诞老人", R.drawable.christmas_santa);
            mChristmasList.add(santa);
            Christmas sled = new Christmas("雪橇", R.drawable.christmas_sled);
            mChristmasList.add(sled);
            Christmas smallBell = new Christmas("铃铛", R.drawable.christmas_small_bell);
            mChristmasList.add(smallBell);
            Christmas snowman = new Christmas("雪人", R.drawable.christmas_snowman);
            mChristmasList.add(snowman);
            Christmas stack = new Christmas("烟囱", R.drawable.christmas_stack);
            mChristmasList.add(stack);
        }
    }
}
