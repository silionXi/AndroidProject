package com.silion.androidproject.customview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.silion.androidproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by silion on 2017/2/17.
 */

public abstract class RefreshLayoutBase<T extends View> extends ViewGroup implements AbsListView.OnScrollListener {
    private static final String TAG = "RefreshLayoutBase";
    /**
     * 下拉刷新时显示的header view
     */
    protected View mHeaderView;
    /**
     * header中的箭头图标
     */
    private ImageView mArrowImageView;
    /**
     * header 中的文本标签
     */
    private TextView mTipsTextView;
    /**
     * header中的时间标签
     */
    private TextView mTimeTextView;
    /**
     * header中的进度条
     */
    private ProgressBar mProgressBar;
    /**
     * 内容视图, 即用户触摸导致下拉刷新、上拉加载的主视图. 比如ListView, GridView等.
     */
    protected T mContentView;
    /**
     * 上拉加载更多时显示的footer view
     */
    protected View mFooterView;
    /**
     * 最初的滚动位置.第一次布局时滚动header的高度的距离
     */
    protected int mInitScrollY = 0;
    /**
     * 最后一次触摸事件的y轴坐标
     */
    protected int mLastY = 0;
    /**
     * 本次触摸滑动y坐标上的偏移量
     */
    protected int mYOffset;
    /**
     * 空闲状态
     */
    public static final int STATUS_IDLE = 0;
    /**
     * 下拉或者上拉状态, 还没有到达可刷新的状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 1;

    /**
     * 下拉或者上拉状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 2;
    /**
     * 刷新中
     */
    public static final int STATUS_REFRESHING = 3;

    /**
     * LOADING中
     */
    public static final int STATUS_LOADING = 4;
    /**
     * 当前状态
     */
    protected int mCurrentStatus = STATUS_IDLE;
    /**
     * 箭头是否向上
     */
    private boolean isArrowUp;
    /**
     * 下拉刷新监听器
     */
    protected OnRefreshListener mOnRefreshListener;
    /**
     * 加载更多回调
     */
    protected OnLoadListener mOnLoadListener;

    private Scroller mScroller;
    private int mScreenHeight;
    private int mHeaderHeight;

    public RefreshLayoutBase(Context context) {
        this(context, null);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化Scroller对象
        mScroller = new Scroller(context);

        // 获取屏幕高度
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        // header的高度为屏幕高度的1/4
        mHeaderHeight = mScreenHeight / 4;

        // 初始化整个布局
        initLayout(context);
    }

