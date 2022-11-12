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
import android.widget.Button;
import android.widget.ImageView;
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
    float withoutDiscount;
    float withDiscount;
    private Button btn_checkout;
    String productList = "";
    ImageView img_cart_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // bottom navigation bar
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
                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
                        overridePendingTransition(0, 0);
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

        db = FirebaseFirestore.getInstance();
        preference = new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);
        btn_checkout = (Button)findViewById(R.id.btn_checkout);
        cartRV = findViewById(R.id.idRVCartItems);
        loadingPB = findViewById(R.id.idProgressBar);
        totalPrice = findViewById(R.id.totalPrice);
        img_cart_back = findViewById(R.id.img_cart_back);

        cartArrayList = new ArrayList<>();
        cartRV.setHasFixedSize(true);
        cartRV.setLayoutManager(new LinearLayoutManager(this));

        cartRVAdapter = new CartViewAdapter (cartArrayList, this);
        cartRV.setAdapter(cartRVAdapter);

        // back button
        img_cart_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ProductViewActivity.class);
                startActivity(intent);
            }
        });

        // read the cart items in the database
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
                                withoutDiscount= withoutDiscount+Float.valueOf(c.getWithoutTotal());
                                withDiscount = withDiscount + Float.valueOf(c.getTotalPrice());
                                System.out.println(c.getProductName());
                                productList = productList + c.getProductName()+ " : " + String.valueOf(c.getQuantity())+" , ";

                            }
                            totalPrice.setText(String.valueOf(withoutDiscount));
                            cartRVAdapter.notifyDataSetChanged();
                            System.out.println(withoutDiscount);
                            if(withoutDiscount<=0.0){
                                btn_checkout.setClickable(false);
                            }
                        } else {
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(CartActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(CartActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });



        // checkout button
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("withDiscount", String.valueOf(withDiscount));
                bundle.putString("withoutDiscount", String.valueOf(withoutDiscount));
                bundle.putString("productList", productList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}