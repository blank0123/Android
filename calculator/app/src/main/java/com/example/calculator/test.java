//package com.example.calculator;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//
//import java.math.BigDecimal;
//
//public class MainActivity extends AppCompatActivity {
//    int t1=0,t2=0;
//    double d1=0,d2=0;
//    boolean f1=false,first=false;   //first第一次加点
//    int t3 = 0;   //判断运算类型
//    boolean excuated = false;   //判断是否进行过运算
//    String s1, s2;
//    private TextView res ;
//    private TextView res1 ;
//    private ImageButton a0 ;
//    private ImageButton a1 ;
//    private ImageButton a2 ;
//    private ImageButton a3 ;
//    private ImageButton a4 ;
//    private ImageButton a5 ;
//    private ImageButton a6 ;
//    private ImageButton a7 ;
//    private ImageButton a8 ;
//    private ImageButton a9 ;
//    private ImageButton ce ;
//    private ImageButton chu ;
//    private ImageButton cheng ;
//    private ImageButton c ;
//    private ImageButton jia ;
//    private ImageButton jian ;
//    private ImageButton genhao ;
//    private ImageButton deng ;
//    private ImageButton dian ;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        res = findViewById(R.id.res);
//        res1 = findViewById(R.id.res1);
//        a0 = findViewById(R.id.a0);
//        a1 = findViewById(R.id.a1);
//        a2 = findViewById(R.id.a2);
//        a3 = findViewById(R.id.a3);
//        a4 = findViewById(R.id.a4);
//        a5 = findViewById(R.id.a5);
//        a6 = findViewById(R.id.a6);
//        a7 = findViewById(R.id.a7);
//        a8 = findViewById(R.id.a8);
//        a9 = findViewById(R.id.a9);
//        ce = findViewById(R.id.ce);
//        chu = findViewById(R.id.chu);
//        cheng = findViewById(R.id.cheng);
//        c = findViewById(R.id.c);
//        jia = findViewById(R.id.jia);
//        jian = findViewById(R.id.jian);
//        genhao = findViewById(R.id.genhao);
//        deng = findViewById(R.id.deng);
//        dian = findViewById(R.id.dian);
//
//        a0.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(0);
//            }
//        });
////        RequestOptions option1 = new RequestOptions()//圆形图片
//////                .override(200,200)
////                .fitCenter()
////                .centerCrop();
////        Glide.with(this)
////                .load(R.drawable.a0)
////                .apply(option1)
////                .into(a0);
//
//        a1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                if (excuated){
////                    t1=0;
////                    t2=0;
////                    d2=0;
////                    s1="";
////                    s2="";
//                    res1.setText("");
//                }
//                shuzi(1);
//
//            }
//        });
//        a2.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(2);
//            }
//        });
//        a3.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(3);
//            }
//        });
//        a4.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(4);
//            }
//        });
//        a5.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(5);
//            }
//        });
//        a6.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(6);
//            }
//        });
//        a7.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(7);
//            }
//        });
//        a8.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(8);
//            }
//        });
//        a9.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                shuzi(9);
//            }
//        });
//
//        jia.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//
//                if (!f1){
//                    t2=t1;
//                    s2 = t2 + "+";
//                    res1.setText(s2);
//                    t1=0;
//                    t3=1;
//                }
//                else{
//                    d2=d1;
//                    s2 = d2 + "+";
//                    res1.setText(s2);
//                    d1=0;
//                    t3=1;
//                }
//            }
//        });
//
//        jian.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//
//                if (!f1){
//                    t2=t1;
//                    s2 = t2 + "-";
//                    res1.setText(s2);
//                    t1=0;
//                    t3=2;
//                }
//                else{
//                    d2=d1;
//                    s2 = d2 + "-";
//                    res1.setText(s2);
//                    d1=0;
//                    t3=2;
//                }
//            }
//        });
//        cheng.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//
//                if (!f1){
//                    t2=t1;
//                    s2 = t2 + "×";
//                    res1.setText(s2);
//                    t1=0;
//                    t3=3;
//                }
//                else{
//                    d2=d1;
//                    s2 = d2 + "×";
//                    res1.setText(s2);
//                    d1=0;
//                    t3=3;
//                }
//            }
//        });
//        chu.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//
//                if (!f1){
//                    t2=t1;
//                    s2 = t2 + "÷";
//                    res1.setText(s2);
//                    t1=0;
//                    t3=4;
//                }
//                else{
//                    d2=d1;
//                    s2 = d2 + "÷";
//                    res1.setText(s2);
//                    d1=0;
//                    t3=4;
//                }
//            }
//        });
//        genhao.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                if (!f1){
//                    t2=t1;
//                    s2 = "√("+ t2 +")";
//                    res1.setText(s2);
//                    d1 = java.lang.Math.sqrt(t1);
//                    res.setText(String.valueOf(d1));
//                    jiadian();
//                }
//                else{
//                    d2=d1;
//                    s2 = "√("+ d2 +")";
//                    res1.setText(s2);
//                    d1 = java.lang.Math.sqrt(d1);
//                    res.setText(String.valueOf(d1));
//                }
//
//            }
//        });
//        deng.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                if (t3==1){
//                    if (!f1){
//                        s2=t2+"+"+t1+"=";
//                        res1.setText(s2);
//                        t2=t2+t1;
//                        res.setText(String.valueOf(t2));
//                        excuated = true;
//                    }
//                    else{
//                        s2=d2+"+"+d1+"=";
//                        res1.setText(s2);
//                        d2=d2+d1;
//                        res.setText(String.valueOf(d2));
//                        excuated = true;
//                    }
//                }else if (t3==2){
//                    if (!f1){
//                        s2=t2+"-"+t1+"=";
//                        res1.setText(s2);
//                        t2=t2-t1;
//                        res.setText(String.valueOf(t2));
//                        excuated = true;
//                    }
//                    else{
//                        s2=d2+"-"+d1+"=";
//                        res1.setText(s2);
//                        d2=d2-d1;
//                        res.setText(String.valueOf(d2));
//                        excuated = true;
//                    }
//                }else if (t3==3){
//                    if (!f1){
//                        s2=t2+"×"+t1+"=";
//                        res1.setText(s2);
//                        t2=t2*t1;
//                        res.setText(String.valueOf(t2));
//                        excuated = true;
//                    }
//                    else{
//                        s2=d2+"×"+d1+"=";
//                        res1.setText(s2);
//                        d2=d2*d1;
//                        res.setText(String.valueOf(d2));
//                        excuated = true;
//                    }
//                }else if (t3==4) {
//                    if (!f1) {
//                        s2 = t2 + "÷" + t1 + "=";
//                        res1.setText(s2);
//                        t2 = t2 / t1;
//                        res.setText(String.valueOf(t2));
//                        excuated = true;;
//                    } else {
//                        s2 = d2 + "÷" + d1 + "=";
//                        res1.setText(s2);
//                        d2 = d2 / d1;
//                        res.setText(String.valueOf(d2));
//                        excuated = true;
//                    }
//                }
//
//            }
//        });
//        dian.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                jiadian();
//            }
//        });
//        ce.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                t1=0;
//                res.setText("0");
//            }
//        });
//        c.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                t1=0;
//                t2=0;
//                d2=0;
//                s1="";
//                s2="";
//                res1.setText("");
//                d1=0;
//                t3=0;
//                f1=false;
//                res.setText("0");
//            }
//        });
//
//    }
//
//    void shuzi(int temp){
//        if (f1==false){
//            if(String.valueOf(t1).length()<9){
//                t1=t1*10+temp;
//            }
//            s1 = String.valueOf(t1);
//            res = findViewById(R.id.res);
//            res.setText(s1);
//        }
//        else if(f1==true){
//            double temp2=0;
//            Log.e("d1", String.valueOf(d1));
//            BigDecimal c = null;
//            if(String.valueOf(d1).length()<9){
//                if (first==true){
//                    first=false;
//                    temp2=temp*0.1;
//                    Log.e("first", String.valueOf(temp2));
//                    d1=d1+temp2;
//
//                    s1 = String.valueOf(d1);
//                    res = findViewById(R.id.res);
//                    res.setText(s1);
//                }
//                else {
//                    int a=(d1+"").length()-(d1+"").indexOf(".")-1;
//                    temp2=temp;
//                    for (int i=0;i<=a;i++){
//                        temp2=temp2*0.1;
//                    }
//                    c = new BigDecimal(d1+temp2).setScale(a+1, BigDecimal.ROUND_HALF_UP);
//                    Log.e("second", String.valueOf(c));
//                    s1 = String.valueOf(c);
//                    res = findViewById(R.id.res);
//                    res.setText(s1);
//                    d1 =Double.parseDouble(res.getText().toString());
//                }
//            }
//
//        }
//
//    }
//
//    void jiadian(){
//        if (f1==false){
//            f1=true;
//            String str = res.getText().toString()+".";
//            res.setText(str);
//            d1=t1;
//            first=true;
//        }
//    }
//
//
//}
//
////TODO 前小数后整数的问题
////TODO 前整数后小数的问题
////TODO 0.3变成0.3000000的问题