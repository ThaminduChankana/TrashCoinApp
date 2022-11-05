package com.example.trashcoinapp.activities.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.trashcoinapp.database.DBHelper;

import java.util.Calendar;

public class EditInventory extends AppCompatActivity {

    private Button edt_inv_pick_date;
    private ImageView img_edit_inventory_back;
    private EditText et_edit_inventory_name, et_edit_inventory_description;
    private Button btn_edit_inventory, btn_inventory_delete;
    private String itemId, itemName, itemDescription, pickUpDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_inventory);

        edt_inv_pick_date = findViewById(R.id.edt_inv_pick_date);
        img_edit_inventory_back = findViewById(R.id.img_edit_inventory_back);
        et_edit_inventory_name = findViewById(R.id.et_edit_inventory_name);
        et_edit_inventory_description = findViewById(R.id.et_edit_inventory_description);
        btn_edit_inventory = findViewById(R.id.btn_edit_inventory);
        btn_inventory_delete = findViewById(R.id.btn_inventory_delete);

        getAndSetIntentData();



        img_edit_inventory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                startActivity(intent);
            }
        });

        edt_inv_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditInventory.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_inv_pick_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        btn_edit_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inventoryName = et_edit_inventory_name.getText().toString();
                String inventoryDescription = et_edit_inventory_description.getText().toString();
                String inventoryAddDate = edt_inv_pick_date.getText().toString();

                if(inventoryName.isEmpty()){
                    et_edit_inventory_name.setError("Enter Item Name !");
                }
                else if (inventoryDescription.isEmpty()){
                    et_edit_inventory_description.setError("Enter Item Description !");
                }
                else if (inventoryAddDate.equals("Pickup Date")){
                    edt_inv_pick_date.setError("Enter Pickup Date !");
                }
                else{
                    DBHelper helper = new DBHelper(EditInventory.this);
                    helper.updateData(itemId,inventoryName, inventoryDescription, inventoryAddDate);
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

        btn_inventory_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    private void getAndSetIntentData(){
        if(getIntent().hasExtra("itemId") && getIntent().hasExtra("itemName") && getIntent().hasExtra("itemDescription") &&
                getIntent().hasExtra("pickUpDate")) {

            itemId = getIntent().getStringExtra("itemId");
            itemName = getIntent().getStringExtra("itemName");
            itemDescription = getIntent().getStringExtra("itemDescription");
            pickUpDate = getIntent().getStringExtra("pickUpDate");

            et_edit_inventory_name.setText(itemName);
            et_edit_inventory_description.setText(itemDescription);
            edt_inv_pick_date.setText(pickUpDate);

        } else{
            Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ itemName + " ?");
        builder.setMessage("Are You Sure You Want To Delete The Inventory Item ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper helper = new DBHelper(EditInventory.this);
                helper.deleteOneRow(itemId);
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
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
}