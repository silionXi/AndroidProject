package com.silion.androidproject.materialdesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialDesignActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private List<Person> mPersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.naviView);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_more);
        }
        navigationView.setCheckedItem(R.id.menuCall);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "开始下载", Snackbar.LENGTH_SHORT)
                        .setAction("取消下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MaterialDesignActivity.this, "已经取消下载了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        initPerson();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        PersonAdapter adapter = new PersonAdapter();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initPerson() {
        for (int i = 0; i < 2; i++) {
            mPersonList.add(new Person("Girl", R.drawable.girl01));
            mPersonList.add(new Person("Girl", R.drawable.girl02));
            mPersonList.add(new Person("Girl", R.drawable.girl03));
            mPersonList.add(new Person("Girl", R.drawable.girl04));
            mPersonList.add(new Person("Girl", R.drawable.girl05));
            mPersonList.add(new Person("Girl", R.drawable.girl06));
            mPersonList.add(new Person("Girl", R.drawable.girl07));
            mPersonList.add(new Person("Girl", R.drawable.girl08));
            mPersonList.add(new Person("Girl", R.drawable.girl09));
            mPersonList.add(new Person("Girl", R.drawable.girl10));
            mPersonList.add(new Person("Girl", R.drawable.girl11));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.mStar: {
                Toast.makeText(this, "你点击了Star", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.mDelete: {
                Toast.makeText(this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.mEdit: {
                Toast.makeText(this, "你点击了Edit", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_person, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Person person = mPersonList.get(position);
            holder.ivImage.setImageResource(person.getImageId());
            holder.tvName.setText(person.getName());
        }

        @Override
        public int getItemCount() {
            return mPersonList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            ImageView ivImage;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            }
        }
    }
}
