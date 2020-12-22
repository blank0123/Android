package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String res1="";     //screen1显示的值
    String res2="";     //screen2显示的值
    double d1;          //第一个小数类型
    double d2;          //第二个小数类型
    int operator = 0;   //运算符标志
    TextView screen1 ;
    TextView screen2 ;
    boolean point_existed = false;      //是否存在小数点

    //是否为按了等号first之后的第一次按数字的情况，是则清空res1，res2，保留d2
    //√如果按了等号之后的第一次按CE，也会清空res1，res2，保留d2
    //按了等号后，再按+-*/，要将d1=d2
    //false表示按了运算符后还没按等号
    //true表示按了运算符后，按了等号
    boolean first = false;

    //各运算符标记，避免重复点击
    boolean jiaf=false;
    boolean jianf=false;
    boolean chengf=false;
    boolean chuf=false;



    String success;      //继承连续点击等号时的中间值

    String deng_shu = "";   //等号后数字，中间值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen1 = findViewById(R.id.screen1);
        screen2 = findViewById(R.id.screen2);
        ImageButton a0 = findViewById(R.id.a0);
        ImageButton a1 = findViewById(R.id.a1);
        ImageButton a2 = findViewById(R.id.a2);
        ImageButton a3 = findViewById(R.id.a3);
        ImageButton a4 = findViewById(R.id.a4);
        ImageButton a5 = findViewById(R.id.a5);
        ImageButton a6 = findViewById(R.id.a6);
        ImageButton a7 = findViewById(R.id.a7);
        ImageButton a8 = findViewById(R.id.a8);
        ImageButton a9 = findViewById(R.id.a9);
        ImageButton ce = findViewById(R.id.ce);
        ImageButton chu = findViewById(R.id.chu);
        ImageButton cheng = findViewById(R.id.cheng);
        ImageButton c = findViewById(R.id.c);
        ImageButton jia = findViewById(R.id.jia);
        ImageButton jian = findViewById(R.id.jian);
        ImageButton genhao = findViewById(R.id.genhao);
        ImageButton deng = findViewById(R.id.deng);
        ImageButton dian = findViewById(R.id.dian);

        a0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //res1为“”时，或res1最高位不为0时
                if (!first){
                    if (res1.equals("")){
                        shuru("0");
                    }else if (!screen1.getText().toString().startsWith("0") ) {
                        shuru("0");
                    }else if (screen1.getText().toString().startsWith("0.") ) {
                        shuru("0");
                    }
                }else{
                    shuru("0");
                }
            }
        });
        a1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("1");
            }
        });
        a2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("2");
            }
        });
        a3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("3");
            }
        });
        a4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("4");
            }
        });
        a5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("5");
            }
        });
        a6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("6");
            }
        });
        a7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("7");
            }
        });
        a8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("8");
            }
        });
        a9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                shuru("9");
            }
        });

        jia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!jiaf){
                    point_existed = false;
                    shuru("+");
                    operator=1;
                    jiaf=true;
                }

            }
        });
        jian.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!jianf){
                    point_existed = false;
                    shuru("-");
                    operator=2;
                    jianf=true;
                }
            }
        });
        cheng.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!chengf){
                    point_existed = false;
                    shuru("×");
                    operator=3;
                    chengf=true;
                }
            }
        });
        chu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!chuf){
                    point_existed = false;
                    shuru("÷");
                    operator=4;
                    chuf=true;
                }
            }
        });
        genhao.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            if (res1.equals("")){
                res1="0";
            }
            d1=Double.parseDouble(screen1.getText().toString());
            d2=d1;

            res2 = "√("+ del_endwithzero(String.valueOf(d2)) +")";
            screen2.setText(res2);
            d1 = java.lang.Math.sqrt(d1);
            res1=String.valueOf(d1);
            screen1.setText(del_endwithzero(res1));
            }
        });
        deng.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            if (!res1.equals("")){

                if (operator==1||operator==2||operator==3||operator==4){
                    d2 = parse_num(res1);
                }
                if (operator==1){
                    if (res2.indexOf("+")!=res2.length()-1){
                        res2=del_endwithzero(res2)+"+"+del_endwithzero(res1)+"=";
                    }else{
                        res2=del_endwithzero(res2)+del_endwithzero(res1)+"=";
                    }
                    d1=d1+d2;
//                    screen2.setText(res2);
//                    success=String.valueOf(d1);
//                    screen1.setText(del_endwithzero(success));
//                    res2=success;
//                    first = true;
//                    deng_shu="";
//                    point_existed = false;
                }
                else if (operator==2){

                    if (res2.indexOf("-")!=res2.length()-1){
                        res2=del_endwithzero(res2)+"-"+del_endwithzero(res1)+"=";
                    }else{
                        res2=del_endwithzero(res2)+del_endwithzero(res1)+"=";
                    }
                    d1=d1-d2;
//                    screen2.setText(res2);
//                    success=String.valueOf(d1);
//                    screen1.setText(del_endwithzero(success));
//                    res2=success;
//                    first = true;
//                    deng_shu="";
//                    point_existed = false;
                }
                else if(operator==3){

                    if (res2.indexOf("×")!=res2.length()-1){
                        res2=del_endwithzero(res2)+"×"+del_endwithzero(res1)+"=";
                    }else{
                        res2=del_endwithzero(res2)+del_endwithzero(res1)+"=";
                    }
                    d1=d1*d2;
//                    screen2.setText(res2);
//                    success=String.valueOf(d1);
//                    screen1.setText(del_endwithzero(success));
//                    res2=success;
//                    first = true;
//                    deng_shu="";
//                    point_existed = false;
                }
                else if(operator==4){

                    if (res2.indexOf("÷")!=res2.length()-1){
                        res2=del_endwithzero(res2)+"÷"+del_endwithzero(res1)+"=";
                    }else{
                        res2=del_endwithzero(res2)+del_endwithzero(res1)+"=";
                    }
                    d1=d1/d2;
//                    screen2.setText(res2);
//                    success=String.valueOf(d1);
//                    screen1.setText(del_endwithzero(success));
//                    res2=success;
//                    first = true;
//                    deng_shu="";
//                    point_existed = false;
                }
                if (operator==1||operator==2||operator==3||operator==4){
                    screen2.setText(res2);
                    success=String.valueOf(d1);
                    screen1.setText(del_endwithzero(success));
                    res2=success;
                    first = true;
                    deng_shu="";
                    point_existed = false;
                    jiaf=false;
                    jianf=false;
                    chengf=false;
                    chuf=false;
                }
            }

            }
        });
        dian.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!point_existed){
                    if (!screen1.getText().toString().contains(".")) {
                        shuru(".");
                        point_existed =true;
                    }
                }
            }
        });
        ce.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!first){
                    res1="";
                    point_existed = false;
                }else{
                    d1=0;
                    res2="0";
                    screen2.setText("");
                }
                screen1.setText("0");
            }
        });
        c.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                d1=0;
                d2=0;
                res1="";
                res2="";
                operator = 0;
                point_existed = false;
                screen1.setText("0");
                screen2.setText("");
                deng_shu="";
                first = false;

                jiaf=false;
                jianf=false;
                chengf=false;
                chuf=false;
            }
        });
    }

    void shuru(String temp){

        if (temp.equals("+") || temp.equals("-") || temp.equals("×") || temp.equals("÷")){
            if (res1.equals("")){
                res1="0";
            }
            if (first){
                String deng_operator = screen1.getText().toString();
                res2 = deng_operator + temp;
                res1="";
                screen2.setText(res2);
                first = false;  //输入运算符，解除first
            }else{
                d1 = parse_num(res1);
                String del = del_endwithzero(String.valueOf(d1));
                res2 = del + temp;
                res1 = "";
                screen2.setText(res2);
            }

        }else{
            if (first){
                //按了等号first之后的第一次按数字的情况，是则清空res1，res2，保留d2
                //不能改res1，但是要清空screen1，再输入到screen1，使screen1内容变为res2
                screen1.setText("");
                screen2.setText("");
                deng_shu = deng_shu + temp;
                screen1.setText(deng_shu);
                res2 = screen1.getText().toString();
                d1 = parse_num(res2);
            }else{
                res1 = res1 + temp;
                String display = res1;
                if (display.startsWith(".")){
                    display = "0" + display;
                }

                screen1.setText(display);
            }

        }
    }

    double parse_num(String temp){
//        int a=(d1+"").length()-(d1+"").indexOf(".")-1;
//        res2=res1;
        double x;
        if (temp.equals("")){
            x = 0;
        }else{
            x=Double.parseDouble(temp);
        }
        return x;
    }

    String del_endwithzero(String tmp){

        String del = tmp;
        if(del.indexOf(".") > 0){
            //正则表达
            //去掉后面无用的零
            del = del.replaceAll("0+?$", "");
            //如小数点后面全是零则去掉小数点
            del = del.replaceAll("[.]$", "");
        }
        if (del.equals("-0")){
            del="0";
        }
        return del;
    }

}
