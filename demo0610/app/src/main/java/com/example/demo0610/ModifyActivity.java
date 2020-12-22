package com.example.demo0610;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class ModifyActivity extends AppCompatActivity {

    public static String U="U";
    Toolbar toolbar;
    ImageView protrait;
    private static final int CHOOSE_PHOTO = 1;
    private static final int GET_STORAGE_PERMISSION = 2;
    String musername;
    String mpassword;
    String mrealname;
    String mstudentnum;
    String mphonenum;
    String mmajor;
    String mclassroom;
    String msex = "true";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        RadioGroup gender;

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.GRAY);
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置导航按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        protrait = findViewById(R.id.midify_portrait);
        protrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ModifyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //没有授权进行权限申请
                    ActivityCompat.requestPermissions(ModifyActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, GET_STORAGE_PERMISSION);}
                else {
                    pickPhoto();
                }
            }
        });

        final EditText uet = findViewById(R.id.username);
        final EditText pet = findViewById(R.id.password);
        final EditText rnet = findViewById(R.id.realname);
        final EditText snet = findViewById(R.id.studentnum);
        final EditText pnet = findViewById(R.id.phonenum);
        final EditText majoret = findViewById(R.id.major);
        final EditText cnet = findViewById(R.id.classname);
        final Button confirmbtn = findViewById(R.id.confirm);

        gender = findViewById(R.id.gender);
        RadioGroup.OnCheckedChangeListener radioGrouplisten = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int id = group.getCheckedRadioButtonId();
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio0:
//                        Toast.makeText(RegisterActivity.this, "male", Toast.LENGTH_SHORT).show();
                        msex = "false";
                        break;
                    case R.id.radio1:
//                        Toast.makeText(RegisterActivity.this, "female", Toast.LENGTH_SHORT).show();
                        msex = "true";
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
                if (!isUserNameValid(uet.getText().toString())) {
                    uet.setError("Invalid username");
                } else if (!isPasswordValid(pet.getText().toString())) {
                    pet.setError("Password must be >3 characters!");
                } else if (!isStudentIDValid(snet.getText().toString())){
                    snet.setError("Invalid Student ID!");
                } else if (!isRealnameValid(rnet.getText().toString())){
                    rnet.setError("Invalid");
                } else if (!isPhoneNumValid(pnet.getText().toString())){
                    pnet.setError("Invalid Phone number!");
                } else if (!isMajorValid(majoret.getText().toString())){
                    majoret.setError("Invalid");
                } else if (!isClassnameValid(cnet.getText().toString())){
                    cnet.setError("Invalid");
                } else {
                    confirmbtn.setEnabled(true);
                    confirmbtn.postInvalidate();
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



        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ModifyActivity.this,"confirm",Toast.LENGTH_LONG).show();
                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(ModifyActivity.this);
                //normalDialog.setIcon(R.drawable.buttom_yello);
                normalDialog.setTitle("修改个人信息");
                normalDialog.setMessage("您确定要修改个人信息吗?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ModifyActivity.this,"修改完成！",Toast.LENGTH_LONG).show();
                                musername = uet.getText().toString();
                                mpassword = pet.getText().toString();
                                mrealname = rnet.getText().toString();
                                mstudentnum = snet.getText().toString();
                                mphonenum = pnet.getText().toString();
                                mmajor = majoret.getText().toString();
                                mclassroom = cnet.getText().toString();
                                modify(musername,mpassword,mrealname,
                                    mstudentnum,msex,mphonenum,mmajor,mclassroom);
                                Intent intent = new Intent(ModifyActivity.this,UserActivity.class);
                                intent.putExtra("U",musername);
                                startActivity(intent);
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ModifyActivity.this,"取消了修改！",Toast.LENGTH_LONG).show();

                            }
                        });
                normalDialog.show();

            }
        });



        // TODO：修改后上传修改信息


    }   // onCreate-------------------------------------

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
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
                    Glide.with(this).load(data.getData()).into(protrait);
                }
        }
    }
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }


    private void modify(String username, String password, String realname, String studentnum, String gender, String phonenum, String major, String classname)
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
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.i("debug",Log.getStackTraceString(e));
                        Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        file_upload();
                        finish();
                    }
                });
    }

    // TODO:上传,修改代码
    private void file_upload()
    {

        String filepath; String filename;
        File file = new File("/storage/emulated/0/Download/59739640.jpg");
        if (!file.exists())
        {
            Toast.makeText(ModifyActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
        }
        else {
            OkHttpUtils
                    .post()
                    .url("http://47.97.173.230:8090/Android_Server/upload")
                    .addFile("59739640", "59739640.jpg", file)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("debug", Log.getStackTraceString(e));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        }

                    });
        }

    }


}
