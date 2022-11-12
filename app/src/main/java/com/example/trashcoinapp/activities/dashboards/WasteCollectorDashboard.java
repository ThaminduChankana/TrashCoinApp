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
import com.example.trashcoinapp.activities.BaseActivity;
import com.example.trashcoinapp.activities.addData.CollectorAddData;
import com.example.trashcoinapp.activities.calculators.CollectorCalculator;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.householdDisposer.WasteDisposerAllView;
import com.example.trashcoinapp.activities.inventory.InventoryActivity;
import com.example.trashcoinapp.activities.user.LoginSelector;
import com.example.trashcoinapp.activities.chat.Chat;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class WasteCollectorDashboard extends BaseActivity {

    private PreferenceManager preferenceManager;
    private TextView tv_waste_collector_dashboard;
    private CardView cv_wc_db_chat;
    private CardView cv_wc_db_inventory;

    private CardView cv_wc_db_disposer;


    private Button btn_logout, btn_add_details, btn_calculator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_collector_dashboard);

        preferenceManager = new PreferenceManager(getApplicationContext());

        tv_waste_collector_dashboard=findViewById(R.id.tv_waste_collector_dashboard);
        cv_wc_db_chat = findViewById(R.id.cv_wc_db_chat);
        cv_wc_db_inventory = findViewById(R.id.cv_wc_db_inventory);
        btn_logout = findViewById(R.id.btn_logout);
        btn_add_details = findViewById(R.id.btn_add_details);

        cv_wc_db_disposer = findViewById(R.id. cv_wc_db_disposer);

        btn_calculator = findViewById(R.id.btn_calculator);

        loadUserDetails();
        getToken();

        btn_add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });

        btn_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CollectorCalculator.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        cv_wc_db_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                startActivity(intent);
            }
        });

        cv_wc_db_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
            }
        });

        cv_wc_db_disposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WasteDisposerAllView.class);
                startActivity(intent);
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_collector);
        bottomNavigationView.setSelectedItemId(R.id.img_collector_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_collector_home:
                        return true;
//                    case R.id.img_collector_disposers:
//                        startActivity(new Intent(getApplicationContext(), CollectorsForDisposers.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//                    case R.id.img_collector_recyclers:
//                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
                    case R.id.img_collector_inventory:
                        startActivity(new Intent(getApplicationContext(), InventoryActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_chat:
                        startActivity(new Intent(getApplicationContext(), Chat.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }

                return false;
            }
        });

    }

    private void loadUserDetails(){
        tv_waste_collector_dashboard.setText("Hello "+preferenceManager.getString(Constants.KEY_FULL_NAME));
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

    private void signOut(){
        showToast("Signing Out ...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );

        HashMap <String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), LoginSelector.class));
                    finish();
                })
                .addOnFailureListener((e -> showToast("Unable To Sign Out !")));
    }
    private void setData(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_COLLECTOR_DETAILS)
                .whereEqualTo(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        showToast("Details Already Exists !");
                        return;
                        //onBackPressed();

                    }else{
                        showToast("Details Do Not Exist !");
                        Intent intent = new Intent(getApplicationContext(), CollectorAddData.class);
                        startActivity(intent);
                    }
                });


    }


}