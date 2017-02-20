package com.silion.androidproject.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class CustomViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_activity);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSimpleImageView:
                Intent imageViewIntent = new Intent(this, SimpleImageViewActivity.class);
                startActivity(imageViewIntent);
                break;
            case R.id.btPullRefreshView:
                Intent pullRefreshIntent = new Intent(this, PullRefreshActivity.class);
                startActivity(pullRefreshIntent);
                break;
            default:
                break;
        }
    }
}
