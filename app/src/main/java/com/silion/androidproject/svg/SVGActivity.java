package com.silion.androidproject.svg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.silion.androidproject.R;

/**
 * 动画播放SVG图片
 * 1. 依赖:compile 'com.eftimoff:android-pathview:1.0.8@aar'
 * 2. 使用 com.eftimoff.androipathview.PathView 控件
 * 3. 代码中设置动画
 *
 * 参考
 * 一行代码实现炫酷动画(https://mp.weixin.qq.com/s/wDdw1kcPaJwote1st_46wA)
 * 在 Android 中使用 SVG 矢量图(http://blog.csdn.net/cniao5/article/details/72121635)
 * Android 中 Iconfont 图标的使用以及自定义(http://blog.csdn.net/cniao5/article/details/72457771)
 */

public class SVGActivity extends Activity {

    private PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        mPathView = (PathView) findViewById(R.id.pathView);
        mPathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.getPathAnimator()
                        .delay(100)
                        .duration(500)
                        .interpolator(new AccelerateDecelerateInterpolator())
                        .start();
            }
        });
    }
}
