package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.trashcoinapp.R;

public class WasteDisposerWelcomePage extends AppCompatActivity {
    Button Next;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_disposer_welcome_page);


        Next=findViewById(R.id.btnNext);

        //navigation
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WasteDisposerWelcomePage.this, WasteDisposerLetGoPage.class);
                startActivity(intent);
            }
        });
    }
    }
