package com.silion.androidproject.serializable;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializeActivity extends BaseActivity implements View.OnClickListener {
    private User[] mUsers;
    private User mAppendUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize);
        findViewById(R.id.btWrite).setOnClickListener(this);
        findViewById(R.id.btAppend).setOnClickListener(this);
        findViewById(R.id.btRead).setOnClickListener(this);
        mUsers = new User[] {new User("silion", 1), new User("silion", 2)};
        mAppendUser = new User("silion", 3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btWrite: {
                writeObject(mUsers, getDiskCacheDir(this, "UserSerializable"));
                break;
            }
            case R.id.btAppend: {
                appendObject(mAppendUser, getDiskCacheDir(this, "UserSerializable"));
                break;
            }
            case R.id.btRead: {
                List<Object> list = readObject(getDiskCacheDir(this, "UserSerializable"));
                android.util.Log.d("silion", list.toString());

//                for (Object object : list) {
//                    android.util.Log.d("silion", ((User) object).toString());
//                }
                break;
            }
            default:
                break;
        }
    }

    private List<Object> readObject(File file) {
        List<Object> list = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (fis.available() > 0) {
                list.add(ois.readObject());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void appendObject(Object o, String file) {
        appendObject(o, new File(file));
    }

    private void appendObject(Object o, File file) {
        /**
         * true 以追加模式创建文件流对象
         */
        try(FileOutputStream fos = new FileOutputStream(file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos) {
            @Override
            protected void writeStreamHeader(){
                // 重写 writeStreamHeader()方法，空实现
            }
            }) {
            // 写入对象
            oos.writeObject(o);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObject(Object[] objs, String file) {
        writeObject(objs, new File(file));
    }

    public void writeObject(Object[] objs, File file) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            // 写入对象
            for(Object o : objs)
            {
                oos.writeObject(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDiskCacheDir (Context context, String name) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + name);
    }
}
