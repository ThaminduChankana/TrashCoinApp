package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.trashcoinapp.R;

public class WasteDisposerLetGoPage extends AppCompatActivity {
    Button btn_next;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_disposer_let_go_page);

        btn_next=findViewById(R.id.btn_next);

        //navigation
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WasteDisposerLetGoPage.this, HouseHoldWasteAddPage.class);
                startActivity(intent);
            }
        });


    }

}