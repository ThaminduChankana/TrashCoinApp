package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.intro.IntroPgOne;

public class IntroActivity extends AppCompatActivity {

    Button btn_intro_decide_yes;
    Button btn_intro_decide_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        btn_intro_decide_yes = findViewById(R.id.btn_intro_decide_yes);
        btn_intro_decide_skip = findViewById(R.id.btn_intro_decide_skip);

        btn_intro_decide_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntroPgOne.class);
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