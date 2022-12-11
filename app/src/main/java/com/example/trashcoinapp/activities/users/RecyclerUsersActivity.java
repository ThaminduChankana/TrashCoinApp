package com.example.trashcoinapp.activities.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.BaseActivity;
import com.example.trashcoinapp.activities.addData.RecyclerAddData;
import com.example.trashcoinapp.activities.chat.ChatRecycler;
import com.example.trashcoinapp.activities.collectors.CollectorsForRecyclers;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.activities.messaging.MessagingActivity;
import com.example.trashcoinapp.activities.messaging.RecyclerMessagingActivity;
import com.example.trashcoinapp.activities.recyclerProduct.RecyclerProductView;
import com.example.trashcoinapp.adapters.UsersAdapter;
import com.example.trashcoinapp.databinding.ActivityRecyclerUsersBinding;
import com.example.trashcoinapp.listeners.UserListener;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerUsersActivity extends BaseActivity implements UserListener {

    private ActivityRecyclerUsersBinding binding;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRecyclerUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_recycler);
        bottomNavigationView.setSelectedItemId(R.id.img_recycler_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_recycler_home:
                        startActivity(new Intent(getApplicationContext(), WasteRecyclerDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.img_view_collectors_of_recycler:
                        startActivity(new Intent(getApplicationContext(), CollectorsForRecyclers.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }

                return false;
            }
        });

    }

    private void setListeners(){
        binding.imgRecyclerUsersBack.setOnClickListener((v -> onBackPressed()));
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
                        List<User> users = new ArrayList<>();
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
                            binding.recyclerUsersRecyclerView.setAdapter(usersAdapter);
                            binding.recyclerUsersRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }


                    } else {
                        showErrorMessage();
                    }

                });

    }

    private void showErrorMessage(){
        binding.recyclerUsersTxtErrorMessage.setText(String.format("%s", "No Users Available"));
        binding.recyclerUsersTxtErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.prgRecyclerUsers.setVisibility(View.VISIBLE);
        }else{
            binding.prgRecyclerUsers.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), RecyclerMessagingActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}