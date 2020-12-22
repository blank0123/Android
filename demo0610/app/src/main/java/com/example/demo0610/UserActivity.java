package com.example.demo0610;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Objects;

import okhttp3.Call;

public class UserActivity extends AppCompatActivity {

    Toolbar toolbar;
    boolean switch1 = false;
//    String sex = "true";
    SharedPreferences sp;
    Context context;
    String name;
    String passw;
    String remem;
    String stunum;
    String phnum;
    String maj;
    String cln;
    String sox;
    Uri uri;
    int i = R.mipmap.test1;
    private TextView utv ;
    private EditText ptv ;
    private EditText rntv ;
    private EditText sntv ;
    private EditText pntv ;
    private EditText majortv ;
    private EditText cntv ;
    private RadioButton radio0;
    private RadioButton radio1 ;
    private ProgressBar progressBar1;

    private ImageView portrait;
    private static final int CHOOSE_PHOTO = 1;
    private static final int GET_STORAGE_PERMISSION = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //TODO: 函数，
        //      1. 头像
        //      2. 获取用户信息
        //      3. 保存值

//        file_exist("username");     //TODO: 修改

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.GRAY);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置导航按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        final TextView utv = findViewById(R.id.username);
//        final EditText ptv = findViewById(R.id.password);
//        final EditText rntv = findViewById(R.id.realname);
//        final EditText sntv = findViewById(R.id.studentnum);
//        final EditText pntv = findViewById(R.id.phonenum);
//        final EditText majortv = findViewById(R.id.major);
//        final EditText cntv = findViewById(R.id.classname);
//        final RadioButton radio0 =findViewById(R.id.radio0);
//        final RadioButton radio1 =findViewById(R.id.radio1);

        utv = findViewById(R.id.username);
        ptv = findViewById(R.id.password);
        rntv = findViewById(R.id.realname);
        sntv = findViewById(R.id.studentnum);
        pntv = findViewById(R.id.phonenum);
        majortv = findViewById(R.id.major);
        cntv = findViewById(R.id.classname);
        radio0 =findViewById(R.id.radio0);
        radio1 =findViewById(R.id.radio1);
//        progressBar1=findViewById(R.id.progressBar1);
        //TODO:获取用户信息
        context = getApplicationContext();
        sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        name = sp.getString("username","");
        get_all(name);
//         passw=sp.getString("password","");
//         remem =sp.getString("realname","");
//         stunum =sp.getString("studentnum","");
//         phnum = sp.getString("phonenum","");
//         maj= sp.getString("major", "");
//         cln=sp.getString("classroom", "");
//         sox = sp.getString("sex", "");

        utv.setText(name);
        ptv.setText(passw);
        rntv.setText(remem);
        sntv.setText(stunum);
        pntv.setText(phnum);
        majortv.setText(maj);
        cntv.setText(cln);
        sox = "true";
        if (sox.equals("true")){
            radio0.setChecked(true);
            radio1.setChecked(false);
        }else{
            radio1.setChecked(true);
            radio0.setChecked(false);
        }


        portrait = findViewById(R.id.portrait);
        RequestOptions option2 = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(this)
                .load(i)
                .apply(option2)
                .into(portrait);
//        file_exist(name);

//        Intent intent = getIntent();
//        if(intent!=null)
//        {
//            String U = intent.getStringExtra(LoginActivity.U);
//            utv.setText(U);
//            name=utv.getText().toString();
//            demo s=(demo)intent.getSerializableExtra(MainActivity.C);
//            setTitle("名字是："+ s.getUserName());
//        }

        RadioGroup gender;
        gender = findViewById(R.id.gender);
        RadioGroup.OnCheckedChangeListener radioGrouplisten = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                int id = group.getCheckedRadioButtonId();
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio0:
//                        Toast.makeText(RegisterActivity.this, "male", Toast.LENGTH_SHORT).show();
                        sox = "false";
                        break;
                    case R.id.radio1:
