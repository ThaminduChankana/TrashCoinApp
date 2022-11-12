package com.example.trashcoinapp.activities.householdDisposer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.models.HouseholdWaste;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;

public class HouserHoldWasteAddPagetwo extends AppCompatActivity {


    ArrayList<String> selectedWasteList = new ArrayList<String>();


    private  EditText edtm_waste;
    private  EditText edt_Name,edt_phone,edt_address;
    private  Button btn_submit;
    private  Button btn_view;
    private  ImageView btn_back4;
    String userId;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_houser_hold_waste_add_page_two);

        db = FirebaseFirestore.getInstance();

        PreferenceManager preference = new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);


        edt_Name=findViewById(R.id.edt_Name);
        edt_phone=findViewById(R.id.edt_phone);
        edt_address=findViewById(R.id.edt_address);
        btn_submit=findViewById(R.id.btn_submit);
        btn_view=findViewById(R.id.btn_view);
        edtm_waste=findViewById(R.id.edtm_waste);
        btn_back4=findViewById(R.id.btn_back4);

        Intent intent = getIntent();
        selectedWasteList = (ArrayList<String>) getIntent().getSerializableExtra("selectedWasteList");

        for(int i=0;i<selectedWasteList.size();i++)
        {
            if(i==selectedWasteList.size()-1)
            {
                edtm_waste.setText(edtm_waste.getText() + selectedWasteList.get(i));
            }
            else
            {
                edtm_waste.setText(edtm_waste.getText() + selectedWasteList.get(i)+", ");
            }
        }

        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(HouserHoldWasteAddPagetwo.this,HouseHoldWasteAddPage.class);
                startActivity(i);
            }
        });


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(HouserHoldWasteAddPagetwo.this,HouseholdwasteDetails.class);
                startActivity(i);
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
                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_shopping_cart:
                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_waste_in_hand:
                        startActivity(new Intent(getApplicationContext(), HouseHoldWasteAddPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_collector_chat:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();

                // getting data from edittext fields.
                String name = edt_Name.getText().toString();
                String phone = edt_phone.getText().toString();
                String address = edt_address.getText().toString();
                String housewaste = edtm_waste.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(name )) {
                    edt_Name.setError("Please enter Name");
                } else if (TextUtils.isEmpty( phone)) {
                    edt_phone.setError("Please enter Phone");
                } else if (TextUtils.isEmpty(address)) {
                    edt_address.setError("Please enter Address");
                } else if (TextUtils.isEmpty(housewaste)) {
                    edtm_waste.setError("Please enter Waste");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(userId,id,name,phone,address,housewaste);
                }
            }
        });
    }



    private void addDataToFirestore(String userId,String id,String name, String phone, String address,String housewaste) {

        DocumentReference dbHouseholdWaste = db.collection("HouseholdWaste").document(id);

        // adding our data to our waste object class.
        HouseholdWaste householdWaste = new HouseholdWaste(id,userId,name,phone,address,housewaste);

        dbHouseholdWaste.set(householdWaste);

        Intent i = new Intent(HouserHoldWasteAddPagetwo.this,HouseholdwasteDetails.class);
        startActivity(i);





    }
}




