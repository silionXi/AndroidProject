package com.silion.androidproject.diffutil;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * 继承DiffUtil.Callback类
 * DiffUtil.calculateDiff会自动调用这个类的方法
 * 比较两个集合的Item是否相同{@link #areItemsTheSame}
 * Item的内容是否相同{@link #areContentsTheSame(int, int)}
 */
public class DiffCallback<T extends Model> extends DiffUtil.Callback {
    private List<T> mOldDatas, mNewDatas;//看名字

    public DiffCallback(List<T> oldDatas, List<T> newDatas) {
        mOldDatas = oldDatas;
        mNewDatas = newDatas;
    }

    /**
     * 旧数据集size
     *
     * @return
     */
    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    /**
     * 新数据集size
     *
     * @return
     */
    @Override
    public int getNewListSize() {
        return mNewDatas == null ? 0 : mNewDatas.size();
    }

    /**
     * 被DiffUtil调用，用来判断两个对象是否是相同的Item。
     * 本例在Model重写equals实现比较
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        if (oldItemPosition >= getOldListSize() || newItemPosition >= getNewListSize()) {
//            return false;
//        }
        boolean isSame = mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        return isSame;
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * 使用泛型T extends Model, 在Model实现对比数据的equalsContents方法
     * 子类可以重写equalsContents实现自己的数据对比
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        if (oldItemPosition >= getOldListSize() || newItemPosition >= getNewListSize()) {
//            return false;
//        }
        boolean isSame = mOldDatas.get(oldItemPosition).equalsContents(mNewDatas.get(newItemPosition));
        return isSame;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