//                        Toast.makeText(RegisterActivity.this, "female", Toast.LENGTH_SHORT).show();
                        sox = "true";
                        break;
                    default:
                        break;
                }
            }
        };
        gender.setOnCheckedChangeListener(radioGrouplisten);

//        final Button edit =findViewById(R.id.edit);
//        final EditText edit1 =findViewById(R.id.edit1);
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (switch1 == false){
//                    edit1.setEnabled(true);
//                    edit.setText("提交");
//                    switch1 = true;
//                } else {
//                    edit1.setEnabled(false);
//                    edit.setText("修改");
//                    switch1 = false;
//                }
//
//            }
//
//        });


        portrait = findViewById(R.id.portrait);
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1 == true){
                    if (ContextCompat.checkSelfPermission(UserActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //没有授权进行权限申请
                        ActivityCompat.requestPermissions(UserActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, GET_STORAGE_PERMISSION);}
                    else {
                        pickPhoto();
                    }
                }
            }
        });

    }   //onCreate --------------------------------------------------------------------------------

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar1, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify:
                // User chose the "Settings" item, show the app settings UI...

                if (switch1 == true){

                    item.setIcon(R.drawable.modify);
                    switch1 = false  ;

                    passw=ptv.getText().toString();
                    remem=rntv.getText().toString();
                    stunum=sntv.getText().toString();
                    phnum=pntv.getText().toString();
                    maj=majortv.getText().toString();
                    cln=cntv.getText().toString();
                    if (radio0.isChecked()){
                        sox="true";
                    }else if(!radio0.isChecked()){
                        sox="false";
                    }

//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("password",passw);
//                    editor.putString("realname",remem);
//                    editor.putString("studentnum",stunum);
//                    editor.putString("phonenum",phnum);
//                    editor.putString("major", maj);
//                    editor.putString("classroom",cln );
//                    editor.putString("sex", sox);
//                    editor.commit();

                    ptv.setEnabled(false);
                    rntv.setEnabled(false);
                    sntv.setEnabled(false);
                    pntv.setEnabled(false);
                    majortv.setEnabled(false);
                    cntv.setEnabled(false);

                    radio0.setEnabled(false);
                    radio1.setEnabled(false);

//                    file_upload();
                    modify(name,passw,remem,stunum,sox,phnum,maj,cln);
                    Toast.makeText(this, "已保存！！！", Toast.LENGTH_SHORT).show();
                    //TODO: 保存函数

                } else{


                    final AlertDialog.Builder normalDialog = new AlertDialog.Builder(UserActivity.this);
                    //normalDialog.setIcon(R.drawable.buttom_yello);
                    normalDialog.setTitle("编辑");
                    normalDialog.setMessage("您确定要编辑信息吗?");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(UserActivity.this,"注册中！",Toast.LENGTH_SHORT).show();

                                    item.setIcon(R.drawable.save);
                                    switch1 = true  ;
                                    final EditText ptv = findViewById(R.id.password);
                                    final EditText rntv = findViewById(R.id.realname);
                                    final EditText sntv = findViewById(R.id.studentnum);
                                    final EditText pntv = findViewById(R.id.phonenum);
                                    final EditText majortv = findViewById(R.id.major);
                                    final EditText cntv = findViewById(R.id.classname);

                                    ptv.setEnabled(true);
                                    rntv.setEnabled(true);
                                    sntv.setEnabled(true);
                                    pntv.setEnabled(true);
                                    majortv.setEnabled(true);
                                    cntv.setEnabled(true);

                                    RadioButton radio0 = findViewById(R.id.radio0);
                                    radio0.setEnabled(true);
                                    RadioButton radio1 = findViewById(R.id.radio1);
                                    radio1.setEnabled(true);
                                }
                            });
                    normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(UserActivity.this,"取消编辑！",Toast.LENGTH_SHORT).show();
                                }
                            });
                    normalDialog.show();

                }
                return true;
            case R.id.logout:
                Toast.makeText(this,"退出登陆",Toast.LENGTH_SHORT).show();

                context = getApplicationContext();
                sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("autologin","false");
                editor.commit();

                update_auto(name);
                Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //TODO: 从服务器获取用户信息

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GET_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    //使用Glide来加载图片data.getData()得到图片的Uri
                    assert data != null;
                    uri = data.getData();
                    Glide.with(this).load(uri).into(portrait);
                }
        }
    }
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    private void modify(String username, String password, String realname,
                        String studentnum, String gender, String phonenum, String major, String classname)
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/modify")
                .addParams("username", username)
                .addParams("password", password)
                .addParams("realname",realname)
                .addParams("studentnum",studentnum)
                .addParams("gender",gender)
                .addParams("phonenum",phonenum)
                .addParams("major",major)
                .addParams("classname",classname)
                .addParams("oldname",username)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.i("debug",Log.getStackTraceString(e));
