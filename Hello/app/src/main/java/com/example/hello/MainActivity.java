package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("ActivityLife","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ActivityLife","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ActivityLife", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ActivityLife", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ActivityLife", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ActivityLife", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ActivityLife", "onRestart");
    }
}
