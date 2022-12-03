package com.example.trashcoinapp.activities.householdDisposer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HouseholdWasteTimetable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_household_waste_timetable);

        findViewById(R.id.img_timetable_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    }
