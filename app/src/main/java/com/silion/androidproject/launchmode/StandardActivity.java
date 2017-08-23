package com.silion.androidproject.launchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.R;

public class StandardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.standardBtn: {
                Intent intent = new Intent(this, StandardActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.singleTopBtn: {
                Intent intent = new Intent(this, SingleTopActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.singleTaskBtn: {
                Intent intent = new Intent(this, SingleTaskActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.singleInstanceBtn: {
                Intent intent = new Intent(this, SingleInstanceActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.flagNewTaskBtn: {
                Intent intent = new Intent(this, StandardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.flagSingleTopBtn: {
                Intent intent = new Intent(this, StandardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            }
            case R.id.flagClearTopBtn: {
                Intent intent = new Intent(this, StandardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.flagClearTaskBtn: {
                Intent intent = new Intent(this, StandardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }
            default:
        }
    }
}
