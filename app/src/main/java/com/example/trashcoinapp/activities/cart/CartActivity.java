package com.example.trashcoinapp.activities.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.adapters.CartViewAdapter;
import com.example.trashcoinapp.models.Cart;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRV;
    private ArrayList<Cart> cartArrayList;
    private CartViewAdapter cartRVAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    private PreferenceManager preference;
    String userId;
    private TextView totalPrice;
    float total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
       getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        preference = new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);

        cartRV = findViewById(R.id.idRVCartItems);
        loadingPB = findViewById(R.id.idProgressBar);
        totalPrice = findViewById(R.id.totalPrice);


        db = FirebaseFirestore.getInstance();


        cartArrayList = new ArrayList<>();
        cartRV.setHasFixedSize(true);
        cartRV.setLayoutManager(new LinearLayoutManager(this));

        cartRVAdapter = new CartViewAdapter (cartArrayList, this);
        cartRV.setAdapter(cartRVAdapter);





        db.collection("Cart").whereEqualTo("userID", userId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                Cart c = d.toObject(Cart.class);
                                cartArrayList.add(c);
                                total = total+Float.valueOf(c.getTotalPrice());
                            }
                            totalPrice.setText(String.valueOf(total));
                            cartRVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(CartActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(CartActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}