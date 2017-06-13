package com.silion.androidproject.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AIDLService extends Service {
    private static final String TAG = "AIDLService";
    private List<Book> mBooks = new ArrayList();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.d(TAG, "Invoking getBooks() method, now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
            }
            return new ArrayList<>();
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                Log.d(TAG, "Invoking addBook() method, book is : " + book);
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null");
                    return;
                }
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
            }
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG, "Listener already exists");
            }

            Log.d(TAG, "registerListener list size = " + mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mListenerList != null && mListenerList.contains(listener)) {
                mListenerList.remove(listener);
            } else {
                Log.d(TAG, "Listener not found");
            }
        }
    };

    private MyBinder mBinder = new MyBinder();

    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, String.format("On bind, intent = %s", intent.toString()));
        return mBookManager;
//        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("Android开发进阶");
        book.setPrice(69);
        mBooks.add(book);
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        Log.d(TAG, "silion onNewBookArrived, notifi " + mListenerList.size() + " listeners: " + book);

        mBooks.add(book);
        for (IOnNewBookArrivedListener listener : mListenerList) {
            listener.onNewBookArrived(book);
        }
    }

    class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int index = mBooks.size() + 1;
                Book book = new Book();
                book.setName("Android开发艺术探索-" + index);
                book.setPrice(99);
                try {
                    onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    }
}
