package com.example.trashcoinapp.activities.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.users.UsersActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatDisposer extends AppCompatActivity {

    private FloatingActionButton fab_new_chat_disposer;
    private ImageView img_chat_disposer_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_disposer);

        img_chat_disposer_back = findViewById(R.id.img_chat_disposer_back);
        fab_new_chat_disposer = findViewById(R.id.fab_new_chat_disposer);

        img_chat_disposer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab_new_chat_disposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UsersActivity.class));
            }
        });
    }
}