package com.silion.androidproject.customview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by silion on 2017/12/7.
 */

public class ViewDragHelperLayout extends FrameLayout {
    private ViewDragHelper mViewDragHelper;

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 判断是不是要操作的View，这里直接放回true，即所有子View都是目标
         * @param child
         * @param pointerId
         * @return true
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 水平方向上的滑动
         * @param child 子View
         * @param left 水平方向上child滑动的距离
         * @param dx 比较前一次的增量
         * @return 实际要滑动的距离
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        /**
         * 垂直方向上的滑动
         * @param child 子View
         * @param top 垂直方向上child滑动的距离
         * @param dy 比较前一次的增量
         * @return 实际要滑动的距离
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        /**
         * 手指释放时回调
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    };

    public ViewDragHelperLayout(Context context) {
        super(context);
        initView();
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给ViewDragHelper，此操作必不可少
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }
}
