package com.example.mskir.hw6;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    EditText etname,ettel,etmenu1,etmenu2,etmenu3,etaddr;
    Button btnCancel, btnAdd;
    RadioButton radio1,radio2,radio3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etname = (EditText)findViewById(R.id.etname);
        ettel = (EditText)findViewById(R.id.ettel);
        etmenu1 = (EditText)findViewById(R.id.etmenu1);
        etmenu2 = (EditText)findViewById(R.id.etmenu2);
        etmenu3 = (EditText)findViewById(R.id.etmenu3);
        etaddr = (EditText)findViewById(R.id.etaddr);

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        radio1 = (RadioButton)findViewById(R.id.radio1);
        radio2 = (RadioButton)findViewById(R.id.radio2);
        radio3 = (RadioButton)findViewById(R.id.radio3);
    }

    public void onClick(View v){
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdfNow.format(new Date(System.currentTimeMillis()));
        String[] tempString = {etmenu1.getText().toString(),etmenu2.getText().toString(),etmenu3.getText().toString()};

        Intent intent = getIntent();
        Data temp;
        temp = new Data(etname.getText().toString(),ettel.getText().toString(),tempString,etaddr.getText().toString(),time,whichCategory());
        intent.putExtra("data",temp);

        if(v.getId() == R.id.btnCancel){
            setResult(RESULT_CANCELED,intent);
        }else{
            setResult(RESULT_OK,intent);
        }
        finish();
    }

    public String whichCategory(){
        if(radio1.isChecked()){
            return "치킨";
        }else if(radio2.isChecked()){
            return "피자";
        }else{
            return "햄버거";
        }
    }

}
