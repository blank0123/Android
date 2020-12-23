package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

public class User_login extends Activity {

    private String check = "false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.user_login);



        final EditText uet = findViewById(R.id.username);
        final EditText pet = findViewById(R.id.password);
        final Button login = findViewById(R.id.login);
        Log.i("user", uet +"//////////");
        Log.i("user", pet +"//////////");
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                    if (!isUserNameValid(uet.getText().toString())) {
                        uet.setError("Invalid username");
                    } else if (!isPasswordValid(pet.getText().toString())) {
                        pet.setError("Password must be >3 characters");
                    } else {
                        login.setEnabled(true);

                        login.postInvalidate();
                    }
                }
        };
        uet.addTextChangedListener(afterTextChangedListener);
        pet.addTextChangedListener(afterTextChangedListener);

        login.setOnClickListener(new OnClickListener(){

            public void onClick(View v) {

                login(uet.getText().toString(),pet.getText().toString());
            }
        });

    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 3;
    }

    private void login(String username, String password)
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/login")
                .addParams("username",username )
                .addParams("password",password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                        Log.i("debug",Log.getStackTraceString(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("true")){
                            //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(User_login.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}
