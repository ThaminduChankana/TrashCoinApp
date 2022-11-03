package com.example.trashcoinapp.activities.messaging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.trashcoinapp.adapters.ChatAdapter;
import com.example.trashcoinapp.databinding.ActivityMessagingBinding;
import com.example.trashcoinapp.models.ChatMessage;
import com.example.trashcoinapp.models.User;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MessagingActivity extends AppCompatActivity {

    private ActivityMessagingBinding binding;
    private User receiverUser;
    private List <ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private String conversationId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMessagingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
        init();
        listenMessages();
    }

    private void init(){
        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        binding.recyclerMessaging.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();

    }

    private void sendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, binding.messagingInputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        if(conversationId != null) {
            updateConversation(binding.messagingInputMessage.getText().toString());
        } else {
            HashMap<String, Object> conversation = new HashMap<>();
            conversation.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            conversation.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_FULL_NAME));
            conversation.put(Constants.KEY_SENDER_ROLE, preferenceManager.getString(Constants.KEY_USER_TYPE));
            conversation.put(Constants.KEY_RECEIVER_ID,receiverUser.id);
            conversation.put(Constants.KEY_RECEIVER_NAME, receiverUser.fullName);
            conversation.put(Constants.KEY_LAST_MESSAGE, binding.messagingInputMessage.getText().toString());
            conversation.put(Constants.KEY_TIMESTAMP, new Date());
            addConversation(conversation);
        }
        binding.messagingInputMessage.setText(null);
    }

    private void listenMessages(){
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.id)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID,  preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null){
            return;
        }
        if(value != null){
            int count = chatMessages.size();
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(chatMessage);

                }
            }
            Collections.sort(chatMessages,(obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if(count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.recyclerMessaging.smoothScrollToPosition(chatMessages.size()-1);
            }
            binding.recyclerMessaging.setVisibility(View.VISIBLE);
        }
        binding.prgMessaging.setVisibility(View.GONE);
        if(conversationId == null){
            checkFofConversations();
        }
    };

    private void loadReceiverDetails(){
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.txtMessagingName.setText(receiverUser.fullName.split(" ")[0]);
    }

    private void setListeners(){
        binding.imgMessagingBack.setOnClickListener(v -> onBackPressed());
        binding.messagingSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.messagingInputMessage.getText().toString().isEmpty()){
                    Toast.makeText(MessagingActivity.this, "Please input message !", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage();
                }
            }
        });
    }

    private String getReadableDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void addConversation(HashMap<String, Object> conversation){
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .add(conversation)
                .addOnSuccessListener(documentReference -> conversationId = documentReference.getId());
    }

    private void updateConversation(String message){
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_CONVERSATIONS).document(conversationId);
        documentReference.update(
                Constants.KEY_LAST_MESSAGE, message,
                Constants.KEY_TIMESTAMP, new Date()
        );
    }

    private void checkFofConversations(){
        if(chatMessages.size() != 0 ){
            checkForConversationRemotely(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    receiverUser.id
            );
            checkForConversationRemotely(
                    receiverUser.id,
                    preferenceManager.getString(Constants.KEY_USER_ID)
            );
        }
    }

    private void checkForConversationRemotely(String senderId, String receiverId){
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID,senderId)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverId)
                .get()
                .addOnCompleteListener(conversationOnCompleteListener);
    }

    private  final OnCompleteListener<QuerySnapshot> conversationOnCompleteListener =  task -> {
        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversationId = documentSnapshot.getId();
        }
    };

}