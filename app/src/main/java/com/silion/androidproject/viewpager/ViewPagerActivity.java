package com.silion.androidproject.viewpager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.silion.androidproject.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BitmapView bitmapView = mViewList.get(position);
            ImageView iv = (ImageView) ViewPagerActivity.this.getLayoutInflater().inflate(R.layout.viewpager_show, mViewPager, false);
            getBitmap(iv, bitmapView.mUrl, bitmapView.mRes, bitmapView.mRes);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
    private List<BitmapView> mViewList = new ArrayList<>();
    private ViewPagerScroller mViewPagerScroller;
    private int[] mRes = new int[]{R.drawable.viewpager1, R.drawable.viewpager2, R.drawable.viewpager3};
    private RequestQueue mQueue;
    private String[] mUrl = new String[]{"http://img.7160.com/uploads/allimg/120612/1-120612123Z1.jpg", "http://imgsrc.baidu.com/forum/w%3D580/sign=56d8ac1b8d13632715edc23ba18ea056/15d435f4e0fe9925fb36599733a85edf8cb17138.jpg", "http://www.bz55.com/uploads/allimg/150324/140-150324152G1-51.jpg"};
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
        mQueue = Volley.newRequestQueue(ViewPagerActivity.this);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache(this));
        loadView();
        initViewPagerScroller();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViewPagerScroller() {
        try {
            Field scroller;
            scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            mViewPagerScroller = new ViewPagerScroller(this, (Interpolator) interpolator.get(null));
            scroller.set(mViewPager, mViewPagerScroller);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadView() {
        verifyStoragePermissions(this);
        mViewList.clear();

        for (int i = 0; i < 3; i++) {
            mViewList.add(new BitmapView(mRes[i], mUrl[i]));
        }
        mPagerAdapter.notifyDataSetChanged();
    }


    public void getBitmap(ImageView iv, String url, int defaultImg, int errorImg) {
        // 使用Volley加载网络图片
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, defaultImg, errorImg);
        mImageLoader.get(url, listener);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public static void verifyStoragePermissions(AppCompatActivity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
