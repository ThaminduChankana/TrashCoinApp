package com.example.trashcoinapp.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.logins.LoginWasteCollector;
import com.example.trashcoinapp.activities.logins.LoginWasteDisposer;
import com.example.trashcoinapp.activities.logins.LoginWasteRecycler;

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