package com.example.trashcoinapp.activities.householdDisposer;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;

import java.util.ArrayList;

public class HouseHoldWasteAddPage extends AppCompatActivity {

    CheckBox chk_plastic;
    CheckBox chk_paper;
    CheckBox chk_metal;
    CheckBox chk_aluminium;
    CheckBox chk_cardboard;
    Button btn_next;
    Button btn_timetable;
    Button btn_moredetails;
    ImageView btn_back;


    ArrayList<String> selectedWasteList = new ArrayList<String>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_house_hold_waste_add_page);

        btn_moredetails = findViewById(R.id.btn_moredetails);
        btn_timetable = findViewById(R.id.btn_timetable);
        btn_next = findViewById(R.id.btn_next);
        chk_plastic =findViewById(R.id.chk_plastic);
        chk_paper =findViewById(R.id.chk_paper);
        chk_metal =findViewById(R.id.chk_metal);
        chk_aluminium =findViewById(R.id.chk_aluminium);
        chk_cardboard =findViewById(R.id.chk_cardboard);
        btn_back=findViewById(R.id.btn_back);


        btn_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(HouseHoldWasteAddPage.this,HouseholdWasteTimetable.class);
                startActivity(i);
            }
        });

        btn_moredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(HouseHoldWasteAddPage.this,WasteManagementMoredetails.class);
                startActivity(i);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // opening a new activity on button click
               Intent i = new Intent(HouseHoldWasteAddPage.this,WasteDisposerLetGoPage.class);
                startActivity(i);
            }
        });



        //navigation
       btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chk_plastic.isChecked() || chk_paper.isChecked() || chk_metal.isChecked() || chk_aluminium.isChecked() || chk_cardboard.isChecked() )
                {
                    Intent intent = new Intent(HouseHoldWasteAddPage.this, HouserHoldWasteAddPagetwo.class);
                    addSelectedItemsToList();
                    intent.putExtra("selectedWasteList", selectedWasteList);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Please Add Details!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //FUnction use to add selected items to List-------------------------------------
    public void addSelectedItemsToList()
    {

        if( chk_plastic.isChecked())
        {
            selectedWasteList.add("Plastic Waste");

        }

        if(chk_paper.isChecked())
        {
            selectedWasteList.add("Paper Waste");
        }

        if( chk_metal.isChecked())
        {
            selectedWasteList.add("Metal Waste");
        }

        if(chk_aluminium.isChecked())
        {
            selectedWasteList.add("Aluminium Waste");
        }
        if(chk_cardboard.isChecked())
        {
            selectedWasteList.add("Cardboard Waste");
        }




    }
    ///End of function use to ADD items to list----------------------------------------------------

}