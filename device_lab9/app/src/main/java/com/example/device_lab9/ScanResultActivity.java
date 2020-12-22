package com.example.device_lab9;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ouyangshen on 2017/11/4.
 */
@SuppressLint("SetTextI18n")
public class ScanResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        TextView tv_result = findViewById(R.id.tv_result);
        // 获取扫码页面传来的结果字符串
        String result = getIntent().getStringExtra("result");
        tv_result.setText("扫码结果为：" + result);
    }

}
