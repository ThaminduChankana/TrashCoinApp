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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.models.HouseholdWaste;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HouseholdwasteUpdate extends AppCompatActivity {

    CheckBox chk_plastic;
    CheckBox chk_paper;
    CheckBox chk_metal;
    CheckBox chk_aluminium;
    CheckBox chk_cardboard;
    private EditText edt_Name,edt_phone,edt_address;
    private Button btn_update;
    private PreferenceManager preference;
    String userId;
    ImageView btn_back5;

    ArrayList<String> selectedWasteList = new ArrayList<String>();


    // creating a variable for firebasefirestore.
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_householdwaste_update);
        HouseholdWaste householdWaste = (HouseholdWaste) getIntent().getSerializableExtra("householdWaste");

        preference = new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);

        // getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        chk_plastic =(CheckBox)findViewById(R.id.chk_plastic);
        chk_paper =(CheckBox)findViewById(R.id.chk_paper);
        chk_metal =(CheckBox)findViewById(R.id.chk_metal);
        chk_aluminium =(CheckBox)findViewById(R.id.chk_aluminium);
        chk_cardboard =(CheckBox)findViewById(R.id.chk_cardboard);
        edt_Name = findViewById(R.id.edt_Name);
        edt_phone= findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        btn_back5=findViewById(R.id.btn_back5);


        // creating variable for button
        btn_update = findViewById(R.id.btn_update);

        btn_back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(HouseholdwasteUpdate.this,HouseholdwasteDetails.class);
                startActivity(i);
            }
        });


        edt_Name.setText(householdWaste.getName());
        edt_phone.setText(householdWaste.getPhone());
        edt_address .setText(householdWaste.getAddress());

        ///Select check boxes for selected items
        String selectedWaste = householdWaste.getHouseWaste();

        if(selectedWaste.contains("Plastic Waste"))
        {
            chk_plastic.setChecked(true);
        }
        if(selectedWaste.contains("Paper Waste"))
        {
            chk_paper.setChecked(true);
        }
        if(selectedWaste.contains("Metal Waste"))
        {
            chk_metal.setChecked(true);
        }
        if(selectedWaste.contains("Aluminium Waste"))
        {
            chk_aluminium.setChecked(true);
        }
        if (selectedWaste.contains("Cardboard Waste"))
        {
            chk_cardboard.setChecked(true);
        }
        //--------------------------------------

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                String name = edt_Name.getText().toString();
                String phone = edt_phone.getText().toString();
                String address = edt_address.getText().toString();

                addSelectedItemsToList();

                String selectedWaste = "";

                for(int i=0;i<selectedWasteList.size();i++)
                {
                    if(i==selectedWasteList.size()-1)
                    {
                        selectedWaste = selectedWaste + selectedWasteList.get(i);
                    }
                    else
                    {
                        selectedWaste = selectedWaste + selectedWasteList.get(i)+", ";
                    }
                }



                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(name )) {
                    edt_Name.setError("Please enter Name");
                } else if (TextUtils.isEmpty( phone)) {
                    edt_phone.setError("Please enter Phone");
                } else if (TextUtils.isEmpty(address)) {
                    edt_address.setError("Please enter Address");
                } else {
                    // calling method to add data to Firebase Firestore.
                    updateHouseholdWaste(name,phone,address,selectedWaste);
                }
            }


            private void updateHouseholdWaste(String name, String phone, String address, String selectedWaste) {
                // inside this method we are passing our updated values
                // inside our object class and later on we
                // will pass our whole object to firebase Firestore.
                HouseholdWaste updateHouseholdWaste = new  HouseholdWaste(householdWaste.getObjectId(),householdWaste.getUserId(),name,phone,address,selectedWaste);

                // after passing data to object class we are
                // sending it to firebase with specific document id.
                // below line is use to get the collection of our Firebase Firestore.
                db.collection("HouseholdWaste").
                        // below line is use toset the id of
                        // document where we have to perform
                        // update operation.
                                document(householdWaste.getObjectId()).

                        // after setting our document id we are
                        // passing our whole object class to it.
                                set(updateHouseholdWaste).

                        // after passing our object class we are
                        // calling a method for on success listener.
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // on successful completion of this process
                                // we are displaying the toast message.
                                Intent i = new Intent(HouseholdwasteUpdate.this,HouseholdwasteDetails.class);
                                startActivity(i);
                                Toast.makeText(HouseholdwasteUpdate.this, "Waste details has been updated..", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    // inside on failure method we are
                    // displaying a failure message.
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HouseholdwasteUpdate.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
                    }
                });
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
                        startActivity(new Intent(getApplicationContext(), ChatDisposer.class));
                        overridePendingTransition(0, 0);
                        finish();
                }

                return false;
            }
        });

    }


    //FUnction use to add selected items to List-------------------------------------
    public void addSelectedItemsToList()
    {

        if( chk_plastic.isChecked())
        {
            selectedWasteList.add("Plastic Waste");

        }

        if(chk_paper.isChecked())
        {
            selectedWasteList.add("Paper Waste");
        }

        if( chk_metal.isChecked())
        {
            selectedWasteList.add("Metal Waste");
        }

        if(chk_aluminium.isChecked())
        {
            selectedWasteList.add("Aluminium Waste");
        }
        if(chk_cardboard.isChecked())
        {
            selectedWasteList.add("Cardboard Waste");
        }




    }
    ///End of function use to ADD items to list----------------------------------------------------

}