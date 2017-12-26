package com.silion.androidproject.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.silion.lsllibrary.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silion on 2017/12/5.
 * 使用{@link View#offsetLeftAndRight}和{@link View#offsetTopAndBottom(int)}
 */

public class DragViewGroup extends FrameLayout {
    private static final String TAG = "DragViewGroup";
    // 保存所有的子View
    private List<View> mChildViews = new ArrayList<>();
    // 记录手指上次触摸的坐标
    private int mStartX;
    private int mStartY;
    // 被触摸的子View
    private View mTouchedView;
    // 记录被触摸子View移动前的坐标
    private int mTouchedViewStartX;
    private int mTouchedViewStartY;
    // 系统认为的最低滑动距离
    private int mTouchSlop;

    public DragViewGroup(Context context) {
        super(context);
        initView();
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     * 解析完xml最后一个tag调用？
     */
    @Override
    protected void onFinishInflate() {
        Logger.d(this, "onFinishInflate");
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            mChildViews.add(getChildAt(i));
        }
    }

    /**
     * 通常用来获取宽高？
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
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
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = x;
                mStartY = y;

                mTouchedView = findTouchedView(x, y);
                if (mTouchedView != null) {
                    mTouchedView.animate().cancel();
                    mTouchedViewStartX = (int) mTouchedView.getX();
                    mTouchedViewStartY = (int) mTouchedView.getY();
//                mTouchedView.bringToFront(); // Parent会redraw，所有子View也会初始化
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mStartX;
                int moveY = y - mStartY;

                if (mTouchedView != null) {
                    mTouchedView.offsetLeftAndRight(moveX);
                    mTouchedView.offsetTopAndBottom(moveY);
                    mStartX = x;
                    mStartY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTouchedView != null) {

                    // 回弹，有bug
                    int viewMoveX = (int) mTouchedView.getX() - mTouchedViewStartX;
                    int viewMoveY = (int) mTouchedView.getY() - mTouchedViewStartY;
                    if ((viewMoveX * viewMoveX + viewMoveY * viewMoveY) < (300 * 300)) {
                        mTouchedView.animate().translationXBy(-viewMoveX).translationYBy(-viewMoveY).setDuration(2000);
                    }
                    mTouchedView = null;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private void initView() {
//        mTouchSlop = ViewConfiguration.getTouchSlop();
        ViewConfiguration configuration = new ViewConfiguration();
        mTouchSlop = configuration.getScaledWindowTouchSlop();
    }

    /**
     * 返回触摸点所在的子View作为touchedView，如果触摸点在多个子View内，则返回最上面的子View
     * 参考{@link ViewGroup#dispatchTouchEvent(MotionEvent)}
     * for (int i = childrenCount - 1; i >= 0; i--) 遍历，优先最上面的子View
     * ViewGroup#isTransformedTouchPointInView(@hide) 判断触摸点是否在子View内
     *
     * @param x
     * @param y
     * @return 触摸点所在的子View
     */
    private View findTouchedView(int x, int y) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (pointInView(x, y, child)) {
                Log.d(TAG, "touched view id = " + child.getId());
                return child;
            }
        }
        return null;
    }

    /**
     * 判断触摸点是否在View内
     * 参考:ViewGrooup#transformPointTo 和 View#pointInView
     *
     * @param x
     * @param y
     * @param child
     * @return 触摸点是否在View内
     */
    private boolean pointInView(int x, int y, View child) {
        int left = (int) child.getX();
        int top = (int) child.getY();
        int right = left + child.getWidth();
        int bottom = top + child.getHeight();
        Log.d(TAG, "pointInView x = " + x);
        Log.d(TAG, "pointInView left = " + left + ", right = " + right);
        Log.d(TAG, "pointInView y = " + y);
        Log.d(TAG, "pointInView top = " + top + ", bottom = " + bottom);
        return x >= left && x <= right && y >= top && y <= bottom;
    }
}
