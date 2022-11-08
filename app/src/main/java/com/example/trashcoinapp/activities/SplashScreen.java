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
import com.example.trashcoinapp.activities.dashboards.WasteCollectorDashboard;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;

public class SplashScreen extends AppCompatActivity {

    Animation logoAnim, innoAnim,teamAnim, quoteAnim;
    ImageView logo,innovation,team,quote;
    private PreferenceManager preference;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        preference = new PreferenceManager(getApplicationContext());
        if(preference==null){
            role="";
        }
        role = preference.getString(Constants.KEY_USER_TYPE);

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
                try{
                    if(role.equals("Waste Collector")){
                        startActivity(new Intent(SplashScreen.this, WasteCollectorDashboard.class));
                        finish();
                    }else if(role.equals("Waste Disposer")){
                        startActivity(new Intent(SplashScreen.this, WasteDisposerDashboard.class));
                        finish();
                    }else if(role.equals("Waste Recycler")){
                        startActivity(new Intent(SplashScreen.this, WasteRecyclerDashboard.class));
                        finish();
                    }

                }catch(Exception e){

                    startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                    finish();
                }
            }
        },4000);

    }
}