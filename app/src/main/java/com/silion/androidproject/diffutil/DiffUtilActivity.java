package com.silion.androidproject.diffutil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;
import com.silion.lsllibrary.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/zxt0601/article/details/52562770
 * DiffUtil用来比较两个数据集，寻找出旧数据集->新数据集的最小变化量
 * 用于RecyclerAdapter只刷新改变的item
 */
public class DiffUtilActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private GridLayoutManager mRecyclerManager;
    private List<Model> mDatas;
    private int[] mImages = {R.drawable.fei01, R.drawable.fei02, R.drawable.fei03, R.drawable.fei04, R.drawable.fei05, R.drawable.fei06};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_util);
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerAdapter = new RecyclerAdapter();
        mDatas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mDatas.add(new Model("飞儿" + (i + 1), mImages[i]));
        }
        mRecyclerAdapter.setDatas(mDatas);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mRecyclerManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diff_util, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.init:
                // 初始化数据,每次更新完后都要初始化数据后再测试更新操作
                if (mDatas.isEmpty()) {
                    for (int i = 0; i < 6; i++) {
                        mDatas.add(new Model("飞儿" + (i + 1), mImages[i]));
                    }
                }
                mRecyclerAdapter.setDatas(mDatas);
                mRecyclerAdapter.notifyDataSetChanged();
                return true;
            case R.id.update: {
                // 普通方式更新
                List<Model> newDatas = new ArrayList<>();

                for (Model model : mDatas) {
                    try {
                        //clone一遍旧数据 ，模拟刷新操作
                        newDatas.add(model.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }

                // 模拟增加数据
                newDatas.add(new Model("允儿6", R.drawable.model06));
                newDatas.add(new Model("允儿7", R.drawable.model07));
                newDatas.add(new Model("允儿8", R.drawable.model08));

                // 模拟数据移位
                Model model = newDatas.remove(1);
                newDatas.add(model);

                mRecyclerAdapter.setDatas(newDatas);
                // 普通方式更新,所有的Item都需要刷新(即重新调用onBindViewHolder)
                mRecyclerAdapter.notifyDataSetChanged();
                return true;
            }
            case R.id.updateDiff: {
                // 使用DiffUtil更新
                List<Model> newDatas = new ArrayList<>();

                for (Model model : mDatas) {
                    try {
                        //clone一遍旧数据 ，模拟刷新操作
                        newDatas.add(model.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }

                // 模拟增加数据
                newDatas.add(new Model("允儿6", R.drawable.model06));
                newDatas.add(new Model("允儿7", R.drawable.model07));
                newDatas.add(new Model("允儿8", R.drawable.model08));

                // 模拟数据移位
                Model model = newDatas.remove(1);
                newDatas.add(model);
                /* 算法对比找出有改变的Item, 算法需要一定耗时
                   实战时应该将获取DiffResult的过程放到子线程中，并在主线程中更新RecyclerView
                 */
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback<>(mDatas, newDatas), true);
                // Dispatches the update events to the given adapter.
                diffResult.dispatchUpdatesTo(mRecyclerAdapter);
                mRecyclerAdapter.setDatas(newDatas);
                return true;
            }
            case R.id.single: {
                List<Model> newDatas = new ArrayList<>();
                for (Model model : mDatas) {
                    try {
                        //clone一遍旧数据 ，模拟刷新操作
                        newDatas.add(model.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                // 删除一个
//                newDatas.remove(1);

                // 更新一个Item的图片;
                newDatas.get(1).imageId = R.drawable.model03;

                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback<>(mDatas, newDatas), true);
                diffResult.dispatchUpdatesTo(mRecyclerAdapter);
                mRecyclerAdapter.setDatas(newDatas);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        private List<Model> mDatas = new ArrayList<>();

        public void setDatas(List<Model> datas) {
            mDatas.clear();
            mDatas.addAll(datas);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(DiffUtilActivity.this).inflate(R.layout.listitem_diff_util, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Model model = mDatas.get(position);
            holder.textView.setText(model.name);
            holder.imageView.measure(0, 0);
            Bitmap bitmap = BitmapUtil.decodeSampleBitmapFromResource(getResources(),
                    model.imageId, holder.imageView.getMeasuredWidth(), holder.imageView.getMeasuredHeight());
            holder.imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }
    }
}
