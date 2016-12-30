package com.silion.androidproject.recycleview;

import android.os.Bundle;
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

public class RecyclerVerticalFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private ChristmasVerticalAdapter mChristmasAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mChristmasAdapter = new ChristmasVerticalAdapter(((RecyclerViewActivity) getActivity()).getChristmas());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mChristmasAdapter);
        return rootView;
    }
}
