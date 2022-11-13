package com.example.trashcoinapp.activities.addData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.chat.Chat;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteCollectorDashboard;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.activities.inventory.InventoryActivity;
import com.example.trashcoinapp.adapters.CollectorsAdapter;
import com.example.trashcoinapp.adapters.RecyclersAdapter;
import com.example.trashcoinapp.databinding.ActivityCollectorsForDisposersBinding;
import com.example.trashcoinapp.databinding.ActivityRecyclersListForCollectorsBinding;
import com.example.trashcoinapp.models.Collectors;
import com.example.trashcoinapp.models.Recyclers;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclersListForCollectors extends AppCompatActivity {


    private ActivityRecyclersListForCollectorsBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRecyclersListForCollectorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getRecyclers();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_collector);
        bottomNavigationView.setSelectedItemId(R.id.img_collector_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_collector_home:
                        startActivity(new Intent(getApplicationContext(), WasteCollectorDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_collector_disposers:
                        startActivity(new Intent(getApplicationContext(), CollectorsForDisposers.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_recyclers:
                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
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

    private void setListeners() {
        binding.imgRecyclerForCollectorBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WasteCollectorDashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getRecyclers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_RECYCLER_DETAILS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Recyclers> recyclers = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            Recyclers recycler = new Recyclers();
                            recycler.recyclerName = queryDocumentSnapshot.getString(Constants.KEY_RECYCLER_NAME);
                            recycler.recyclerTelephone = queryDocumentSnapshot.getString(Constants.KEY_RECYCLER_PHONE);
                            recycler.recyclerService = queryDocumentSnapshot.getString(Constants.KEY_RECYCLER_SERVICE);

                            recyclers.add(recycler);
                        }
                        if (recyclers.size() > 0) {
                            RecyclersAdapter recyclersAdapter = new RecyclersAdapter(recyclers);
                            binding.rvRecyclerForCollectorRecyclerView.setAdapter(recyclersAdapter);
                            binding.rvRecyclerForCollectorRecyclerView.setAdapter(recyclersAdapter);
                            binding.rvRecyclerForCollectorRecyclerView.setVisibility(View.VISIBLE);

                        } else {
                            showErrorMessage();
                        }


                    } else {
                        showErrorMessage();
                    }

                });

    }

    private void showErrorMessage() {
        binding.recyclerForCollectorTxtErrorMessage.setText(String.format("%s", "No Users Available"));
        binding.recyclerForCollectorTxtErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.prgRecyclersForCollectors.setVisibility(View.VISIBLE);
        } else {
            binding.prgRecyclersForCollectors.setVisibility(View.INVISIBLE);
        }
    }
}