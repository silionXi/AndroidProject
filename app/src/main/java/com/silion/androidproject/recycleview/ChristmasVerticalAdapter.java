package com.silion.androidproject.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silion.androidproject.R;

import java.util.List;

/**
 * Created by LiangShilong on 2016/12/29.
 */

public class ChristmasVerticalAdapter extends RecyclerView.Adapter<ChristmasVerticalAdapter.ViewHolder> {
    List<Christmas> mChristmasList;
    private int createNum;
    private int bindNum;

    public ChristmasVerticalAdapter(List<Christmas> christmasList) {
        mChristmasList = christmasList;
        createNum = 0;
        bindNum = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_christmas_vertical, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        android.util.Log.d("silion", "onCreateViewHolder" + createNum++);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Christmas christmas = mChristmasList.get(position);
        holder.mIconImageView.setImageResource(christmas.getImageId());
        holder.mTitleTextView.setText(christmas.getName());
        android.util.Log.d("silion", "onBindViewHolder" + bindNum++);
    }

    @Override
    public int getItemCount() {
        return mChristmasList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIconImageView;
        public TextView mTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        }
    }
}
