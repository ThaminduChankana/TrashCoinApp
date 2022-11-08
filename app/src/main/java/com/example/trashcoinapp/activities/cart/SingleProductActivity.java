package com.example.trashcoinapp.activities.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class SingleProductActivity extends AppCompatActivity {

    TextView productTitle;
    EditText productDescription, productDiscountNote, productPrice, productCategory;
    private Bundle bundle;
    ImageView productImage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
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

        bundle = getIntent().getExtras();
        context = getApplicationContext();

        productTitle = findViewById(R.id.tv_product_title);
       productDescription = findViewById(R.id.tv_product_description);
       productDiscountNote = findViewById(R.id.tv_product_discount_note);
        productPrice = findViewById(R.id.tv_product_price);
        productCategory = findViewById(R.id.tv_product_category);
        productImage = findViewById(R.id.img_product_view);

        productTitle.setText(bundle.getString("title"));
       productDescription.setText(bundle.getString("description"));
        productDiscountNote.setText(bundle.getString("discountNote"));
        productPrice .setText(bundle.getString("price"));
        productCategory .setText(bundle.getString("category"));
        Picasso.get()
                .load(bundle.getString("picUrl"))
                .into(productImage);


    }
}