package com.silion.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.silion.androidproject.layoutinflater.LayoutInflaterActivity;
import com.silion.androidproject.zxing.ZXingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new ListAdapter(this, null);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
        initData();
    }

    private void initData() {
        List<Demo> datas = new ArrayList<>();
        datas.add(new Demo("二维码扫描工具-ZXing", ZXingActivity.class));
        datas.add(new Demo("LayoutInflater的参数", LayoutInflaterActivity.class));
        mListAdapter.addAll(datas);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, mListAdapter.getItem(position).mClass);
        startActivity(intent);
    }

    protected class ListAdapter extends SimpleBaseAdapter<Demo> {

        public ListAdapter(Context context, List<Demo> datas) {
            super(context, datas);
        }

        @Override
        public int getItemLayout() {
            return R.layout.listitem_main;
        }

        @Override
        public View getItemView(int position, View convertView, ViewHolder holder) {
            TextView textView = holder.getView(R.id.tvTile);
            textView.setText(getItem(position).mName);
            return convertView;
        }
    }
}
