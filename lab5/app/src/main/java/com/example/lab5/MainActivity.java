package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_calendar).setOnClickListener(this);
        findViewById(R.id.btn_schedule).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_calendar) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.btn_schedule) {
            Intent intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);
        }

    }
}