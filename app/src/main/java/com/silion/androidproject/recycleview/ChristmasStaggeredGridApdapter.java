package com.silion.androidproject.recycleview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        final ChristmasStaggeredGridApdapter.ViewHolder viewHolder = new ChristmasStaggeredGridApdapter.ViewHolder(view);
        viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Christmas christmas = mChristmasList.get(position);
                Toast.makeText(view.getContext(), "点击了" + christmas.getName() + "Item", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.mIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Christmas christmas = mChristmasList.get(position);
                Toast.makeText(view.getContext(), "点击了" + christmas.getName() + "Iamge", Toast.LENGTH_SHORT).show();
            }
        });
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
        public View mItemView;
        public ImageView mIconImageView;
        public TextView mTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
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
