package com.example.trashcoinapp.activities.householdDisposer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.adapters.DisposerWasteAllViewForCollectorAdapter;
import com.example.trashcoinapp.models.HouseholdWaste;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WasteDisposerAllView extends AppCompatActivity {

        // creating variables for our recycler view,
        // array list, adapter, firebase firestore
        // and our progress bar.
        private RecyclerView wasteRV;
        private ArrayList<HouseholdWaste> wasteArrayList;
        private DisposerWasteAllViewForCollectorAdapter DisposerWasteAllViewForCollectorAdapter;
        private FirebaseFirestore db;
        ProgressBar loadingPB;
        private PreferenceManager preference;
        String userId;



        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_waste_disposer_all_view);

            preference = new PreferenceManager(getApplicationContext());
            userId = preference.getString(Constants.KEY_USER_ID);

            // initializing our variables.
            wasteRV = findViewById(R.id.idRVWaste2);
            loadingPB = findViewById(R.id.idProgressBar2);


            // initializing our variable for firebase
            // firestore and getting its instance.
            db = FirebaseFirestore.getInstance();

            // creating our new array list
            wasteArrayList = new ArrayList<>();
            wasteRV .setHasFixedSize(true);
            wasteRV .setLayoutManager(new LinearLayoutManager(this));

            // adding our array list to our recycler view adapter class.
            DisposerWasteAllViewForCollectorAdapter DisposerWasteAllViewForCollectorAdapter = new DisposerWasteAllViewForCollectorAdapter(wasteArrayList,this);

            // setting adapter to our recycler view.
            wasteRV.setAdapter(DisposerWasteAllViewForCollectorAdapter);

            // below line is use to get the data from Firebase Firestore.
            // previously we were saving data on a reference of Courses
            // now we will be getting the data from the same reference.
            db.collection("HouseholdWaste").get()
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
                                DisposerWasteAllViewForCollectorAdapter.notifyDataSetChanged();
                            } else {
                                // if the snapshot is empty we are displaying a toast message.
                                Toast.makeText(WasteDisposerAllView.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // if we do not get any data or any error we are displaying
                            // a toast message that we do not get any data
                            Toast.makeText(WasteDisposerAllView.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
}