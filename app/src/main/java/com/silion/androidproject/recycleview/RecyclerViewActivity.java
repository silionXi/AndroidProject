package com.silion.androidproject.recycleview;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {
    private List<Christmas> mChristmasList = new ArrayList<>();
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        loadChristmas();
        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new RecyclerVerticalFragment(), RecyclerVerticalFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vertical:
                if (!(mFragmentManager.findFragmentById(R.id.container) instanceof RecyclerVerticalFragment)) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new RecyclerVerticalFragment(), RecyclerVerticalFragment.class.getSimpleName());
                    fragmentTransaction.commit();
                }
                break;
            case R.id.horizontal:
                if (!(mFragmentManager.findFragmentById(R.id.container) instanceof RecyclerHorizontalFragment)) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new RecyclerHorizontalFragment(), RecyclerHorizontalFragment.class.getSimpleName());
                    fragmentTransaction.commit();
                }
                break;
            case R.id.staggeredGrid:
                if (!(mFragmentManager.findFragmentById(R.id.container) instanceof  RecyclerStaggeredGridFragment)) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new RecyclerStaggeredGridFragment(), RecyclerStaggeredGridFragment.class.getSimpleName());
                    fragmentTransaction.commit();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected void loadChristmas() {
        for (int i = 0; i < 3; i++) {
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

    protected List<Christmas> getChristmas() {
        return mChristmasList;
    }
}
