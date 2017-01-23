package com.silion.androidproject.materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class PersonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int imageId = intent.getIntExtra("image_id", R.drawable.girl01);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout cToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.cToolbarLayout);
        ImageView ivPersonImage = (ImageView) findViewById(R.id.ivPersonImage);
        TextView tvPersonContennt = (TextView) findViewById(R.id.tvPersonContent);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        cToolbarLayout.setTitle(name);
        Glide.with(this).load(imageId).into(ivPersonImage);
        String personContent = generatePersonContent(name);
        tvPersonContennt.setText(personContent);
    }

    private String generatePersonContent(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append(name);
        }
        return sb.toString();
    }
}
