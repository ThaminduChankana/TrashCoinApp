package com.example.trashcoinapp.activities.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.BaseActivity;
import com.example.trashcoinapp.activities.dashboards.WasteCollectorDashboard;
import com.example.trashcoinapp.activities.householdDisposer.WasteDisposerAllView;
import com.example.trashcoinapp.activities.inventory.InventoryActivity;
import com.example.trashcoinapp.activities.messaging.MessagingActivity;
import com.example.trashcoinapp.activities.recyclers.RecyclersListForCollectors;
import com.example.trashcoinapp.adapters.UsersAdapter;
import com.example.trashcoinapp.databinding.ActivityUsersBinding;
import com.example.trashcoinapp.listeners.UserListener;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends BaseActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_users);
        bottomNavigationView.setSelectedItemId(R.id.img_collector_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_collector_home:
                        startActivity(new Intent(getApplicationContext(), WasteCollectorDashboard.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_disposers:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerAllView.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_recyclers:
                        startActivity(new Intent(getApplicationContext(), RecyclersListForCollectors.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_inventory:
                        startActivity(new Intent(getApplicationContext(), InventoryActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_collector_chat:
                        return true;
                }

                return false;
            }
        });

    }

    private void setListeners(){
        binding.imgUsersBack.setOnClickListener((v -> onBackPressed()));
    }

    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task->{
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);

                    if(task.isSuccessful() && task.getResult() != null){
                        List <User> users = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.fullName = queryDocumentSnapshot.getString(Constants.KEY_FULL_NAME);
                            user.category = queryDocumentSnapshot.getString(Constants.KEY_USER_TYPE);
                            user.address = queryDocumentSnapshot.getString(Constants.KEY_ADDRESS);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if(users.size()>0){
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }


                    } else {
                        showErrorMessage();
                    }

                });

    }

    private void showErrorMessage(){
        binding.usersTxtErrorMessage.setText(String.format("%s", "No Users Available"));
        binding.usersTxtErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.prgUsers.setVisibility(View.VISIBLE);
        }else{
            binding.prgUsers.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}