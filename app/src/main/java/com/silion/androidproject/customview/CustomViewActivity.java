package com.silion.androidproject.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.btSimpleImageView)
    Button mBtSimpleImageView;
    @BindView(R.id.btPullRefreshView)
    Button mBtPullRefreshView;
    @BindView(R.id.btDragViewGroup)
    Button mBtDragViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btSimpleImageView, R.id.btPullRefreshView, R.id.btDragViewGroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSimpleImageView:
                Intent imageViewIntent = new Intent(this, SimpleImageViewActivity.class);
                startActivity(imageViewIntent);
                break;
            case R.id.btPullRefreshView:
                Intent pullRefreshIntent = new Intent(this, PullRefreshActivity.class);
                startActivity(pullRefreshIntent);
                break;
            case R.id.btDragViewGroup:
                Intent dragViewGroup = new Intent(this, DragViewActivity.class);
                startActivity(dragViewGroup);
                break;
        }
    }
}