//                        Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(getApplicationContext(), "修改完成", Toast.LENGTH_SHORT).show();
//                        file_upload();
//                        finish();
                    }
                });
    }


    private void update_auto(String username){
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/updateauto")
                .addParams("username", username)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                });
    }

    // TODO:上传,修改代码
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void file_upload()
    {

        String filepath; String filename;
        File file = new File(Objects.requireNonNull(uri.getPath()));
        if (!file.exists())
        {
            Toast.makeText(UserActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
        }
        else {
            OkHttpUtils
                    .post()
                    .url("http://47.97.173.230:8090/Android_Server/upload")
                    .addFile(name, name+".jpg", file)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("debug", Log.getStackTraceString(e));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Toast.makeText(getApplicationContext(), "头像上传成功", Toast.LENGTH_SHORT).show();

                        }

                    });
        }

    }
    // TODO:下载
    private void file_download(String filename){
//        String name = filename;
        //String name = "59739640.jpg";
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/download")
                .addParams("filename",filename)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),name+".jpg") {
                    @Override
                    public void inProgress(float progress, long total, int id) {

                        progressBar1.setProgress((int)(100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.i("debug",Log.getStackTraceString(e));
                    }

                    @Override
                    public void onResponse(File response, int id) {
//                        Log.e(TAG, "onResponse :" + response.getAbsolutePath());
                        portrait = findViewById(R.id.portrait);
                        RequestOptions option2 = new RequestOptions()//圆形图片
                                .circleCrop();
                        Glide.with(UserActivity.this)
                                .load(Uri.fromFile(response))
                                .apply(option2)
                                .into(portrait);
                    }
                });
    }

    // TODO:查看服务器是否存在图片的方法
    private void file_exist(final String usernam)
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/filedir")
                .addParams("username", usernam)
                .build()
                .execute(new StringCallback() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));
                        portrait = findViewById(R.id.portrait);
                        RequestOptions option2 = new RequestOptions()//圆形图片
                                .circleCrop();
                        Glide.with(UserActivity.this)
                                .load(R.id.portrait)
                                .apply(option2)
                                .into(portrait);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(getApplicationContext(), "下载头像", Toast.LENGTH_SHORT).show();
                        //返回true表示服务器存在用户保存图片
                        //false表示不存在
                        file_download(usernam);
                    }
                });
    }

    private void get_all(String na){
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/userinfo")
                .addParams("username",na)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        JSONObject obj;
                        JSONArray jsonArray = JSONObject.parseArray(response);
                        obj = jsonArray.getJSONObject(0);
                        passw = obj.getString("password");
                        remem = obj.getString("realname");
                        stunum = obj.getString("studentnum");
                        sox = obj.getString("gender");
                        phnum = obj.getString("phonenum");
                        maj = obj.getString("major");
                        cln = obj.getString("classname");

                        ptv.setText(passw);
                        rntv.setText(remem);
                        sntv.setText(stunum);
                        pntv.setText(phnum);
                        majortv.setText(maj);
                        cntv.setText(cln);
                        if (sox.equals("true")){
                            radio0.setChecked(true);
                            radio1.setChecked(false);
                        }else if(sox.equals("false")){
                            radio0.setChecked(false);
                            radio1.setChecked(true);
                        }
                    }
                });
    }

}
