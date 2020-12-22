package com.example.housing_loan;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    double goufang_num = 0;
    double anjie_num = 0;
    double loan_sum;
    int date = 12;
    double rate = 0.7;
    boolean interest = false;
    double shangdai = 0;
    double gongji = 0;
    double peryue = 0;
    double interest_sum = 0;
    double repayment_sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        EditText goufang_edit = findViewById(R.id.goufang_edit);
        EditText anjie_edit = findViewById(R.id.anjie_edit);
        TextView loan_sum_text = findViewById(R.id.loan_sum_text);
        Button loan_sum_button = findViewById(R.id.loan_sum_button);
        Button compute = findViewById(R.id.compute);
        Spinner loan_spinner = findViewById(R.id.loan_spinner);
        Spinner rate_spinner = findViewById(R.id.rate_spinner);
        RadioGroup repayment = findViewById(R.id.repayment);
        CheckBox shangdai_check = findViewById(R.id.shangdai);
        CheckBox gongji_check = findViewById(R.id.gongji);
        EditText shangdai_edit = findViewById(R.id.shangdai_edit);
        EditText gongji_edit = findViewById(R.id.gongji_edit);
        TextView t1 = findViewById(R.id.t1);
        TextView t2 = findViewById(R.id.t2);
        TextView t3 = findViewById(R.id.t3);
        TextView t4 = findViewById(R.id.t4);
        TextView t5 = findViewById(R.id.t5);


        loan_sum_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!goufang_edit.getText().toString().equals("")){
                    goufang_num = Double.parseDouble(String.valueOf(goufang_edit.getText().toString()));

                } else{
                    goufang_num = 0;
                }
                if (!anjie_edit.getText().toString().equals("")){
                    anjie_num = Double.parseDouble(String.valueOf(anjie_edit.getText().toString()));
                }else{
//                    if (!goufang_edit.getText().toString().equals("")){
                        anjie_num = 100;
//                    }else{
//                        anjie_num = 0;
//                    }
                }
                loan_sum = goufang_num * anjie_num * 0.01;
                loan_sum_text.setText("您的贷款总额为"+loan_sum+"万元");
            }
        });

        loan_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    date = 12;
                }else if (position == 1){
                    date = 24;
                }else if (position == 2){
                    date = 36;
                }else if (position == 3){
                    date = 48;
                }else if (position == 4){
                    date = 60;
                }else if (position == 5){
                    date = 72;
                }else if (position == 6){
                    date = 84;
                }else if (position == 7){
                    date = 96;
                }else if (position == 8){
                    date = 108;
                }else if (position == 9){
                    date = 120;
                }else if (position == 10){
                    date = 132;
                }else if (position == 11){
                    date = 144;
                }else if (position == 12){
                    date = 156;
                }else if (position == 13){
                    date = 168;
                }else if (position == 14){
                    date = 180;
                }else if (position == 15){
                    date = 192;
                }else if (position == 16){
                    date = 204;
                }else if (position == 17){
                    date = 216;
                }else if (position == 18){
                    date = 228;
                }else if (position == 19){
                    date = 240;
                }else if (position == 20){
                    date = 300;
                }else if (position == 21){
                    date = 360;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rate_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    rate = 0.7;
                }else if (position == 1){
                    rate = 0.85;
                }else if (position == 2){
                    rate = 1.1;
                }else if (position == 3){
                    rate = 1.2;
                }else if (position == 4){
                    rate = 1.3;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        RadioGroup.OnCheckedChangeListener radioGrouplisten = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio2:
                        interest = false;
                        break;
                    case R.id.radio3:
//                        interest = true;
//                         先false测试
                        interest = false;
                        break;
                    default:
                        break;
                }
            }
        };
        repayment.setOnCheckedChangeListener(radioGrouplisten);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!interest){
                    if (shangdai_check.isChecked()){
                        shangdai = Double.parseDouble(shangdai_edit.getText().toString());

                    }
                    if (gongji_check.isChecked()){
                        gongji = Double.parseDouble(gongji_edit.getText().toString());
                    }
                }
                repayment_sum   = loan_sum * rate   *   Math.pow(1+rate,date);
                interest_sum    = repayment_sum     -   loan_sum;
                peryue          = repayment_sum     /   (Math.pow(1+rate,date)-1);
                t1.setText("您的贷款总额为"+Math.round(loan_sum)+"万元");
                t2.setText("            还总额为"+Math.round(repayment_sum)+"万元");
                t3.setText("其中利息总额为"+Math.round(interest_sum)+"万元");
                t4.setText("    还款总时间为"+date+"月");
                t5.setText("每月还款金额为"+Math.round(peryue)+"元");
            }
        });

    }

}

