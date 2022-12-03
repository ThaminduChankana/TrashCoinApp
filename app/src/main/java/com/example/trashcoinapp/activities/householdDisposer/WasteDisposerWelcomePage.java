package com.example.trashcoinapp.activities.householdDisposer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WasteDisposerWelcomePage extends AppCompatActivity {
    Button Next;
    ImageView btnbacktodb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_disposer_welcome_page);


        Next=findViewById(R.id.btnNext);
        btnbacktodb=findViewById(R.id.btnbacktodb);

        //navigation
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WasteDisposerWelcomePage.this, WasteDisposerLetGoPage.class);
                startActivity(intent);
            }
        });

        btnbacktodb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(WasteDisposerWelcomePage.this,WasteDisposerDashboard.class);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_disposer);
        bottomNavigationView.setSelectedItemId(R.id.img_waste_in_hand);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_disposer_home:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_view_collectors:
                        startActivity(new Intent(getApplicationContext(), CollectorsForDisposers.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_shopping_cart:
                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_waste_in_hand:
                        return true;
                    case R.id.img_collector_chat:
                        startActivity(new Intent(getApplicationContext(), ChatDisposer.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }

                return false;
            }
        });
    }
    }
