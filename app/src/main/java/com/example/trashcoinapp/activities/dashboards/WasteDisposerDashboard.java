package com.example.trashcoinapp.activities.dashboards;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.householdDisposer.WasteDisposerWelcomePage;
import com.example.trashcoinapp.activities.user.LoginSelector;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import com.example.trashcoinapp.activities.BaseActivity;
import com.google.firebase.messaging.FirebaseMessaging;


public class WasteDisposerDashboard extends BaseActivity {

    private PreferenceManager preferenceManager;
    private CardView cv_wd_db_shop;
    private CardView cv_wd_db_chat;
    private CardView cv_wd_db_collector;
    private CardView cv_wd_db_waste;
    private TextView tv_waste_disposer_dashboard;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_disposer_dashboard);

        logout = findViewById(R.id.btn_logout);
        tv_waste_disposer_dashboard=findViewById(R.id.tv_waste_disposer_dashboard);
        cv_wd_db_shop = findViewById(R.id.cv_wd_db_shop);
        cv_wd_db_chat = findViewById(R.id.cv_wd_db_chat);
        cv_wd_db_chat = findViewById(R.id.cv_wd_db_chat);
        cv_wd_db_collector = findViewById(R.id.cv_wd_db_collector);
        cv_wd_db_waste = findViewById(R.id.cv_wd_db_waste);
        preferenceManager = new PreferenceManager(getApplicationContext());

        loadUserDetails();
        getToken();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        cv_wd_db_shop .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cv_wd_db_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatDisposer.class);
                startActivity(intent);
                finish();
            }
        });

        cv_wd_db_collector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CollectorsForDisposers.class);
                startActivity(intent);
                finish();
            }
        });

        cv_wd_db_waste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WasteDisposerWelcomePage.class);
                startActivity(intent);
                finish();
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_disposer);
        bottomNavigationView.setSelectedItemId(R.id.img_disposer_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_disposer_home:
                        return true;
                    case R.id.img_view_collectors:
                        startActivity(new Intent(getApplicationContext(), CollectorsForDisposers.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_shopping_cart:
                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_waste_in_hand:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerWelcomePage.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_chat:
                        startActivity(new Intent(getApplicationContext(), ChatDisposer.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }

                return false;
            }
        });
    }

    private void loadUserDetails(){
        tv_waste_disposer_dashboard.setText("Hello "+preferenceManager.getString(Constants.KEY_FULL_NAME));
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        preferenceManager.putString(Constants.KEY_FCM_TOKEN,token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
//                .addOnSuccessListener(unused -> showToast("Token Updated Successfully"))
                .addOnFailureListener(e -> showToast("Unable To Update The Token"));
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signOut(){
        showToast("Signing Out ...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );

        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), LoginSelector.class));
                    finish();
                })
                .addOnFailureListener((e -> showToast("Unable To Sign Out !")));
    }
}