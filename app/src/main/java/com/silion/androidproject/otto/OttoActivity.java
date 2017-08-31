package com.silion.androidproject.otto;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.MyApplication;
import com.silion.androidproject.R;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OttoActivity extends BaseActivity {
    private static final String TAG = "OttoActivity";
    @BindView(R.id.numTextView)
    TextView mNumTextView;
    @BindView(R.id.msgTextView)
    TextView mMsgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otto);
        ButterKnife.bind(this);
        MyApplication.getBus().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_otto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start: {
                Intent intent = new Intent(this, SmsService.class);
                startService(intent);
                return true;
            }
            case R.id.end: {
                Intent intent = new Intent(this, SmsService.class);
                stopService(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onMessage(SmsMessage msg) {
        Log.d(TAG, "silion onMessage receive otto");
        mNumTextView.setText(msg.getOriginatingAddress());
        mMsgTextView.setText(msg.getMessageBody());
    }
}
