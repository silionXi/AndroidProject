package com.silion.androidproject.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by silion on 2017/2/20.
 */

public class RefreshListView extends RefreshAdapterView<ListView> {
    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean isBottom() {
        return mContentView != null && mContentView.getAdapter() != null
                && mContentView.getLastVisiblePosition() == mContentView.getAdapter().getCount() - 1;
    }

    @Override
    protected boolean isTop() {
        return mContentView != null && mContentView.getFirstVisiblePosition() == 0
                && getScrollY() <= mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void setupContentView(Context context) {
        mContentView = new ListView(context);
        mContentView.setOnScrollListener(this);
    }
}
