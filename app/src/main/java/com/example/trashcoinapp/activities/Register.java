package com.example.trashcoinapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.trashcoinapp.R;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText et_reg_full_name;
    private EditText et_reg_address;
    private EditText et_reg_email;
    private EditText et_reg_telephone;
    private EditText pw_reg_password;
    private Button btn_register;
    private ProgressBar prg_reg_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        et_reg_full_name = findViewById(R.id.et_reg_full_name);
        et_reg_address = findViewById(R.id.et_reg_address);
        et_reg_email = findViewById(R.id.et_reg_email);
        et_reg_telephone = findViewById(R.id.et_reg_telephone);
        pw_reg_password = findViewById(R.id.pw_reg_password);
        btn_register = findViewById(R.id.btn_register);
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


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}