package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.databinding.ActivityMessagingBinding;
import com.example.trashcoinapp.databinding.ActivityUsersBinding;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;

public class MessagingActivity extends AppCompatActivity {

    private ActivityMessagingBinding binding;
    private User receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMessagingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
    }

    private void loadReceiverDetails(){
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.txtMessagingName.setText(receiverUser.fullName.split(" ")[0]);
    }

    private void setListeners(){
        binding.imgMessagingBack.setOnClickListener(v -> onBackPressed());
    }

}