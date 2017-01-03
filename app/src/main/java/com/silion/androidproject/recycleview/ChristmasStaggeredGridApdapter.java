package com.silion.androidproject.recycleview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silion.androidproject.R;

import java.util.List;
import java.util.Random;

/**
 * Created by LiangShilong on 2016/12/30.
 */

public class ChristmasStaggeredGridApdapter extends RecyclerView.Adapter<ChristmasStaggeredGridApdapter.ViewHolder> {
    List<Christmas> mChristmasList;

    public ChristmasStaggeredGridApdapter(List<Christmas> christmasList) {
        mChristmasList = christmasList;
    }

    @Override
    public ChristmasStaggeredGridApdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_christmas_staggered_grid, parent, false);
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = getRandomHeight();
        ChristmasStaggeredGridApdapter.ViewHolder viewHolder = new ChristmasStaggeredGridApdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChristmasStaggeredGridApdapter.ViewHolder holder, int position) {
        Christmas christmas = mChristmasList.get(position);
        holder.mIconImageView.setImageResource(christmas.getImageId());
        holder.mTitleTextView.setText(christmas.getName());
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

    protected int getRandomHeight() {
        int height = 300;
        Random random = new Random();
        height = height + random.nextInt(100);
        return height;
    }
}
