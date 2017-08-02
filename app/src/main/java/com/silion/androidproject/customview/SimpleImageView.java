package com.silion.androidproject.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.silion.androidproject.R;

/**
 * <Android开发进阶P36-45>继承View自定义一个ImageView, 过程如下
 * 1. 继承自View创建自定义控件
 * 2. 如有需要自定义View属性, 也就是在values/attrs.xml中定义属性集
 * 3. 在xml中引入命名控件, 设置属性
 * 4. 在代码中读取xml中的属性, 初始化视图
 * 5. 测量视图大小
 * 6. 绘制视图内容
 */
public class SimpleImageView extends View {

    private Drawable mDrawable;
    private Paint mBitmapPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;

    public SimpleImageView(Context context) {
        this(context, null);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 根据属性初始化
        initAttrs(attrs);
        // 初始化画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取宽的模式与大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //  获取高的模式与大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 调用setMeasuredDimension设置该视图的大小
        setMeasuredDimension(measuredWidth(widthMode, width), measuredHeight(heightMode, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable == null) {
            return;
        }
        // 绘制图片
        if (mBitmap == null) {
            mBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) mDrawable).getBitmap(), getMeasuredWidth(), getMeasuredHeight(), true);
        }
        canvas.drawBitmap(mBitmap, getLeft(), getTop(), mBitmapPaint);

        // 保存画布状态
        canvas.save();
        // 逆时针?旋转90°
        canvas.rotate(90);

        //绘制文字
        mBitmapPaint.setColor(Color.YELLOW);
        mBitmapPaint.setTextSize(150);
        canvas.drawText("郭碧婷", getLeft(), getTop(), mBitmapPaint);

        // 恢复原来的状态
        canvas.restore();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray array = null;
        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleImageView);
            // 根据图片id获取到Drawable对象
            mDrawable = array.getDrawable(R.styleable.SimpleImageView_src);
            //测量Drawable对象的宽高
            measureDrawable();
        } finally {
            if (array != null) {
                array.recycle();
            }
        }
    }

    private void measureDrawable() {
        if (mDrawable == null) {
            throw new RuntimeException("drawable不能为空");
        }
        mWidth = mDrawable.getIntrinsicWidth();
        mHeight = mDrawable.getIntrinsicHeight();
    }

    private int measuredWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
            default:
                break;
        }
        return mWidth;
    }

    private int measuredHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
            default:
                break;
        }
        return mHeight;
    }
}
