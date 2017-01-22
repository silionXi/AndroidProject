package com.silion.androidproject.materialdesign;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class MaterialDesignActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mStar: {
                Toast.makeText(this, "你点击了Star", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.mDelete: {
                Toast.makeText(this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.mEdit: {
                Toast.makeText(this, "你点击了Edit", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
