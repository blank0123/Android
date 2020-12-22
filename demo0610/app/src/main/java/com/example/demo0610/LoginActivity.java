package com.example.demo0610;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    public static String U="U";
    String remember_username;
    String remember_password;
    String check_remember;
    String check_autologin;
//    String empty = "false";
    SharedPreferences sp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.GRAY);
        }

        // TODO：测试，暂时停用自动登陆
//        checkempty();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        final EditText uet = findViewById(R.id.username);
        final EditText uet = findViewById(R.id.username);
        final EditText pet = findViewById(R.id.password);
        final CheckBox remember_cbox =findViewById(R.id.remember);
        final CheckBox autologin_cbox =findViewById(R.id.atuologin);
        final Button login = findViewById(R.id.login);
        final Button register = findViewById(R.id.register);

        Intent intent = getIntent();
        if(intent!=null)
        {
            String rU = intent.getStringExtra(RegisterActivity.rU);
            uet.setText(rU);
//            demo s=(demo)intent.getSerializableExtra(MainActivity.C);
//            setTitle("名字是："+ s.getUserName());
        }

        context = getApplicationContext();
        sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        String name = sp.getString("username","");
        String password=sp.getString("password","");
        String remem =sp.getString("remember","");
        String auto =sp.getString("autologin","");

//       getString(name,defValue)的第二个参数为当读取的文件中不存在第一个参数name的时候，为name赋的值。这里设的空。

        if (remem.equals("true")){
//        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
            uet.setText(name);
            pet.setText(password);
            remember_cbox.setChecked(true);
            login.setEnabled(true);
            if(auto.equals("true")){
                Toast.makeText(LoginActivity.this,"自动登陆",Toast.LENGTH_SHORT).show();
                autologin_cbox.setChecked(true);
                Intent intent_autologin = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent_autologin);
            }
            else{
                autologin_cbox.setChecked(false);
            }
        }

        remember_username = uet.getText().toString();
        remember_password = pet.getText().toString();
        if (remember_cbox.isChecked()){
            check_remember = "true"  ;
        } else{
            check_remember = "false"  ;
        }
        if (autologin_cbox.isChecked()){
            check_autologin = "true"  ;
        } else{
            check_autologin = "false"  ;
        }

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUserNameValid(uet.getText().toString()) && !isPasswordValid(pet.getText().toString())) {
                    uet.setError("Invalid username");
                    pet.setError("Password must be >3 characters");
                } else {
                    login.setEnabled(true);
                    remember_username = uet.getText().toString();
                    remember_password = pet.getText().toString();
                    login.postInvalidate();
                }
            }
        };
        uet.addTextChangedListener(afterTextChangedListener);
        pet.addTextChangedListener(afterTextChangedListener);

        remember_cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    Toast.makeText(LoginActivity.this, "remember", Toast.LENGTH_SHORT).show();
                    check_remember="true";
                }else {
//                    Toast.makeText(LoginActivity.this, " not remember", Toast.LENGTH_SHORT).show();
                    check_remember="false";
                }
            }
        });

        autologin_cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    Toast.makeText(LoginActivity.this, "autologin", Toast.LENGTH_SHORT).show();
                    check_autologin="true";
                    remember_cbox.setChecked(true);
                }else {
//                    Toast.makeText(LoginActivity.this, " not autologin", Toast.LENGTH_SHORT).show();
                    check_autologin="false";
                    remember_cbox.setChecked(false);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

//                remember_username = uet.getText().toString();
//                remember_password = pet.getText().toString();


                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("U",remember_username);
                startActivity(intent);
                //TODO:测试
//                login(uet.getText().toString(),pet.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }   // onCreate---------------------------------------------------------------------------------------

//
//    private void checke_remember()//查看记住密码
//    {

//        Toast.makeText(this,username,Toast.LENGTH_SHORT).show();

//    }





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
                        Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();

//                        Log.i("debug",Log.getStackTraceString(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (response.equals("true")){

                            context = getApplicationContext();
                            //config为文件名，此时并不会创建文件，只有在执行putString()方法后文件才会被创建。
                            sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();//获取SharedPreferences编辑器
                            editor.putString("username",remember_username);
                            editor.putString("password", remember_password);
                            editor.putString("remember", check_remember);
                            editor.putString("autologin", check_autologin);
                            editor.commit();

                            setremember(remember_username,remember_password,check_remember,check_autologin);
//                            setremember("root","root","true","true","true");
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("U",remember_username);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void checkempty()//查看记住密码
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/checkempty")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        if(response.equals("false"))//没有记录的用户（第一次登录）服务器
                        {

                        }
                        else if(response.equals("true"))//用户设置记住密码
                        {
                            getremember();
                            final EditText uet = findViewById(R.id.username);
                            final EditText pet = findViewById(R.id.password);
                            uet.setText(remember_username);
                            pet.setText(remember_password);
                            checkauto();
                        }
                        else{//没点“记住密码”，但是记住用户名
                            String username = response;
                        }
                    }
                });
    }

    private void checkauto()//查看是否自动登录
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/checkauto")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        if(response.equals("false"))//没有设置自动登录
                        {

                        }
                        else if(response.equals("true"))
                        {
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("U",remember_username);
                            startActivity(intent);
                        }
                    }
                });
    }





    private void getremember()//返回用户名和密码
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/getremember")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject obj;
                        JSONArray jsonArray = JSONObject.parseArray(response);
                        obj = jsonArray.getJSONObject(0);
                        remember_username = obj.getString("username");
                        remember_password = obj.getString("password");
                    }
                });
    }


    // 如果登陆成功，则设置
    private void setremember(String username, String password, String remember, String auto_login)
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/setremember")
                .addParams("username", username)
                .addParams("password", password)
                .addParams("remember",remember)
                .addParams("auto_login",auto_login)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                });
    }











}
