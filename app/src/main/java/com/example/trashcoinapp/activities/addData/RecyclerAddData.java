package com.example.trashcoinapp.activities.addData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.activities.recyclerProduct.RecyclerProductView;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RecyclerAddData extends AppCompatActivity {


    private PreferenceManager preferenceManager;
    private TextView tv_add_rec_details_full_name, tv_add_rec_details_phone;
    private ImageView img_rec_add_data_back;
    private EditText et_add_rec_details_service_company;
    private Button  btn_rec_add_data;
    private ProgressBar prg_rec_add_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_add_data);


        preferenceManager = new PreferenceManager(getApplicationContext());

        tv_add_rec_details_full_name = findViewById(R.id.tv_add_rec_details_full_name);
        tv_add_rec_details_phone = findViewById(R.id.tv_add_rec_details_phone);
        img_rec_add_data_back = findViewById(R.id.img_rec_add_data_back);
        et_add_rec_details_service_company = findViewById(R.id.et_add_rec_details_service_company);

        btn_rec_add_data = findViewById(R.id.btn_rec_add_data);
        prg_rec_add_loading = findViewById(R.id.prg_rec_add_loading);


        img_rec_add_data_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WasteRecyclerDashboard.class);
                startActivity(intent);
            }
        });


        getToken();
        //setData();
        loadUserDetails();



        btn_rec_add_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValidCollectorDetails()){
                            recyclerAddData();
                        }
                    }
                });

                if(preferenceManager.getBoolean(Constants.KEY_COLLECTOR_DETAILS_ADDED)){
                    btn_rec_add_data.setVisibility(View.INVISIBLE);

                    addedDetails();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(com.example.trashcoinapp.activities.addData.RecyclerAddData.this);
                    builder.setMessage("You Can Requested Material Only for a month");
                    builder.setTitle("Alert ! Be Aware When Adding Your Data");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {});

                    builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }

            private void loadUserDetails(){
                tv_add_rec_details_full_name.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
                tv_add_rec_details_phone.setText(preferenceManager.getString(Constants.KEY_TELEPHONE));

            }

            private void addedDetails(){
                et_add_rec_details_service_company.setText(preferenceManager.getString(Constants.KEY_COLLECTOR_COMPANY));

            }

            private void getToken(){
                FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
            }

            private void showToast(String message){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

            private void recyclerAddData(){
                loading(true);
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                HashMap<String, Object> recycler = new HashMap<>();
                recycler.put(Constants.KEY_USER_ID,preferenceManager.getString(Constants.KEY_USER_ID));
                recycler.put(Constants.KEY_RECYCLER_NAME,tv_add_rec_details_full_name.getText().toString());
                recycler.put(Constants.KEY_RECYCLER_PHONE,tv_add_rec_details_phone.getText().toString());
                recycler.put(Constants.KEY_RECYCLER_SERVICE,et_add_rec_details_service_company.getText().toString());


                database.collection(Constants.KEY_COLLECTION_RECYCLER_DETAILS)
                        .add(recycler)
                        .addOnSuccessListener(documentReference -> {
                            preferenceManager.putBoolean(Constants.KEY_RECYCLER_DETAILS_ADDED,true);
                            preferenceManager.putString(Constants.KEY_RECYCLER_NAME,tv_add_rec_details_full_name.getText().toString());
                            preferenceManager.putString(Constants.KEY_RECYCLER_PHONE,tv_add_rec_details_phone.getText().toString());
                            preferenceManager.putString(Constants.KEY_RECYCLER_SERVICE,et_add_rec_details_service_company.getText().toString());


                            loading(false);
                            showToast("Recycler Details Adding Successful !");
                        })
                        .addOnFailureListener(exception ->{
                            loading(false);
                            showToast(exception.getMessage());
                        });

            }



            private Boolean isValidCollectorDetails(){
                if(et_add_rec_details_service_company.getText().toString().trim().isEmpty()){
                    showToast("Enter Service offer !");
                    return false;


                }else{
                    return true;
                }
            }

            private void loading(Boolean isLoading){
                if(isLoading){
                    btn_rec_add_data.setVisibility(View.INVISIBLE);
                    prg_rec_add_loading.setVisibility(View.VISIBLE);
                }else{
                    prg_rec_add_loading.setVisibility(View.INVISIBLE);
                    btn_rec_add_data.setVisibility(View.VISIBLE);
                }
            }


        }


