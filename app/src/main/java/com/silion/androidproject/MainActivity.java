package com.silion.androidproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new ListAdapter(this, null);
        mListView.setAdapter(mListAdapter);
        initData();
    }

    private void initData() {
        List<String> datas = new ArrayList<>();
        datas.add("二维码扫描工具-ZXing");
        mListAdapter.addAll(datas);
    }

    protected class ListAdapter extends SimpleBaseAdapter<String> {

        public ListAdapter(Context context, List<String> datas) {
            super(context, datas);
        }

        @Override
        public int getItemLayout() {
            return R.layout.listitem_main;
        }

        @Override
        public View getItemView(int position, View convertView, ViewHolder holder) {
            TextView textView = holder.getView(R.id.tvTile);
            textView.setText(getItem(position));
            return convertView;
        }
    }
}
