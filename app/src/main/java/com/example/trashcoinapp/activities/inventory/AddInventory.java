package com.example.trashcoinapp.activities.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.dashboards.WasteCollectorDashboard;
import com.example.trashcoinapp.database.DBHelper;

import java.util.Calendar;

public class AddInventory extends AppCompatActivity {

    private Button add_inv_pick_date;
    private EditText et_add_inventory_name;
    private EditText et_add_inventory_description;
    private Button btn_add_inventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_inventory);

        add_inv_pick_date = findViewById(R.id.add_inv_pick_date);
        et_add_inventory_name = findViewById(R.id.et_add_inventory_name);
        et_add_inventory_description = findViewById(R.id.et_add_inventory_description);
        btn_add_inventory = findViewById(R.id.btn_add_inventory);

        ImageView img_add_inventory_back = findViewById(R.id.img_add_inventory_back);

        img_add_inventory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                startActivity(intent);
            }
        });

        add_inv_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddInventory.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                add_inv_pick_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        btn_add_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inventoryName = et_add_inventory_name.getText().toString();
                String inventoryDescription = et_add_inventory_description.getText().toString();
                String inventoryAddDate = add_inv_pick_date.getText().toString();

                if(inventoryName.isEmpty()){
                    et_add_inventory_name.setError("Enter Item Name !");
                }
                else if (inventoryDescription.isEmpty()){
                    et_add_inventory_description.setError("Enter Item Description !");
                }
                else if (inventoryAddDate.equals("Pickup Date")){
                    add_inv_pick_date.setError("Enter Pickup Date !");
                }
                else{
                    DBHelper helper = new DBHelper(AddInventory.this);
                    helper.addInventory(inventoryName,
                            inventoryDescription,
                            inventoryAddDate);

                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);


                }



            }
        });

    }
}