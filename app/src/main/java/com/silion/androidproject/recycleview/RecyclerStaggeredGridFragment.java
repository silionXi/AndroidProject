package com.silion.androidproject.recycleview;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silion.androidproject.BaseFragment;
import com.silion.androidproject.R;

/**
 * Created by LiangShilong on 2016/12/30.
 */

public class RecyclerStaggeredGridFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ChristmasStaggeredGridApdapter christmasAdapter = new ChristmasStaggeredGridApdapter(((RecyclerViewActivity) getActivity()).getChristmas());
        recyclerView.setAdapter(christmasAdapter);
        return rootView;
    }
}
