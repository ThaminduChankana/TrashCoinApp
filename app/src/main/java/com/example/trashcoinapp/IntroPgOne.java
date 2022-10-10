package com.example.trashcoinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class IntroPgOne extends AppCompatActivity {

    Button btn_intro_pg_one_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro_pg_one);

        btn_intro_pg_one_next = findViewById(R.id.btn_intro_pg_one_next);
        btn_intro_pg_one_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IntroPgTwo.class);
                startActivity(intent);
                IntroPgOne.this.finish();

            }
        });



    }
}