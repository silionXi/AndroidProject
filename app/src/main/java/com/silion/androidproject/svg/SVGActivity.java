package com.silion.androidproject.svg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.silion.androidproject.R;

/**
 * Created by silion on 2017/8/9.
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
