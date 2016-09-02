package com.silion.androidproject.layoutinflater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.silion.androidproject.R;

public class LayoutInflaterActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mllContainer;
    private LinearLayout mllAdd;
    private View mInflaterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);
        mllContainer = (LinearLayout) findViewById(R.id.llContainer);
        mllAdd = (LinearLayout) findViewById(R.id.llAdd);
        findViewById(R.id.btTrue).setOnClickListener(this);
        findViewById(R.id.btFalse).setOnClickListener(this);
        findViewById(R.id.btAdd).setOnClickListener(this);
        findViewById(R.id.btNull).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * root = mllContainer
             * attachToRoot = true
             */
            case R.id.btTrue: {
                mllContainer.removeAllViews();
                mInflaterView = LayoutInflater.from(this).inflate(R.layout.view_inflater, mllContainer, true);
                break;
            }
            /**
             * root = mllContainer
             * attachToRoot = false
             */
            case R.id.btFalse: {
                mllContainer.removeAllViews();
                mInflaterView = LayoutInflater.from(this).inflate(R.layout.view_inflater, mllContainer, false);
                break;
            }
            /**
             * root = null
             * attachToRoot invail
             */
            case R.id.btNull: {
                mllContainer.removeAllViews();
                mInflaterView = LayoutInflater.from(this).inflate(R.layout.view_inflater, null, false);
                break;
            }
            case R.id.btAdd: {
                mllAdd.removeAllViews();
                if (mInflaterView != null) {
                    mllAdd.addView(mInflaterView);
                }
            }
            default:
                break;
        }
    }
}
