package com.example.trashcoinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    Button btn_intro_decide_yes;
    Button btn_intro_decide_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        setContentView(R.layout.activity_intro);

        btn_intro_decide_yes = findViewById(R.id.btn_intro_decide_yes);
        btn_intro_decide_skip = findViewById(R.id.btn_intro_decide_skip);

        btn_intro_decide_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IntroPgOne.class);
                startActivity(intent);

            }
        });

        btn_intro_decide_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GetStarted.class);
                startActivity(intent);
            }
        });


    }
}