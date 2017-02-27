package com.silion.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.silion.androidproject.aidl.AIDLActivity;
import com.silion.androidproject.alarm.AlarmActivity;
import com.silion.androidproject.customview.CustomViewActivity;
import com.silion.androidproject.jackson.JacksonActivity;
import com.silion.androidproject.layoutinflater.LayoutInflaterActivity;
import com.silion.androidproject.materialdesign.MaterialDesignActivity;
import com.silion.androidproject.network.simplenet.SimpleNetActivity;
import com.silion.androidproject.network.socket.SocketActivity;
import com.silion.androidproject.notification.NotificationActivity;
import com.silion.androidproject.permission.PermissionActivity;
import com.silion.androidproject.progressdialog.ProgressDialogActivity;
import com.silion.androidproject.recycleview.RecyclerViewActivity;
import com.silion.androidproject.serializable.SerializeActivity;
import com.silion.androidproject.tablayout.TablayoutActivity;
import com.silion.androidproject.viewpager.ViewPagerActivity;
import com.silion.androidproject.zxing.ZXingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
        datas.add(new Demo("Jackson的使用", JacksonActivity.class));
        datas.add(new Demo("对象序列化", SerializeActivity.class));
        datas.add(new Demo("ProgressDialog使用总结", ProgressDialogActivity.class));
        datas.add(new Demo("自定义Scroller的ViewPager", ViewPagerActivity.class));
        datas.add(new Demo("Design库-TabLayout", TablayoutActivity.class));
        datas.add(new Demo("Android 6.0运行时权限详解", PermissionActivity.class));
        datas.add(new Demo("RecycleView", RecyclerViewActivity.class));
        datas.add(new Demo("通知", NotificationActivity.class));
        datas.add(new Demo("MaterialDesign", MaterialDesignActivity.class));
        datas.add(new Demo("定时任务", AlarmActivity.class));
        datas.add(new Demo("AIDL", AIDLActivity.class));
        datas.add(new Demo("自定义view", CustomViewActivity.class));
        datas.add(new Demo("Socket模拟Http Post请求服务器", SocketActivity.class));
        datas.add(new Demo("网络框架的设计与实现", SimpleNetActivity.class));
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
