package com.example.trashcoinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class LoginSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_selector);
    }

    public void openWasteDisposerLogin(View view){
        Intent intent = new Intent(this, LoginWasteDisposer.class);
        startActivity(intent);
    }
    public void openWasteCollectorLogin(View view){
        Intent intent = new Intent(this, LoginWasteCollector.class);
        startActivity(intent);
    }
    public void openWasteRecyclerLogin(View view){
        Intent intent = new Intent(this, LoginWasteRecycler.class);
        startActivity(intent);
    }
}