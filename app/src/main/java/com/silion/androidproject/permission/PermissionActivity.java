package com.silion.androidproject.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

public class PermissionActivity extends BaseActivity implements View.OnClickListener {
    private final int CALENDAR_REQUEST_CODE = 0;
    private final int CAMERA_REQUEST_CODE = 1;
    private final int CONTACTS_REQUEST_CODE = 2;
    private final int LOCATION_REQUEST_CODE = 3;
    private final int MICROPHONE_REQUEST_CODE = 4;
    private final int PHONE_REQUEST_CODE = 5;
    private final int SENSORS_REQUEST_CODE = 6;
    private final int SMS_REQUEST_CODE = 7;
    private final int STORAGE_REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Button calendarButton = (Button) findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(this);
        Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(this);
        Button contactsButton = (Button) findViewById(R.id.contactsButton);
        contactsButton.setOnClickListener(this);
        Button locationButton = (Button) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(this);
        Button microPhoneButton = (Button) findViewById(R.id.microPhoneButton);
        microPhoneButton.setOnClickListener(this);
        Button phoneButton = (Button) findViewById(R.id.phoneButton);
        phoneButton.setOnClickListener(this);
        Button sensorsButton = (Button) findViewById(R.id.sensorsButton);
        sensorsButton.setOnClickListener(this);
        Button smsButton = (Button) findViewById(R.id.smsButton);
        smsButton.setOnClickListener(this);
        Button storageButton = (Button) findViewById(R.id.storageButton);
        storageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendarButton:
                requestPermission(Manifest.permission.WRITE_CALENDAR, "日历", CALENDAR_REQUEST_CODE);
                break;
            case R.id.cameraButton:
                requestPermission(Manifest.permission.CAMERA, "相机", CAMERA_REQUEST_CODE);
                break;
            case R.id.contactsButton:
                requestPermission(Manifest.permission.WRITE_CONTACTS, "联系人", CONTACTS_REQUEST_CODE);
                break;
            case R.id.locationButton:
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, "定位", LOCATION_REQUEST_CODE);
                break;
            case R.id.microPhoneButton:
                requestPermission(Manifest.permission.RECORD_AUDIO, "麦克风", MICROPHONE_REQUEST_CODE);
                break;
            case R.id.phoneButton:
                requestPermission(Manifest.permission.CALL_PHONE, "电话", PHONE_REQUEST_CODE);
                break;
            case R.id.sensorsButton:
                requestPermission(Manifest.permission.BODY_SENSORS, "传感器", SENSORS_REQUEST_CODE);
                break;
            case R.id.smsButton:
                requestPermission(Manifest.permission.SEND_SMS, "短信", SMS_REQUEST_CODE);
                break;
            case R.id.storageButton:
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", STORAGE_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    public void requestPermission(final String permisson, String name, final int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permisson) != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permisson)) {
                new AlertDialog.Builder(this)
                        .setMessage("申请" + name + "权限")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请权限
                                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{permisson}, requestCode);
                            }
                        })
                        .show();
            } else {
                // 申请权限
                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{permisson}, requestCode);
            }
        } else {
            Toast.makeText(this, name + "权限已申请", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALENDAR_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "日历权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CALENDAR)) {
                        Toast.makeText(this, "日历权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "相机权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        Toast.makeText(this, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case CONTACTS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "联系人权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)) {
                        Toast.makeText(this, "联系人权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "定位权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Toast.makeText(this, "定位权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case MICROPHONE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "麦克风权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                        Toast.makeText(this, "麦克风权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case PHONE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "电话权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                        Toast.makeText(this, "电话权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case SENSORS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "传感器权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BODY_SENSORS)) {
                        Toast.makeText(this, "传感器权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case SMS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "短信权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                        Toast.makeText(this, "短信权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "存储权限已申请", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户勾选了不在询问
                    // 提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(this, "存储权限已被禁止", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
