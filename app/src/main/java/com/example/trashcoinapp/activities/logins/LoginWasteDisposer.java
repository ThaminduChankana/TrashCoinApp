package com.example.trashcoinapp.activities.logins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.WasteCollectorDashboard;
import com.example.trashcoinapp.activities.WasteDisposerDashboard;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginWasteDisposer extends AppCompatActivity {

    private EditText et_waste_disposer_email;
    private EditText pw_waste_disposer_password;
    private Button btn_waste_disposer_login;
    private ProgressBar prg_waste_disposer_login;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_waste_disposer);

        preferenceManager = new PreferenceManager(getApplicationContext());

        et_waste_disposer_email = findViewById(R.id.et_waste_disposer_email);
        pw_waste_disposer_password = findViewById(R.id.pw_waste_disposer_password);
        btn_waste_disposer_login = findViewById(R.id.btn_waste_disposer_login);
        prg_waste_disposer_login = findViewById(R.id.prg_waste_disposer_login);

        btn_waste_disposer_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidSignInDetails()){
                    signIn();
                }
            }
        });


    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            btn_waste_disposer_login.setVisibility(View.INVISIBLE);
            prg_waste_disposer_login.setVisibility(View.VISIBLE);
        }else{
            prg_waste_disposer_login.setVisibility(View.INVISIBLE);
            btn_waste_disposer_login.setVisibility(View.VISIBLE);
        }
    }


    private void signIn(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, et_waste_disposer_email.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, pw_waste_disposer_password.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_FULL_NAME, documentSnapshot.getString(Constants.KEY_FULL_NAME));
                        preferenceManager.putString(Constants.KEY_USER_TYPE,documentSnapshot.getString(Constants.KEY_USER_TYPE));
                        preferenceManager.putString(Constants.KEY_ADDRESS,documentSnapshot.getString(Constants.KEY_ADDRESS));
                        showToast("Login Successful !");
                        Intent intent = new Intent(getApplicationContext(), WasteDisposerDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        loading(false);
                        showToast("Sign In Failed !");
                    }
                });

    }

    private Boolean isValidSignInDetails(){
        if(et_waste_disposer_email.getText().toString().isEmpty()){
            showToast("Enter The Email !");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(et_waste_disposer_email.getText().toString()).matches()){
            showToast("Enter Valid Email !");
            return false;
        }else if(pw_waste_disposer_password.getText().toString().isEmpty()){
            showToast("Enter The Password !");
            return false;
        }else{
            return true;
        }
    }
}