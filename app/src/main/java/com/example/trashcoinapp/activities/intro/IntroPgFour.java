package com.example.trashcoinapp.activities.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.trashcoinapp.R;

public class IntroPgFour extends AppCompatActivity {

    Button btn_intro_pg_four_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro_pg_four);

        btn_intro_pg_four_next = findViewById(R.id.btn_intro_pg_four_next);
        btn_intro_pg_four_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IntroPgFive.class);
                startActivity(intent);
                IntroPgFour.this.finish();

            }
        });
    }
}