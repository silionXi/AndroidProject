package com.silion.androidproject.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by silion on 2017/1/26.
 */

public class Book implements Parcelable {
    private String mName;
    private int mPrice;

    public Book() {
    }

    protected Book(Parcel in) {
        mName = in.readString();
        mPrice = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mPrice);
    }

    public void readFromParcel(Parcel parcel) {
        mName = parcel.readString();
        mPrice = parcel.readInt();
    }

    @Override
    public String toString() {
        return "Book{" +
                "mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                '}';
    }
}
