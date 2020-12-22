package com.example.housing_loan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean forgotten = false;
    String phone = "";
    Random r = new Random();
    int ram = r.nextInt(1000000);
    Intent intent = getIntent();
    String mod = "";
    String user = "个人用户";
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.login);
        Button forget = findViewById(R.id.forget);
        RadioGroup log = findViewById(R.id.log);
        CheckBox remember = findViewById(R.id.remember);
        EditText phone_number = findViewById(R.id.phone_number);
        EditText password = findViewById(R.id.password_login);
        TextView password_text = findViewById(R.id.password_text);
        RadioButton radio0 =findViewById(R.id.radio0);
        RadioButton radio1 =findViewById(R.id.radio1);
        Spinner spinner = findViewById(R.id.spinner);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(intent!=null){
            mod = intent.getStringExtra("modify");
            if (mod.equals("confirm")){
                forgotten = false;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    user = "个人用户";
                }else if (position == 1){
                    user = "企业用户";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio0.isChecked()) {
                    if (!phone_number.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        phone = phone_number.getText().toString();

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("登陆成功");

                        builder.setMessage("您的手机号码是"+phone+"，类型是"+user+"。恭喜你通过登陆验证，点击“确定”按钮返回上一个页面");
                        builder.setNegativeButton("我再想想",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        });
                        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                    } else {
                        Toast.makeText(MainActivity.this, "请填写完整手机号码或密码！", Toast.LENGTH_SHORT).show();
                    }
                } else if (radio1.isChecked()){
                    if (!forgotten){
                        if (!phone_number.getText().toString().equals("") && !password.getText().toString().equals("")) {
                            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "请填写完整手机号码或验证码！", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if (!phone_number.getText().toString().equals("") && !password.getText().toString().equals("")) {
                            Intent intent = new Intent(MainActivity.this, ForgetActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "请填写完整手机号码或验证码！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                phone = phone_number.getText().toString();
                if (radio0.isChecked()){
                    radio0.setChecked(false);
                    radio1.setChecked(true);
                    forgotten = true;       //这个必须最后
                    Toast.makeText(MainActivity.this, "验证码登陆后，即可修改密码！", Toast.LENGTH_SHORT).show();
                }else{
                    // 弹出获取到的验证码的对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    if (phone.length()<11){
                        builder.setTitle("请填写完整电话号码！");
                        //    设置Content来显示一个信息
                        //    设置一个PositiveButton
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
//                                  Toast.makeText(MainActivity.this, phone, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        builder.setTitle("请记住验证码");
                        builder.setMessage("手机号"+phone+"，本次验证码是"+ram+"，请输入验证码");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                forget.setText(String.valueOf(ram));
                            }
                        });
                    }
                    //    显示出该对话框
                    builder.show();
                }
            }
        });

        RadioGroup.OnCheckedChangeListener radioGrouplisten = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int id = group.getCheckedRadioButtonId();
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio0:
                        password_text.setText("登陆密码:");
                        password.setHint("请输入密码");
                        forget.setText("忘记密码");
                        remember.setVisibility(View.VISIBLE);
                        forgotten = false;
//                        Toast.makeText(MainActivity.this, String.valueOf(forgotten), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio1:
                        password_text.setText("    验证码:");
                        password.setHint("请输入验证码");
                        forget.setText("获取验证码");
                        remember.setVisibility(View.INVISIBLE);
//                        Toast.makeText(MainActivity.this, String.valueOf(forgotten), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        log.setOnCheckedChangeListener(radioGrouplisten);

    }
}