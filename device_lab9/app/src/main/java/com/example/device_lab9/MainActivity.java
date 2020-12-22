package com.example.device_lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.device_lab9.util.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        public static Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.locate).setOnClickListener(this);
        findViewById(R.id.bluetooth).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scan) { // 点击了“扫一扫”
            // WeFindActivity内嵌到WeChatActivity中，造成不会在底部弹出权限选择对话框，所以要通过WeChatActivity弹窗
            // 并且权限选择结果onRequestPermissionsResult要在WeChatActivity里面重写
            //TODO: 这个MainActivity可能有问题
            if (PermissionUtil.checkPermission(MainActivity.act, Manifest.permission.CAMERA, R.id.scan%4096)) {
                // 若已获得相机权限，就跳到扫描二维码页面
                PermissionUtil.goActivity(this, FindScanActivity.class);
            }
        }else if(view.getId() == R.id.locate){
            if (PermissionUtil.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, R.id.locate % 4096)) {
                PermissionUtil.goActivity(this, LocationActivity.class);
            }
        }else if(view.getId() == R.id.bluetooth){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Android6.0之后使用蓝牙需要定位权限
                if (PermissionUtil.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, R.id.bluetooth % 4096)) {
                    PermissionUtil.goActivity(this, BluetoothActivity.class);
                }
            } else {
                PermissionUtil.goActivity(this, BluetoothActivity.class);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == R.id.scan % 4096) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PermissionUtil.goActivity(this, FindScanActivity.class);
            } else {
                Toast.makeText(this, "需要允许相机权限才能扫描二维码噢", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == R.id.locate % 4096) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PermissionUtil.goActivity(this, LocationActivity.class);
            } else {
                Toast.makeText(this, "需要允许定位权限才能开始定位噢", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == R.id.bluetooth % 4096) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PermissionUtil.goActivity(this, BluetoothActivity.class);
            } else {
                Toast.makeText(this, "需要允许定位权限才能使用蓝牙噢", Toast.LENGTH_SHORT).show();
            }
        }
    }

}