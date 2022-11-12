package com.example.trashcoinapp.activities.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.addData.RecyclerAddData;
import com.example.trashcoinapp.activities.collectors.CollectorsForRecyclers;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.activities.messaging.MessagingActivity;
import com.example.trashcoinapp.activities.recyclerProduct.RecyclerProductView;
import com.example.trashcoinapp.activities.users.RecyclerUsersActivity;
import com.example.trashcoinapp.activities.users.UsersActivity;
import com.example.trashcoinapp.adapters.RecentConversationsAdapter;
import com.example.trashcoinapp.databinding.ActivityChatBinding;
import com.example.trashcoinapp.databinding.ActivityChatRecyclerBinding;
import com.example.trashcoinapp.listeners.ConversationListener;
import com.example.trashcoinapp.models.ChatMessage;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRecycler extends AppCompatActivity implements ConversationListener {

    private ActivityChatRecyclerBinding binding;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityChatRecyclerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        init();
        getToken();
        setListeners();
        listenConversations();

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


    private void init() {
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations, this);
        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
        database = FirebaseFirestore.getInstance();


    }

    private void listenConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) ->{
        if(error != null){
            return;
        }
        if(value != null){
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if(preferenceManager.getString(Constants.KEY_USER_ID).equals(senderId)){
                        chatMessage.conversationName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversationId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        chatMessage.UserRole = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ROLE);
                    } else {
                        chatMessage.conversationName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversationId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        chatMessage.UserRole = documentChange.getDocument().getString(Constants.KEY_SENDER_ROLE);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                } else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for(int i = 0 ; i<conversations.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);

                        if(conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId)){
                            conversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            binding.conversationsRecyclerView.smoothScrollToPosition(0);
            binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
            binding.conversationsPrg.setVisibility(View.GONE);
        }
    };

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        preferenceManager.putString(Constants.KEY_FCM_TOKEN,token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
//                .addOnSuccessListener(unused -> showToast("Token Updated Successfully"))
                .addOnFailureListener(e -> showToast("Unable To Update The Token"));
    }

    private void setListeners(){
        binding.imgChatRecyclerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.fabNewChatRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecyclerUsersActivity.class));
            }
        });

    }




    @Override
    public void onConversationClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}