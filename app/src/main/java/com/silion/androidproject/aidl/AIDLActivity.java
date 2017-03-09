package com.silion.androidproject.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.util.List;

public class AIDLActivity extends BaseActivity {
    private static final String TAG = "AIDLActivity";
    private boolean mBound = false;
    private BookManager mBookManager;
    private List<Book> mBooks;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            AIDLService.MyBinder binder = (AIDLService.MyBinder) iBinder;
//            binder.transact(code, data, reply, flags);
            mBookManager = BookManager.Stub.asInterface(iBinder);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Log.d(TAG, "Books = " + mBooks);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attempToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConn);
            mBound = false;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAddBook: {
                addBook();
                break;
            }
            default:
                break;
        }
    }

    private void attempToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.silion.androidproject.aidl"); // 服务器的action
        intent.setPackage("com.silion.androidproject"); // 服务器的package
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    public void addBook() {
        if (!mBound) {
            attempToBindService();
            Toast.makeText(this, "正字链接，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBookManager == null) {
            return;
        }
        Book book = new Book();
        book.setName("第一行代码");
        book.setPrice(78);

        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
