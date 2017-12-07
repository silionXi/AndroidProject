package com.silion.androidproject.customview;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.silion.androidproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by silion on 2017/12/5.
 */

public class DragViewActivity extends Activity {
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.iv3)
    ImageView mIv3;
    private int mWidth;
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drag_view);
        ButterKnife.bind(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
    }

//    @OnClick({R.id.iv1, R.id.iv2, R.id.iv3})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv1:
//                Toast.makeText(this, "点击了iv1", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.iv2:
//                Toast.makeText(this, "点击了iv2", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.iv3:
////                ((View) mIv3.getParent()).scrollTo(mWidth / 3, 0);
////                mIv3.scrollTo(mWidth / 3, 0); // 有效点击也会移动
//                mIv3.offsetLeftAndRight(-mWidth / 3); // 有效点击也会移动
//                Toast.makeText(this, "点击了iv3", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
}
