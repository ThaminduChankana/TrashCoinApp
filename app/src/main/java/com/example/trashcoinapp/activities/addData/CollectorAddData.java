package com.example.trashcoinapp.activities.addData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.example.trashcoinapp.activities.user.LoginSelector;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CollectorAddData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spn_col_availability;
    private PreferenceManager preferenceManager;
    private TextView tv_add_col_details_full_name, tv_add_col_details_phone,tv_add_col_availability;
    private ImageView img_col_add_data_back;
    private EditText et_add_col_details_working_company, et_add_col_details_working_area;
    private Button btn_add_col_details_start_time, btn_add_col_details_end_time, btn_add_data;
    private ProgressBar prg_col_add_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collector_add_data);

        preferenceManager = new PreferenceManager(getApplicationContext());

        spn_col_availability = findViewById(R.id.spn_col_availability);
        tv_add_col_details_full_name = findViewById(R.id.tv_add_col_details_full_name);
        tv_add_col_details_phone = findViewById(R.id.tv_add_col_details_phone);
        img_col_add_data_back = findViewById(R.id.img_col_add_data_back);
        et_add_col_details_working_company = findViewById(R.id.et_add_col_details_working_company);
        et_add_col_details_working_area = findViewById(R.id.et_add_col_details_working_area);
        btn_add_col_details_start_time = findViewById(R.id.btn_add_col_details_start_time);
        btn_add_col_details_end_time = findViewById(R.id.btn_add_col_details_end_time);
        btn_add_data = findViewById(R.id.btn_add_data);
        prg_col_add_loading = findViewById(R.id.prg_col_add_loading);
        tv_add_col_availability = findViewById(R.id.tv_add_col_availability);

        img_col_add_data_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_add_col_details_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CollectorAddData.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        btn_add_col_details_start_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btn_add_col_details_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CollectorAddData.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        btn_add_col_details_end_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        spn_col_availability.setOnItemSelectedListener(this);

        List<String> availabilityCategories = new ArrayList<String>();
        availabilityCategories.add("Select Available Days..");
        availabilityCategories.add("Weekdays");
        availabilityCategories.add("Weekend");
        availabilityCategories.add("Weekend And Weekdays");

        ArrayAdapter<String> availabilityDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availabilityCategories);
        availabilityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_col_availability.setAdapter(availabilityDataAdapter);

        getToken();
        loadUserDetails();
        btn_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidCollectorDetails()){
                    collectorAddData();
                }
            }
        });

        if(preferenceManager.getBoolean(Constants.KEY_COLLECTOR_DETAILS_ADDED)){
            btn_add_data.setVisibility(View.INVISIBLE);
            spn_col_availability.setVisibility(View.GONE);
            tv_add_col_availability.setVisibility(View.VISIBLE);
            addedDetails();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(CollectorAddData.this);
            builder.setMessage("Once Data Is Added It Can Only Be Changed After Three Months");
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
        tv_add_col_details_full_name.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
        tv_add_col_details_phone.setText(preferenceManager.getString(Constants.KEY_TELEPHONE));

    }

    private void addedDetails(){
        et_add_col_details_working_company.setText(preferenceManager.getString(Constants.KEY_COLLECTOR_COMPANY));
        et_add_col_details_working_area.setText(preferenceManager.getString(Constants.KEY_COLLECTOR_AREA));
        btn_add_col_details_start_time.setText(preferenceManager.getString(Constants.KEY_COLLECTOR_START_TIME));
        btn_add_col_details_end_time.setText(preferenceManager.getString(Constants.KEY_COLLECTOR_END_TIME));
        tv_add_col_availability.setText(preferenceManager.getString(Constants.KEY_COLLECTOR_AVAILABILITY));
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

    private void collectorAddData(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> collector = new HashMap<>();
        collector.put(Constants.KEY_COLLECTOR_NAME,tv_add_col_details_full_name.getText().toString());
        collector.put(Constants.KEY_COLLECTOR_PHONE,tv_add_col_details_phone.getText().toString());
        collector.put(Constants.KEY_COLLECTOR_COMPANY,et_add_col_details_working_company.getText().toString());
        collector.put(Constants.KEY_COLLECTOR_START_TIME,btn_add_col_details_start_time.getText().toString());
        collector.put(Constants.KEY_COLLECTOR_END_TIME,btn_add_col_details_end_time.getText().toString());
        collector.put(Constants.KEY_COLLECTOR_AREA,et_add_col_details_working_area.getText().toString());
        collector.put(Constants.KEY_COLLECTOR_AVAILABILITY,spn_col_availability.getSelectedItem().toString());
        database.collection(Constants.KEY_COLLECTION_COLLECTOR_DETAILS)
                .add(collector)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_COLLECTOR_DETAILS_ADDED,true);
                    preferenceManager.putString(Constants.KEY_COLLECTOR_NAME,tv_add_col_details_full_name.getText().toString());
                    preferenceManager.putString(Constants.KEY_COLLECTOR_PHONE,tv_add_col_details_phone.getText().toString());
                    preferenceManager.putString(Constants.KEY_COLLECTOR_COMPANY,et_add_col_details_working_company.getText().toString());
                    preferenceManager.putString(Constants.KEY_COLLECTOR_START_TIME,btn_add_col_details_start_time.getText().toString());
                    preferenceManager.putString(Constants.KEY_COLLECTOR_END_TIME,btn_add_col_details_end_time.getText().toString());
                    preferenceManager.putString(Constants.KEY_COLLECTOR_AREA,et_add_col_details_working_area.getText().toString());
                    preferenceManager.putString(Constants.KEY_COLLECTOR_AVAILABILITY, spn_col_availability.getSelectedItem().toString());
                    loading(false);
                    showToast("Collector Details Adding Successful !");
                })
                .addOnFailureListener(exception ->{
                    loading(false);
                    showToast(exception.getMessage());
                });

    }

    private Boolean isValidCollectorDetails(){
        if(et_add_col_details_working_company.getText().toString().trim().isEmpty()){
            showToast("Enter Working Company Name !");
            return false;

        }else if(btn_add_col_details_start_time.getText().toString().equals("Start Time")){
            showToast("Enter Work Start Time !");
            return false;
        }else if(btn_add_col_details_end_time.getText().toString().equals("End Time")){
            showToast("Enter Work End Time !");
            return false;
        }else if(et_add_col_details_working_area.getText().toString().trim().isEmpty()){
            showToast("Enter Working Area !");
            return false;
        }else if(spn_col_availability.getSelectedItem().toString().equals("Select Available Days..")){
            TextView errorText = (TextView)spn_col_availability.getSelectedView();
            errorText.setTextColor(Color.RED);
            errorText.setText("Select Available Days..");
            errorText.setError("Select Available Days..");
            return false;
        }else{
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            btn_add_data.setVisibility(View.INVISIBLE);
            prg_col_add_loading.setVisibility(View.VISIBLE);
        }else{
            prg_col_add_loading.setVisibility(View.INVISIBLE);
            btn_add_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}