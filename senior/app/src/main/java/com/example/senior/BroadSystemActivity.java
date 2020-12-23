package com.example.senior;

import java.util.Set;

import com.example.senior.util.DateUtil;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;


@SuppressLint("StaticFieldLeak")
public class BroadSystemActivity extends AppCompatActivity {
    private static TextView tv_system;
    private static String desc = "开始侦听分钟广播，请稍等。注意要保持屏幕亮着，才能正常收到广播";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_system);
        tv_system = findViewById(R.id.tv_system);
        tv_system.setText(desc);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 创建一个分钟变更的广播接收器
        timeReceiver = new TimeReceiver();
        // 创建一个意图过滤器，只处理系统分钟变化的广播
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        // 注册广播接收器，注册之后才能正常接收广播
        registerReceiver(timeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播接收器，注销之后就不再接收广播
        unregisterReceiver(timeReceiver);
    }

    // 声明一个分钟广播的接收器
    private TimeReceiver timeReceiver;
    // 定义一个分钟广播的接收器
    public static class TimeReceiver extends BroadcastReceiver {

        // 一旦接收到分钟变更的广播，马上触发接收器的onReceive方法
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String content = "";
                Bundle bundle = intent.getExtras(); // 获取广播携带的包裹
                if (bundle != null) {
                    Set<String> key_set = bundle.keySet();
                    for (String key : key_set) {
                        content = String.format("%s\n%s=%s", content, key, bundle.get(key));
                    }
                }
                desc = String.format("%s\n%s 收到一个%s广播, 内容是%s", desc,
                        DateUtil.getNowTime(), intent.getAction(), content);
                tv_system.setText(desc);
            }
        }
    }

}
