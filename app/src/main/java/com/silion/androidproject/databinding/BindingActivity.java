package com.silion.androidproject.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;
import com.silion.androidproject.databinding.ActivityDataBinding;

/**
 * Created by silion on 2017/10/19.
 */

public class BindingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data);
    }
}
