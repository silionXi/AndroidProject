// BookManager.aidl
package com.silion.androidproject.aidl;

import com.silion.androidproject.aidl.Book;

// 定义方法接口
interface BookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Book> getBooks();

    // 传参时除了java基本类型以及String，CharSequence之外的类型，都需要在前面加上定向tag，具体加什么量需而定
    void addBook(in Book book);
}
