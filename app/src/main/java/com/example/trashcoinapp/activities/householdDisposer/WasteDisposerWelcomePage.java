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
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;

public class WasteDisposerWelcomePage extends AppCompatActivity {
    Button Next;
    ImageView btnback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_disposer_welcome_page);


        Next=findViewById(R.id.btnNext);
        btnback=findViewById(R.id.btnback);

        //navigation
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WasteDisposerWelcomePage.this, WasteDisposerLetGoPage.class);
                startActivity(intent);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(WasteDisposerWelcomePage.this,WasteDisposerDashboard.class);
                startActivity(i);
            }
        });
    }
    }
