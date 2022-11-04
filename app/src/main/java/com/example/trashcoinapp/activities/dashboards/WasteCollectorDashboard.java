package com.example.trashcoinapp.activities.dashboards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.BaseActivity;
import com.example.trashcoinapp.activities.LoginSelector;
import com.example.trashcoinapp.activities.chat.Chat;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class WasteCollectorDashboard extends BaseActivity {

    private PreferenceManager preferenceManager;
    private TextView tv_waste_collector_dashboard;
    private CardView cv_wc_db_chat;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waste_collector_dashboard);

        preferenceManager = new PreferenceManager(getApplicationContext());

        tv_waste_collector_dashboard=findViewById(R.id.tv_waste_collector_dashboard);
        cv_wc_db_chat = findViewById(R.id.cv_wc_db_chat);
        btn_logout = findViewById(R.id.btn_logout);
        loadUserDetails();
        getToken();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        cv_wc_db_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
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


}