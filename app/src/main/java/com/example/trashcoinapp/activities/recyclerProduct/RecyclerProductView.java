package com.example.trashcoinapp.activities.recyclerProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.adapters.CartViewAdapter;
import com.example.trashcoinapp.adapters.ProductViewAdapter;
import com.example.trashcoinapp.adapters.RecyclerProductViewAdapter;
import com.example.trashcoinapp.models.Cart;
import com.example.trashcoinapp.models.Product;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerProductView extends AppCompatActivity {

    private ArrayList<Product> productArrayList;
    private RecyclerView productWrRV;
    private RecyclerProductViewAdapter recyclerProductViewAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    private PreferenceManager preference;
    String userId;
    ImageView img_add_product_system;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_product_view);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        preference = new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);

        productWrRV = findViewById(R.id.recycledProductRecycler);
        loadingPB = findViewById(R.id.idProgressBar);
        img_add_product_system=findViewById(R.id.img_add_product_system);
        db = FirebaseFirestore.getInstance();


        productArrayList = new ArrayList<>();
        productWrRV .setHasFixedSize(true);
        productWrRV .setLayoutManager(new LinearLayoutManager(this));



        img_add_product_system .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProduct.class);
                startActivity(intent);
            }
        });




        recyclerProductViewAdapter = new RecyclerProductViewAdapter(productArrayList, this);
        productWrRV.setAdapter(recyclerProductViewAdapter);
        db.collection("Products").whereEqualTo("userId", userId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Product p = d.toObject(Product.class);
                                p.setId(d.getId());
                                productArrayList.add(p);
                            }
                            recyclerProductViewAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(RecyclerProductView.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RecyclerProductView.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });




    }

}