    /*
     * 丈量视图的宽、高。宽度为用户设置的宽度，高度则为header, content view, footer这三个子控件的高度总和。
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int finalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // measure
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 该view所需要的总高度
            finalHeight += child.getMeasuredHeight();
        }
        setMeasuredDimension(width, finalHeight);
    }

    /*
     * 布局函数，将header, content view,
     * footer这三个view从上到下布局。布局完成后通过Scroller滚动到header的底部，
     * 即滚动距离为header的高度 + 本视图的paddingTop，从而达到隐藏header的效果.
     * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int top = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, top, child.getMeasuredWidth(), top + child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }

        // 计算初始化滑动的y轴距离
        mInitScrollY = getPaddingTop() + mHeaderView.getMeasuredHeight();
        // 滑动到位置从而达到隐藏header view的效果
        scrollTo(0, mInitScrollY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onTouchEvent will be called and we do the actual
         * scrolling there.
         */
        // 获取触摸事件的类型
        final int action = MotionEventCompat.getActionMasked(ev);
        // Always handle the case of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Do not intercept touch event, let the child handle it
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mYOffset = (int) (ev.getRawY() - mLastY);
                // 并不是每次ACTION_MOVE都会调用onInterceptTouchEvent
                android.util.Log.d("silion", "silion onInterceptTouchEvent : ACTION_MOVE");
                // 如果拉到了顶部, 并且是下拉(mYOffset > 0),则拦截触摸事件,从而转到onTouchEvent来处理下拉刷新事件
                if (isTop() & mYOffset > 0) {
                    android.util.Log.d("silion", "silion onInterceptTouchEvent : YES");
                    return true;
                }
                break;
        }

        // Do not intercept touch event, let the child handle it
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "@@@ onTouchEvent : action = " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 获取手指触摸的当前y坐标
                int currentY = (int) event.getRawY();
                // 当前坐标减去按下时的y坐标得到y轴上的偏移量
                mYOffset = currentY - mLastY;
                if (mCurrentStatus != STATUS_LOADING) {
                    changeScrollY(mYOffset);
                }
                rotateHeaderArrow();
                changeTips();
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                doRefresh();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        android.util.Log.d("silion", "onScroll");
        if (mOnLoadListener != null && isBottom() &&
                mScroller.getCurrY() <= mInitScrollY && mYOffset <= 0 && mCurrentStatus == STATUS_IDLE) {
            showFooterView();
            doLoadMore();
        }
    }

    public void loadCompelete() {
        startScroll(mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_IDLE;
    }

    /**
     * 执行下拉(自动)加载更多的操作
     */
    protected void doLoadMore() {
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoadMore();
        }
    }

    private void showFooterView() {
        startScroll(mFooterView.getMeasuredHeight());
        mCurrentStatus = STATUS_LOADING;
    }

    private void startScroll(int yOffset) {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, yOffset);
        invalidate();
    }

    protected abstract boolean isBottom();

    /**
     * 设置下拉刷新监听器
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public void setOnLoadListener(OnLoadListener listener) {
        mOnLoadListener = listener;
    }

    private void doRefresh() {
        changeHeaderViewStatus();
        if (mCurrentStatus == STATUS_REFRESHING && mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }

    public void refreshComplete() {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_IDLE;
        invalidate();
        updateHeaderTimeStamp();

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                mArrowImageView.setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
            }
        }, 100);
    }

    private void updateHeaderTimeStamp() {
        mTimeTextView.setText(R.string.pull_to_refresh_update_time_label);
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        mTimeTextView.append(simpleDateFormat.format(new Date()));
    }

    private void changeHeaderViewStatus() {
        int curScrollY = getScrollY();
        if (curScrollY < mInitScrollY / 2) {
            mScroller.startScroll(getScrollX(), curScrollY, 0, mHeaderView.getPaddingTop() - curScrollY);
            mCurrentStatus = STATUS_REFRESHING;
            mTipsTextView.setText(R.string.pull_to_refresh_refreshing_label);
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(GONE);
            mProgressBar.setVisibility(VISIBLE);
        } else {
            mScroller.startScroll(getScrollX(), curScrollY, 0, mInitScrollY - curScrollY);
            mCurrentStatus = STATUS_IDLE;
        }

        invalidate();
    }

    private void changeTips() {
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
            mTipsTextView.setText(R.string.pull_to_refresh_pull_label);
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            mTipsTextView.setText(R.string.pull_to_refresh_release_label);
        }
    }

    private void rotateHeaderArrow() {
        if (mCurrentStatus == STATUS_REFRESHING) {
            return;
        } else if (mCurrentStatus == STATUS_PULL_TO_REFRESH && !isArrowUp) {
            return;
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH && isArrowUp) {
            return;
        }

        mProgressBar.setVisibility(View.GONE);
        mArrowImageView.setVisibility(VISIBLE);
        float pivotX = mArrowImageView.getWidth() / 2f;
        float pivotY = mArrowImageView.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        mArrowImageView.startAnimation(animation);

        if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            isArrowUp = true;
        } else {
            isArrowUp = false;
        }
    }

    protected void changeScrollY(int distance) {
        // 最大值为scrollY(heade 隐藏), 最小值为0(header 完全显示)
        int curScrollY = getScrollY();
        // 下拉
        if (distance > 0 && curScrollY - distance > getPaddingTop()) {
            scrollBy(0, -distance);
        } else if (distance < 0 && curScrollY - distance <= mInitScrollY) {
            //上拉过程
            scrollBy(0, -distance);
        }

        curScrollY = getScrollY();
        int slop = mInitScrollY / 2;

        if (curScrollY > 0 && curScrollY < slop) {
            mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
        } else if (curScrollY > 0 && curScrollY > slop) {
            mCurrentStatus = STATUS_PULL_TO_REFRESH;
        }
    }

    protected abstract boolean isTop();

    private final void initLayout(Context context) {
        // header view
        setupHeaderView(context);
        // 设置内容视图
        setupContentView(context);
        // 设置内容视图布局参数
        setDefaultContentLayoutParams();
        // 添加mContentView
        addView(mContentView);
        // footer view
        setupFooterView(context);
    }

    protected void setupFooterView(Context context) {
        mFooterView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer, this, false);
        addView(mFooterView);
    }

    /**
     * 设置内容视图布局参数, width和heigh都是match_parent
     */
    protected void setDefaultContentLayoutParams() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    /**
     * 与Scroller合作,实现平滑滚动。在该方法中调用Scroller的computeScrollOffset来判断滚动是否结束。如果没有结束，
     * 那么滚动到相应的位置，并且调用postInvalidate方法重绘界面，从而再次进入到这个computeScroll流程，直到滚动结束。
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 初始化Content View, 子类覆写.
     */
    protected abstract void setupContentView(Context context);

    protected void setupHeaderView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this, false);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, mHeaderHeight));
        mHeaderView.setBackgroundColor(Color.RED);
        /*
        header的高度为1/4的屏幕高度,但是,它只有100px是有效的显示区域
        取余为paddingTop,这样是为了达到下拉的效果
         */
        mHeaderView.setPadding(0, mHeaderHeight - 100, 0, 0);
        addView(mHeaderView);

        // HEADER VIEWS
        mArrowImageView = (ImageView) mHeaderView.findViewById(R.id.pull_to_arrow_image);
        mTipsTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_text);
        mTimeTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_updated_at);
        mProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
    }

    /**
     * 返回Content View
     *
     * @return
     */
    public T getContentView() {
        return mContentView;
    }

    /**
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * @return
     */
    public View getFooterView() {
        return mFooterView;
    }
}
