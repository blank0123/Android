package com.example.demo0610;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static String rU="rU";
    private static final int CHOOSE_PHOTO = 1;
    private static final int GET_STORAGE_PERMISSION = 2;
    String rusername;
    String rpassword;
    String rrealname;
    String rstudentnum;
    String rphonenum;
    String rmajor;
    String rclassroom;
    String sex = "true";
    boolean switch1 = false;
    SharedPreferences sp;
    Context context;


//    private String imagepath = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        RadioGroup gender;

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.GRAY);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置导航按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final EditText uet = findViewById(R.id.register_username);
        final EditText pet = findViewById(R.id.register_password);
        final EditText rnet = findViewById(R.id.realname);
        final EditText snet = findViewById(R.id.studentnum);
        final EditText pnet = findViewById(R.id.phonenum);
        final EditText majoret = findViewById(R.id.major);
        final EditText cnet = findViewById(R.id.classname);
//        final Button registerbtn = findViewById(R.id.register);

        gender = findViewById(R.id.gender);
        RadioGroup.OnCheckedChangeListener radioGrouplisten = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int id = group.getCheckedRadioButtonId();
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio0:
//                        Toast.makeText(RegisterActivity.this, "male", Toast.LENGTH_SHORT).show();
                        sex = "false";
                        break;
                    case R.id.radio1:
//                        Toast.makeText(RegisterActivity.this, "female", Toast.LENGTH_SHORT).show();
                        sex = "true";
                        break;
                    default:
                        break;
                }
            }
        };
        gender.setOnCheckedChangeListener(radioGrouplisten);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
//                if (!isUserNameValid(uet.getText().toString())
////                        && !isPasswordValid(pet.getText().toString())
////                        && !isStudentIDValid(snet.getText().toString())
////                        && !isRealnameValid(rnet.getText().toString())
////                        && !isPhoneNumValid(pnet.getText().toString())
////                        && !isMajorValid(majoret.getText().toString())
////                        && !isClassnameValid(cnet.getText().toString())) {
////                    uet.setError("Invalid username");
////                    pet.setError("Password must be >3 characters!");
////                    rnet.setError("Invalid");
////                    snet.setError("Invalid Student ID!");
////                    pnet.setError("Invalid Phone number!");
////                    majoret.setError("Invalid");
////                    cnet.setError("Invalid");
////                } else {
////                    registerbtn.setEnabled(true);
////                    registerbtn.postInvalidate();
////                }
                if (!isUserNameValid(uet.getText().toString())) {
                    uet.setError("Invalid username");
                    switch1 = false;
                } else if (!isPasswordValid(pet.getText().toString())) {
                    pet.setError("Password must be >3 characters!");
                    switch1 = false;
                } else if (!isStudentIDValid(snet.getText().toString())){
                    snet.setError("Invalid Student ID!");
                    switch1 = false;
                } else if (!isRealnameValid(rnet.getText().toString())){
                    rnet.setError("Invalid");
                    switch1 = false;
                } else if (!isPhoneNumValid(pnet.getText().toString())){
                    pnet.setError("Invalid Phone number!");
                    switch1 = false;
                } else if (!isMajorValid(majoret.getText().toString())){
                    majoret.setError("Invalid");
                    switch1 = false;
                } else if (!isClassnameValid(cnet.getText().toString())){
                    cnet.setError("Invalid");
                } else {
                    switch1 = true;
                }
            }
        };
        uet.addTextChangedListener(afterTextChangedListener);
        pet.addTextChangedListener(afterTextChangedListener);
        snet.addTextChangedListener(afterTextChangedListener);
        pnet.addTextChangedListener(afterTextChangedListener);
        rnet.addTextChangedListener(afterTextChangedListener);
        majoret.addTextChangedListener(afterTextChangedListener);
        cnet.addTextChangedListener(afterTextChangedListener);


        // TODO:判断是否登陆成功后才可跳转至登陆界面loginActivity,提示注册成功

    }       // onCreate-------------------------------------

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                // User chose the "Settings" item, show the app settings UI...

                if (switch1 == false){
                    Toast.makeText(this, "请填写完整用户信息", Toast.LENGTH_SHORT).show();
                }   else{

                    final AlertDialog.Builder normalDialog = new AlertDialog.Builder(RegisterActivity.this);
                    //normalDialog.setIcon(R.drawable.buttom_yello);
                    normalDialog.setTitle("注册");
                    normalDialog.setMessage("您确定注册吗?");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(RegisterActivity.this,"注册中！",Toast.LENGTH_SHORT).show();

                                    EditText uet = findViewById(R.id.register_username);
                                    EditText pet = findViewById(R.id.register_password);
                                    EditText rnet = findViewById(R.id.realname);
                                    EditText snet = findViewById(R.id.studentnum);
                                    EditText pnet = findViewById(R.id.phonenum);
                                    EditText majoret = findViewById(R.id.major);
                                    EditText cnet = findViewById(R.id.classname);
                                    rusername = uet.getText().toString();
                                    rpassword = pet.getText().toString();
                                    rrealname = rnet.getText().toString();
                                    rstudentnum = snet.getText().toString();
                                    rphonenum = pnet.getText().toString();
                                    rmajor = majoret.getText().toString();
                                    rclassroom = cnet.getText().toString();


//                                    context = getApplicationContext();
//                                    //config为文件名，此时并不会创建文件，只有在执行putString()方法后文件才会被创建。
//                                    sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor=sp.edit();//获取SharedPreferences编辑器
//                                    editor.putString("username",rusername);
//                                    editor.putString("password", rstudentnum);
//                                    editor.putString("realname", rrealname);
//                                    editor.putString("studentnum", rstudentnum);
//                                    editor.putString("phonenum", rphonenum);
//                                    editor.putString("major", rmajor);
//                                    editor.putString("classroom", rclassroom);
//                                    editor.putString("sex", sex);
//                                    editor.commit();

                                    register(rusername,rpassword,rrealname,rstudentnum,sex,rphonenum,rmajor,rclassroom);

                                }
                            });
                    normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(RegisterActivity.this,"取消注册！",Toast.LENGTH_SHORT).show();
                                }
                            });
                    normalDialog.show();
                }


                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
    private boolean isStudentIDValid(String studentid) {
        return studentid != null && studentid.trim().length() == 13
                && studentid.matches("[0-9]+");
    }
    private boolean isPhoneNumValid(String PhoneNum) {
        return PhoneNum != null && PhoneNum.trim().length() == 11
                && PhoneNum.matches("[0-9]+");
    }
    private boolean isRealnameValid(String Realname) {
        return Realname != null && Realname.trim().length() >= 1;
    }
    private boolean isMajorValid(String Major) {
        return Major != null && Major.trim().length() >= 1;
    }
    private boolean isClassnameValid(String Classname) {
        return Classname != null && Classname.trim().length() >= 1;
    }


    private void register(String username, String password, String realname,
                          String studentnum, String gender, String phonenum,
                          String major, String classname)
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/register")
                .addParams("username", username)
                .addParams("password", password)
                .addParams("realname",realname)
                .addParams("studentnum",studentnum)
                .addParams("gender",gender)
                .addParams("phonenum",phonenum)
                .addParams("major",major)
                .addParams("classname",classname)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));
                        Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

                        intent.putExtra("rU",rusername);
                        startActivity(intent);

                    }
                });
    }










}
