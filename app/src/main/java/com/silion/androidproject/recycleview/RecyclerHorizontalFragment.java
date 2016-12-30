package com.silion.androidproject.recycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silion.androidproject.BaseFragment;
import com.silion.androidproject.R;

/**
 * Created by LiangShilong on 2016/12/30.
 */

public class RecyclerHorizontalFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        ChristmasHorizontalAdapter christmasAdapter = new ChristmasHorizontalAdapter(((RecyclerViewActivity) getActivity()).getChristmas());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(christmasAdapter);
        return rootView;
    }
}
