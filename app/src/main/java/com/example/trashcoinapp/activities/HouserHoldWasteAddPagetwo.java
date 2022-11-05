package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.trashcoinapp.R;

public class HouserHoldWasteAddPagetwo extends AppCompatActivity {
    String value1;
    String value2;
    String value3;
    String value4;
    String value5;
    EditText edtm_waste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_houser_hold_waste_add_page_two);


        edtm_waste=findViewById(R.id.edtm_waste);

        Intent intent = getIntent();
        value1=intent.getStringExtra("v1");
        value2=intent.getStringExtra("v2");
        value3=intent.getStringExtra("v3");
        value4=intent.getStringExtra("v4");
        value5=intent.getStringExtra("v5");


        edtm_waste.setText(value1+","+value2+","+value3+","+value4+","+value5);




    }
}