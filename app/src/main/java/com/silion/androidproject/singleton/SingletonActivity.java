package com.silion.androidproject.singleton;

import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

/**
 * Created by silion on 2017/8/11.
 */

public class SingletonActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.singleton1:
                Singleton1.getInstance().doSomething();
                break;
            case R.id.singleton2:
                Singleton2.getInstance().doSomething();
                break;
            case R.id.singleton3:
                Singleton3.getInstance().doSomething();
                break;
            case R.id.singleton4:
                Singleton4.INSTANCE.doSomething();
                break;
            case R.id.singleton5:
                // TODO: 2017/8/11
                break;
            default:
        }
    }
}
