package com.example.trashcoinapp.activities.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.adapters.ProductViewAdapter;
import com.example.trashcoinapp.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductViewActivity extends AppCompatActivity {

    ImageView img_shopping_cart, img_order;
    static Context context;
    private RecyclerView productRV;
    private ArrayList<Product> productsArrayList;
    private ProductViewAdapter productViewAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_view);


        db = FirebaseFirestore.getInstance();
        context = getApplicationContext();
        productRV = findViewById(R.id.recycledProducts);
        loadingPB = findViewById(R.id.idProgressBar);
        img_shopping_cart = findViewById(R.id.img_shopping_cart);
        img_order = findViewById(R.id.img_order);

        productsArrayList = new ArrayList<>();
        productRV .setHasFixedSize(true);
        productRV .setLayoutManager(new LinearLayoutManager(this));
        productViewAdapter = new ProductViewAdapter(productsArrayList, this);
        productRV.setAdapter(productViewAdapter);

        // navigation to cart
        img_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        // navigation to orders
        img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductViewActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });


        // bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_disposer);
        bottomNavigationView.setSelectedItemId(R.id.img_shopping_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.img_disposer_home:
                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_view_collectors:
                        startActivity(new Intent(getApplicationContext(), CollectorsForDisposers.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.img_shopping_cart:
                        return true;
//                    case R.id.img_waste_in_hand:
//                        startActivity(new Intent(getApplicationContext(), WasteDisposerDashboard.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
                    case R.id.img_collector_chat:
                        startActivity(new Intent(getApplicationContext(), ChatDisposer.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }

                return false;
            }
        });


        // get the products details
        db.collection("Products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Product p = d.toObject(Product.class);
                                productsArrayList.add(p);
                            }
                            productViewAdapter.notifyDataSetChanged();
                        } else {
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(ProductViewActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductViewActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public  static Context getContext(){
        return context;
    }
}