package com.example.trashcoinapp.activities.cart;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.chat.ChatDisposer;
import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.activities.dashboards.WasteDisposerDashboard;
import com.example.trashcoinapp.models.Cart;
import com.example.trashcoinapp.models.Order;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Random;

public class CashOnDeliveryActivity extends AppCompatActivity {

    EditText name, contact, address;
    Button btn_proceed;
    private FirebaseFirestore db;
    Bundle bundle;
    private int orderNo;
    private float price;
    private String userId, order_name, order_address, productList,contactNo;
    String status="Pending";
    String productPrice;
    String orderNum;
    PreferenceManager preference;
    ImageView img_cash_on_delivery_back;
    String fixed_name, fixed_phone, fixed_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = FirebaseFirestore.getInstance();
        preference= new PreferenceManager(getApplicationContext());
        userId = preference.getString(Constants.KEY_USER_ID);
        bundle = getIntent().getExtras();

        fixed_name = preference.getString(Constants.KEY_FULL_NAME);
        fixed_phone = preference.getString(Constants.KEY_TELEPHONE);
        fixed_address = preference.getString(Constants.KEY_ADDRESS);

        productPrice =bundle.getString("price");
        productList = bundle.getString("productList");
        price = Float.valueOf(productPrice);
        img_cash_on_delivery_back =findViewById(R.id.img_cash_on_delivery_back);

        Random random = new Random();
        orderNo = random.nextInt(100000000);
        orderNum = "ref"+orderNo;

        name = findViewById(R.id.et_checkout_name);
        contact = findViewById(R.id.et_checkout_contact);
        address = findViewById(R.id.et_checkout_address);

        name.setText(fixed_name);
        contact.setText(fixed_phone);
        address.setText(fixed_address);
        btn_proceed = findViewById(R.id.btn_proceed);

        // back button
        img_cash_on_delivery_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CashOnDeliveryActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        // check the validations
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order_name = name.getText().toString();
                contactNo = contact.getText().toString();
                order_address = address.getText().toString();
                if (TextUtils.isEmpty( order_name)) {
                    name.setError("Name is required");
                }else if (TextUtils.isEmpty(contactNo)) {
                    contact.setError("Contact Number is required");
                }else if (TextUtils.isEmpty(order_address)) {
                    address.setError("Address is required");
                }else if (!TextUtils.isEmpty(order_name) && !TextUtils.isEmpty(contactNo) && !TextUtils.isEmpty(order_address))
                    addDataToFirestore(order_name,contactNo,order_address);
            }
        });
    }

    // add order details
    private void addDataToFirestore(String order_name, String contactNo, String order_address) {

        CollectionReference dborder = db.collection("Order");


        Order order = new Order( orderNum,userId,order_name,contactNo,order_address,price,productList,status);


        dborder.add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("Cart").whereEqualTo("userID", userId).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                if (!queryDocumentSnapshots.isEmpty()) {


                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {

                                        Cart c = d.toObject(Cart.class);

                                        DocumentReference df = db.collection("Cart").document(c.getId());
                                        df.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Intent intent = new Intent(CashOnDeliveryActivity.this, OrderActivity.class);
                                                    startActivity(intent);
                                                }else{

                                                }

                                            }
                                        });


                                    }

                                } else {
                                    Toast.makeText(CashOnDeliveryActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(CashOnDeliveryActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            }
                        });



                Toast.makeText(CashOnDeliveryActivity.this, "Your Delivery is now in Pending", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CashOnDeliveryActivity.this, "Fail to create order \n" + e, Toast.LENGTH_SHORT).show();
            }
        });



    }
}