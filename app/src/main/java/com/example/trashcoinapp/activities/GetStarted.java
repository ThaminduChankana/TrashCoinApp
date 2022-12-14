package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.user.LoginSelector;
import com.example.trashcoinapp.activities.user.Register;

public class GetStarted extends AppCompatActivity {

    Button btn_get_started_register;
    Button btn_get_started_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_started);

        btn_get_started_register= findViewById(R.id.btn_get_started_register);
        btn_get_started_login= findViewById(R.id.btn_get_started_login);

        btn_get_started_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        btn_get_started_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginSelector.class);
                startActivity(intent);
            }
        });

    }
}