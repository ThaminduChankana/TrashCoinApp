package com.example.trashcoinapp.activities.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CheckoutActivity extends AppCompatActivity {
    private Bundle bundle;
    private TextView tv_total_price, tv_total_discount,tv_total_payable;
    private Button btn_pay_cash_delivery;
    String price;
    String productList;
    ImageView img_checkout_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bundle = getIntent().getExtras();
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_total_discount = findViewById(R.id.tv_total_discount);
        tv_total_payable = findViewById(R.id.tv_total_payable);
        btn_pay_cash_delivery = findViewById(R.id.btn_pay_online);
        img_checkout_back = findViewById(R.id.img_checkout_back);

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

        bundle = getIntent().getExtras();
        float discount  = Float.valueOf(Float.valueOf(bundle.getString("withoutDiscount"))-Float.valueOf(bundle.getString("withDiscount")));
        tv_total_price.setText("Total Price : "+ bundle.getString("withoutDiscount"));
        tv_total_discount.setText("Discount : "+ String.valueOf(discount));
        tv_total_payable.setText("Total Payable : "+bundle.getString("withDiscount"));
        price = bundle.getString("withDiscount");
        productList = bundle.getString("productList");

        img_checkout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });




        btn_pay_cash_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, CashOnDeliveryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("price", price);
                bundle.putString("productList", productList );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}