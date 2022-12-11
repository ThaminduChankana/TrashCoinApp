package com.example.trashcoinapp.activities.collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.activities.householdDisposer.WasteDisposerWelcomePage;
import com.example.trashcoinapp.activities.inventory.InventoryActivity;
import com.example.trashcoinapp.adapters.CollectorsAdapter;
import com.example.trashcoinapp.adapters.UsersAdapter;
import com.example.trashcoinapp.databinding.ActivityCollectorsForDisposersBinding;
import com.example.trashcoinapp.databinding.ActivityUsersBinding;
import com.example.trashcoinapp.models.Collectors;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CollectorsForDisposers extends AppCompatActivity {

    private ActivityCollectorsForDisposersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding =  ActivityCollectorsForDisposersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getCollectors();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_collectors_for_disposers);
        bottomNavigationView.setSelectedItemId(R.id.img_view_collectors);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_disposer_home:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_view_collectors:
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

    private void setListeners(){
        binding.imgCollectorsForDisposersBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WasteDisposerDashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getCollectors(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_COLLECTOR_DETAILS)
                .get()
                .addOnCompleteListener(task->{
                    loading(false);
                    if(task.isSuccessful() && task.getResult() != null){
                        List<Collectors> collectors = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            Collectors collector = new Collectors();
                            collector.collectorName = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_NAME);
                            collector.collectorTelephone = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_PHONE);
                            collector.collectorCompany = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_COMPANY);
                            collector.startTime = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_START_TIME);
                            collector.endTime = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_END_TIME);
                            collector.workingArea = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_AREA);
                            collector.availability = queryDocumentSnapshot.getString(Constants.KEY_COLLECTOR_AVAILABILITY);
                            collectors.add(collector);
                        }
                        if(collectors.size()>0){
                            CollectorsAdapter collectorsAdapter = new CollectorsAdapter(collectors);
                            binding.rvCollectorsForDisposersRecyclerView.setAdapter(collectorsAdapter);
                            binding.rvCollectorsForDisposersRecyclerView.setAdapter(collectorsAdapter);
                            binding.rvCollectorsForDisposersRecyclerView.setVisibility(View.VISIBLE);

                        } else {
                            showErrorMessage();
                        }


                    } else {
                        showErrorMessage();
                    }

                });

    }

    private void showErrorMessage(){
        binding.collectorsForDisposersTxtErrorMessage.setText(String.format("%s", "No Users Available"));
        binding.collectorsForDisposersTxtErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.prgCollectorsForDisposers.setVisibility(View.VISIBLE);
        }else{
            binding.prgCollectorsForDisposers.setVisibility(View.INVISIBLE);
        }
    }
}