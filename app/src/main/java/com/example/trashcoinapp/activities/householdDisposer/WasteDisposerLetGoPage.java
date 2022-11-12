package com.example.trashcoinapp.activities.householdDisposer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.trashcoinapp.R;

public class WasteDisposerLetGoPage extends AppCompatActivity {
    Button btn_next;
    ImageView btn_back2;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_disposer_let_go_page);

        btn_next=findViewById(R.id.btn_submit);
        btn_back2=findViewById(R.id.btn_back2);

        //navigation
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WasteDisposerLetGoPage.this, HouseHoldWasteAddPage.class);
                startActivity(intent);
            }
        });

        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(WasteDisposerLetGoPage.this,WasteDisposerWelcomePage.class);
                startActivity(i);
            }
        });


    }

}