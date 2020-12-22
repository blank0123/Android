package com.example.housing_loan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ForgetActivity extends AppCompatActivity {

    Random r = new Random();
    int ram = r.nextInt(1000000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        Button confirm = findViewById(R.id.confirm);
        Button code = findViewById(R.id.code);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
                intent.putExtra("modify","confirm");
                startActivity(intent);
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                AlertDialog.Builder builder = new AlertDialog.Builder(ForgetActivity.this);
                builder.setTitle("请记住验证码");
                builder.setMessage("本次验证码是"+ram+"，请输入验证码");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        code.setText(String.valueOf(ram));
                    }
                });
                builder.show();
            }
        });

    }

}