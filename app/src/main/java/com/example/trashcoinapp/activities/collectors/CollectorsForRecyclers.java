package com.example.trashcoinapp.activities.collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.addData.RecyclerAddData;
import com.example.trashcoinapp.activities.chat.ChatRecycler;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.activities.inventory.InventoryActivity;
import com.example.trashcoinapp.activities.recyclerProduct.RecyclerProductView;
import com.example.trashcoinapp.adapters.CollectorsAdapter;
import com.example.trashcoinapp.databinding.ActivityCollectorsForRecyclersBinding;
import com.example.trashcoinapp.databinding.ActivityCollectorsForRecyclersBinding;
import com.example.trashcoinapp.models.Collectors;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CollectorsForRecyclers extends AppCompatActivity {

    private ActivityCollectorsForRecyclersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding =  ActivityCollectorsForRecyclersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getCollectors();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_recycler);
        bottomNavigationView.setSelectedItemId(R.id.img_view_collectors_of_recycler);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_recycler_home:
                        startActivity(new Intent(getApplicationContext(), WasteRecyclerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_view_collectors_of_recycler:
                        return true;
                    case R.id.img_recycler_info:
                        startActivity(new Intent(getApplicationContext(), RecyclerAddData.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_product_management:
                        startActivity(new Intent(getApplicationContext(), RecyclerProductView.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_recycler_chat:
                        startActivity(new Intent(getApplicationContext(), ChatRecycler.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }

    private void setListeners(){
        binding.imgCollectorsForRecyclersBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WasteRecyclerDashboard.class);
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
                            binding.rvCollectorsForRecyclersRecyclerView.setAdapter(collectorsAdapter);
                            binding.rvCollectorsForRecyclersRecyclerView.setAdapter(collectorsAdapter);
                            binding.rvCollectorsForRecyclersRecyclerView.setVisibility(View.VISIBLE);

                        } else {
                            showErrorMessage();
                        }


                    } else {
                        showErrorMessage();
                    }

                });

    }

    private void showErrorMessage(){
        binding.collectorsForRecyclersTxtErrorMessage.setText(String.format("%s", "No Users Available"));
        binding.collectorsForRecyclersTxtErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.prgCollectorsForRecyclers.setVisibility(View.VISIBLE);
        }else{
            binding.prgCollectorsForRecyclers.setVisibility(View.INVISIBLE);
        }
    }
}