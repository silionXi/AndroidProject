package com.silion.androidproject.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.silion.lsllibrary.logger.Logger;

/**
 * Created by silion on 2017/12/7.
 */

public class ViewDragHelperLayout extends FrameLayout {
    private static final String TAG = "ViewDragHelperLayout";
    private ViewDragHelper mViewDragHelper;
    // 子view开始的位置
    private int mViewStartX;
    private int mViewStartY;
    // 屏幕尺寸
    private int mWidth;
    private int mHeight;

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 开始拖拽时调用
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            mViewStartX = (int) capturedChild.getX();
            mViewStartY = (int) capturedChild.getY();
        }

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
         * 手指释放时调用
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 超过屏幕则回弹
            if (releasedChild.getLeft() < 0 || releasedChild.getRight() > mWidth || releasedChild.getTop() < 0 || releasedChild.getBottom() > mHeight) {
                mViewDragHelper.settleCapturedViewAt(mViewStartX, mViewStartY);
                //mViewDragHelper.smoothSlideViewTo(releasedChild, mViewStartX, mViewStartY);
                invalidate();
            }
        }
    };

    public ViewDragHelperLayout(Context context) {
        super(context);
        Log.d(TAG, "ViewDragHelperLayout  1");
        initView(context);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "ViewDragHelperLayout  2");
        initView(context);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "ViewDragHelperLayout  3");
        initView(context);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "ViewDragHelperLayout  4");
        initView(context);
    }

    @Override
    protected void onFinishInflate() {
        Logger.d(this, "onFinishInflate");
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Logger.d(this, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d(this, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Logger.d(this, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Logger.d(this, "onDraw");
        super.onDraw(canvas);
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

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private void initView(Context context) {
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
    }
}
