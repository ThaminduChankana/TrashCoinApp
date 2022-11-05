package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.trashcoinapp.R;

public class HouseHoldWasteAddPage extends AppCompatActivity {
    CheckBox chk_plastic;
    CheckBox chk_paper;
    CheckBox chk_metal;
    CheckBox chk_aluminium;
    CheckBox chk_cardboard;
    Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_house_hold_waste_add_page);

        btn_next=findViewById(R.id.btn_next);
        chk_plastic=findViewById(R.id.chk_plastic);
        chk_paper=findViewById(R.id.chk_paper);
        chk_metal=findViewById(R.id.chk_metal);
        chk_aluminium=findViewById(R.id.chk_aluminium);
        chk_cardboard=findViewById(R.id.chk_cardboard);


        //navigation

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( HouseHoldWasteAddPage.this,HouserHoldWasteAddPagetwo.class);

                String val1=chk_plastic.getText().toString();
                String val2=chk_paper.getText().toString();
                String val3=chk_metal.getText().toString();
                String val4=chk_aluminium.getText().toString();
                String val5=chk_cardboard.getText().toString();

                System.out.println(val1);

                intent.putExtra("v1",val1);
                intent.putExtra("v2",val2);
                intent.putExtra("v3",val3);
                intent.putExtra("v4",val4);
                intent.putExtra("v5",val5);

                startActivity(intent);
            }
        });
    }
}