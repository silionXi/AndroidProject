package com.silion.androidproject.progressdialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class ProgressDialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btNewDialog: {
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.show();
                break;
            }
            case R.id.btStatic1: {
                // 使用静态方式创建并显示，这种进度条只能是圆形条,设置title和Message提示内容
                ProgressDialog dialog = ProgressDialog.show(this, "标题", "正在登录中");
                break;
            }
            case R.id.btStatic2: {
                // 使用静态方式创建并显示，这种进度条只能是圆形条,这里最后一个参数boolean indeterminate设置是否是不明确的状态
                ProgressDialog dialog = ProgressDialog.show(this, "标题", "正在登录中", false);
                break;
            }
            case R.id.btStatic3: {
                // 使用静态方式创建并显示，这种进度条只能是圆形条,这里最后一个参数boolean cancelable 设置是否进度条是可以取消的
                ProgressDialog dialog = ProgressDialog.show(this, "标题", "正在登录中", false, true);
                break;
            }
            case R.id.btStatic4: {
                // 使用静态方式创建并显示，这种进度条只能是圆形条,这里最后一个参数 DialogInterface.OnCancelListener
                ProgressDialog dialog = ProgressDialog.show(this, "标题", "正在登录中", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogActivity.this, "对话框被Cancel了", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case R.id.btCircle: {
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                dialog.setCancelable(true);// 设置是否不可以通过点击Back键取消
                dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                dialog.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("标题");// 设置title
                // dismiss监听
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogActivity.this, "对话框被Dismiss了", Toast.LENGTH_SHORT).show();
                    }
                });
                // 监听Key事件被传递给dialog
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                });
                // 监听cancel事件
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogActivity.this, "对话框被Cancel了", Toast.LENGTH_SHORT).show();
                    }
                });
                //设置可点击的按钮，最多有三个(默认情况下)
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // 设置message
                dialog.setMessage("这是一个圆形进度对话框");
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        // cancel和dismiss方法本质都是一样的，都是从屏幕中删除Dialog,唯一的区别是
                        // 调用cancel方法会回调DialogInterface.OnCancelListener如果注册的话,dismiss方法不会回调
                        dialog.cancel();
//                        dialog.dismiss();
                    }
                }).start();
                break;
            }
            case R.id.btHorizontal: {
                // 进度条还有二级进度条的那种形式，这里就不演示了
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
                dialog.setCancelable(false);// 设置是否不可以通过点击Back键取消
                dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                dialog.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
                dialog.setTitle("标题");
                dialog.setMax(100);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogActivity.this, "对话框被Dismiss了", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ProgressDialogActivity.this, "对话框被Cancel了", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.setMessage("这是一个水平进度对话框");
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (i < 100) {
                            try {
                                Thread.sleep(200);
                                // 更新进度条的进度,可以在子线程中更新进度条进度
//                                dialog.incrementProgressBy(1); // 进度条递增1
                                dialog.setProgress(i);
                                // dialog.incrementSecondaryProgressBy(10)//二级进度条更新方式
                                i++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // 在进度条走完时删除Dialog
                        dialog.dismiss();
                    }
                }).start();
                break;
            }
            default:
                break;
        }
    }
}
