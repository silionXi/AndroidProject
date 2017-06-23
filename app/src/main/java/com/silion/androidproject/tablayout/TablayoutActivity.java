package com.silion.androidproject.tablayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class TablayoutActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayou);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager的后台加载页面个数
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewGroup tabGroupView = (ViewGroup) mTabLayout.getChildAt(0);
                ViewGroup tabView = tabGroupView != null ? (ViewGroup) tabGroupView.getChildAt(tab.getPosition()) : null;
                if (tabView != null) {
                    for (int i = 0; i < tabView.getChildCount(); i++) {
                        View tabViewChild = tabView.getChildAt(i);
                        if (tabViewChild instanceof AppCompatTextView) {
                            // 粗体
                            ((AppCompatTextView) tabViewChild).setTextAppearance(TablayoutActivity.this, R.style.TextAppearance_Large);
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewGroup tabGroupView = (ViewGroup) mTabLayout.getChildAt(0);
                ViewGroup tabView = tabGroupView != null ? (ViewGroup) tabGroupView.getChildAt(tab.getPosition()) : null;
                if (tabView != null) {
                    for (int i = 0; i < tabView.getChildCount(); i++) {
                        View tabViewChild = tabView.getChildAt(i);
                        if (tabViewChild instanceof AppCompatTextView) {
                            // 普通
                            ((AppCompatTextView) tabViewChild).setTextAppearance(TablayoutActivity.this, R.style.TextAppearance_small);
                        }
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 初始化默认选中0
        mTabLayout.getTabAt(0).select();
    }

    // 为了使用android.app.Fragment,这里使用android.support.v13.app.FragmentStatePagerAdapter;
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int PAGE_COUNT = 3;
        private String tabtitles[] = new String[]{"Tab1", "Tab2", "Tab3"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: {
                    fragment = new Tab1Fragment();
                    return fragment;
                }
                case 1: {
                    fragment = new Tab2Fragment();
                    return fragment;
                }
                case 2: {
                    fragment = new Tab3Fragment();
                    return fragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        // Tab title name
        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}
