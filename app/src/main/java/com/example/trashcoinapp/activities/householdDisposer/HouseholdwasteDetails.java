package com.example.trashcoinapp.activities.householdDisposer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.adapters.HouseholdWasteAdapter;
import com.example.trashcoinapp.models.HouseholdWaste;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.util.ArrayList;
        import java.util.List;

public class  HouseholdwasteDetails extends AppCompatActivity {

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private RecyclerView wasteRV;
    private ArrayList<HouseholdWaste> wasteArrayList;
    private HouseholdWasteAdapter householdWasteAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    private PreferenceManager preference;
    String userId;
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_householdwaste_details);

        preference = new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);

        // initializing our variables.
        wasteRV = findViewById(R.id.idRVWaste);
        loadingPB = findViewById(R.id.idProgressBar);
        img_back = findViewById(R.id.img_back);

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        wasteArrayList = new ArrayList<>();
        wasteRV .setHasFixedSize(true);
        wasteRV .setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        HouseholdWasteAdapter HouseholdWasteAdapter = new HouseholdWasteAdapter(wasteArrayList, this);

        // setting adapter to our recycler view.
        wasteRV .setAdapter(HouseholdWasteAdapter);

        // below line is use to get the data from Firebase Firestore.
        // previously we were saving data on a reference of Courses
        // now we will be getting the data from the same reference.
        db.collection("HouseholdWaste").whereEqualTo("userId", userId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                HouseholdWaste c = d.toObject(HouseholdWaste.class);

                                // below is the updated line of code which we have to
                                // add to pass the document id inside our modal class.
                                // we are setting our document id with d.getId() method
                                c.setObjectId(d.getId());

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                wasteArrayList.add(c);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            HouseholdWasteAdapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(HouseholdwasteDetails.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(HouseholdwasteDetails.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(getApplicationContext(), HouseholdwasteUpdate.class));
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

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseholdwasteDetails.this, HouseHoldWasteAddPage.class);
                startActivity(intent);
            }
        });
    }
}