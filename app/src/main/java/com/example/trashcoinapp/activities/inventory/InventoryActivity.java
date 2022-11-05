package com.example.trashcoinapp.activities.inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.dashboards.WasteCollectorDashboard;
import com.example.trashcoinapp.adapters.InventoryAdapter;
import com.example.trashcoinapp.database.DBHelper;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    private ImageView img_inventory_add;
    private ImageView img_inventory_back;
    RecyclerView inventory_recycler_view;
    DBHelper helper;
    ArrayList<String> item_id, item_name, item_description, pickup_date;
    InventoryAdapter inventoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory);

        inventory_recycler_view = findViewById(R.id.inventory_recycler_view);
        img_inventory_add = findViewById(R.id.img_inventory_add);
        img_inventory_back = findViewById(R.id.img_inventory_back);

        helper = new DBHelper(InventoryActivity.this);
        item_id = new ArrayList<>();
        item_name = new ArrayList<>();
        item_description = new ArrayList<>();
        pickup_date = new ArrayList<>();

        storeDataInArrays();

        inventoryAdapter = new InventoryAdapter(InventoryActivity.this, item_id, item_name, item_description, pickup_date);
        inventory_recycler_view.setAdapter(inventoryAdapter);
        inventory_recycler_view.setLayoutManager(new LinearLayoutManager(InventoryActivity.this));

        img_inventory_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddInventory.class);
                startActivity(intent);
            }
        });

        img_inventory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WasteCollectorDashboard.class);
                startActivity(intent);
            }
        });
    }

    public void storeDataInArrays(){
        Cursor cursor =  helper.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data Available !", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                item_id.add(cursor.getString(0));
                item_name.add(cursor.getString(1));
                item_description.add(cursor.getString(2));
                pickup_date.add(cursor.getString(3));
            }
        }


    }


}