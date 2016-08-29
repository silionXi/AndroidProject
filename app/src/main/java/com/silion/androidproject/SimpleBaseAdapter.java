package com.silion.androidproject;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silion on 2016/8/26.
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;

    public SimpleBaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mDatas.size()) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 该方法需要子类实现，需要返回item的layout resource
     *
     * @return layout resource
     */
    public abstract int getItemLayout();

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param holder
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, getItemLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return getItemView(position, convertView, holder);
    }

    public void addAll(List<T> elem) {
        mDatas.addAll(elem);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mDatas.clear();
        mDatas.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        mDatas.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mDatas.remove(index);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        // SparseArray是Android提供的一个数据结构，但是比Map的效率更高
        private SparseArray<View> mViews = new SparseArray<>();
        private View mConvertView;

        public ViewHolder(View convertView) {
            mConvertView = convertView;
        }

        public <T extends View> T getView(int resId) {
            View v = mViews.get(resId);

            if (null == v) {
                v = mConvertView.findViewById(resId);
                mViews.put(resId, v);
            }
            return (T) v;
        }
    }
}
