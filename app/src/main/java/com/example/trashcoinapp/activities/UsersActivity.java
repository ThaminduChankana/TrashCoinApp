package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.adapters.UsersAdapter;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private ProgressBar prg_users;
    private PreferenceManager preferenceManager;
    private TextView users_txt_error_message;
    private RecyclerView users_recycler_view;
    private ImageView img_users_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_users);

        prg_users = findViewById(R.id.prg_users);
        users_txt_error_message = findViewById(R.id.users_txt_error_message);
        users_recycler_view = findViewById(R.id.users_recycler_view);
        img_users_back = findViewById(R.id.img_users_back);

        preferenceManager = new PreferenceManager(getApplicationContext());
        getUsers();

        img_users_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Chat.class));
            }
        });

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

                            users.add(user);
                        }
                        if(users.size()>0){
                            UsersAdapter usersAdapter = new UsersAdapter(users);
                            users_recycler_view.setAdapter(usersAdapter);
                            users_recycler_view.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }


                    } else {
                        showErrorMessage();
                    }

                });

    }

    private void showErrorMessage(){
        users_txt_error_message.setText(String.format("%s", "No Users Available"));
        users_txt_error_message.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            prg_users.setVisibility(View.VISIBLE);
        }else{
            prg_users.setVisibility(View.INVISIBLE);
        }
    }

}