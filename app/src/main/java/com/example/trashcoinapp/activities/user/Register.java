package com.example.trashcoinapp.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText et_reg_full_name;
    private EditText et_reg_address;
    private EditText et_reg_email;
    private EditText et_reg_telephone;
    private EditText pw_reg_password;
    private Spinner spinner_user_category;
    private Button btn_register;
    private ProgressBar prg_reg_loading;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        preferenceManager = new PreferenceManager(getApplicationContext());

        et_reg_full_name = findViewById(R.id.et_reg_full_name);
        et_reg_address = findViewById(R.id.et_reg_address);
        et_reg_email = findViewById(R.id.et_reg_email);
        et_reg_telephone = findViewById(R.id.et_reg_telephone);
        pw_reg_password = findViewById(R.id.pw_reg_password);
        btn_register = findViewById(R.id.btn_register);
        prg_reg_loading = findViewById(R.id.prg_reg_loading);
        spinner_user_category = findViewById(R.id.spinner_user_category);
        prg_reg_loading = findViewById(R.id.prg_reg_loading);

        final Spinner userCat = (Spinner) findViewById(R.id.spinner_user_category);
        userCat.setOnItemSelectedListener(this);

        List<String> userCategories = new ArrayList<String>();
        userCategories.add("Registering As ...");
        userCategories.add("Waste Disposer");
        userCategories.add("Waste Collector");
        userCategories.add("Waste Recycler");

        ArrayAdapter<String> userCatDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userCategories);
        userCatDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userCat.setAdapter(userCatDataAdapter);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidSignUpDetails()){
                    signUp();
                }
            }
        });


    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signUp(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_FULL_NAME,et_reg_full_name.getText().toString());
        user.put(Constants.KEY_ADDRESS,et_reg_address.getText().toString());
        user.put(Constants.KEY_EMAIL,et_reg_email.getText().toString());
        user.put(Constants.KEY_TELEPHONE,et_reg_telephone.getText().toString());
        user.put(Constants.KEY_PASSWORD,pw_reg_password.getText().toString());
        user.put(Constants.KEY_USER_TYPE,spinner_user_category.getSelectedItem().toString());
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    loading(false);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,false);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_FULL_NAME, et_reg_full_name.getText().toString());
                    preferenceManager.putString(Constants.KEY_USER_TYPE, spinner_user_category.getSelectedItem().toString());
                    showToast("Registration Successful !");
                    Intent intent = new Intent(getApplicationContext(),LoginSelector.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                })
                .addOnFailureListener(exception ->{
                    loading(false);
                    showToast(exception.getMessage());
                });

    }

    private Boolean isValidSignUpDetails(){
        if(et_reg_full_name.getText().toString().trim().isEmpty()){
            showToast("Enter Your Full Name !");
            return false;
        }else if(et_reg_address.getText().toString().trim().isEmpty()){
            showToast("Enter Your Address !");
            return false;
        }else if(et_reg_email.getText().toString().trim().isEmpty()){
            showToast("Enter Your Email !");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(et_reg_email.getText().toString()).matches()){
            showToast("Enter Valid Email !");
            return false;
        }else if(et_reg_telephone.getText().toString().trim().isEmpty()){
            showToast("Enter Your Telephone Number !");
            return false;
        }else if(pw_reg_password.getText().toString().trim().isEmpty()){
            showToast("Enter Your Password !");
            return false;
        }else if(spinner_user_category.getSelectedItem().toString().equals("Registering As ...")){
            TextView errorText = (TextView)spinner_user_category.getSelectedView();
            errorText.setTextColor(Color.RED);
            errorText.setText("Select User Category");
            errorText.setError("Select User Category");
            return false;
        }else{
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            btn_register.setVisibility(View.INVISIBLE);
            prg_reg_loading.setVisibility(View.VISIBLE);
        }else{
            prg_reg_loading.setVisibility(View.INVISIBLE);
            btn_register.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}