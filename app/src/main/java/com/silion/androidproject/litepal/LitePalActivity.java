package com.silion.androidproject.litepal;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.silion.androidproject.R;

import org.litepal.tablemanager.Connector;

public class LitePalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);
        SQLiteDatabase database = Connector.getDatabase();
    }
}
