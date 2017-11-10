package com.silion.androidproject.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

/**
 * Created by silion on 2017/10/19.
 */

public class BindingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data);
        binding.setTest(this);
        binding.setTask(new Task("xixi"));
        new CheckBox(this).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    public void onCheckChange(Task task, CompoundButton cb, boolean isCheck) {
        android.util.Log.d("silion", "Binding.onCheckChange");
        task.run();
    }
}
