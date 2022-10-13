package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.trashcoinapp.R;

public class SplashScreen extends AppCompatActivity {

    Animation logoAnim, innoAnim,teamAnim, quoteAnim;
    ImageView logo,innovation,team,quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        logoAnim = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        quoteAnim = AnimationUtils.loadAnimation(this,R.anim.quote_animation);
        innoAnim = AnimationUtils.loadAnimation(this,R.anim.innovation_animation);
        teamAnim = AnimationUtils.loadAnimation(this,R.anim.team_animation);

        logo = findViewById(R.id.img_logo_name);
        quote = findViewById(R.id.img_quote);
        innovation = findViewById(R.id.img_innovation);
        team = findViewById(R.id.img_team_name);

        logo.setAnimation(logoAnim);
        quote.setAnimation(quoteAnim);
        innovation.setAnimation(innoAnim);
        team.setAnimation(teamAnim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                finish();
            }
        },4000);

    }
}