package com.example.trashcoinapp.activities.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.trashcoinapp.activities.BaseActivity;
import com.example.trashcoinapp.activities.LoginSelector;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.recyclerProduct.RecyclerProductView;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class WasteRecyclerDashboard extends BaseActivity {


    private PreferenceManager preferenceManager;
    private CardView cv_wd_db_wasteProduct;
    private CardView cv_wr_db_chat;
    private TextView tv_waste_recycler_dashboard;
    Button btn_wr_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_recycler_dashboard);


        btn_wr_logout=findViewById(R.id.btn_wr_logout);
        tv_waste_recycler_dashboard=findViewById(R.id.tv_waste_recycler_dashboard);
        cv_wd_db_wasteProduct=findViewById(R.id.cv_wd_db_wasteProduct);
        cv_wr_db_chat = findViewById(R.id.cv_wr_db_chat);
        preferenceManager = new PreferenceManager(getApplicationContext());

        loadUserDetails();
        getToken();

        btn_wr_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        cv_wd_db_wasteProduct .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecyclerProductView.class);
                startActivity(intent);
            }
        });
//        cv_wr_db_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), .class);
//                startActivity(intent);
//            }
//        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_recycler);
        bottomNavigationView.setSelectedItemId(R.id.img_recycler_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_recycler_home:
                        return true;
                    case R.id.img_view_collectors_of_recycler:
                        startActivity(new Intent(getApplicationContext(), WasteRecyclerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_recycler_info:
                        startActivity(new Intent(getApplicationContext(), WasteRecyclerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_product_management:
                        startActivity(new Intent(getApplicationContext(), RecyclerProductView.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_recycler_chat:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }

    private void loadUserDetails(){
        tv_waste_recycler_dashboard.setText("Hello "+preferenceManager.getString(Constants.KEY_FULL_NAME));
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
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