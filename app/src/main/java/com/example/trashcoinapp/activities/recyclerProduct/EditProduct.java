package com.example.trashcoinapp.activities.recyclerProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.models.Product;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditProduct extends AppCompatActivity {


    private EditText productTitleEdit, productDescriptionEdit, productCategoryEdit, productQuantityEdit, productPriceEdit, productDiscountPriceEdit, productDiscountNoteEdit;
    private Button submitProductBtn , DeleteProductBtn;
    String userId;
    private Uri imageUri;
    private Bundle bundle;
    String id;
    PreferenceManager preference;
    private FirebaseFirestore db;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    ImageView img_edit_product_back;
    String imge;
    private String productTitle, productDescription, productCategory,productDiscountNote;
    private float productQuantity, productPrice, productDiscountPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Product product = (Product) getIntent().getSerializableExtra("product");
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        db = FirebaseFirestore.getInstance();
        preference = new PreferenceManager(getApplicationContext());
        productTitleEdit = findViewById(R.id.productTitleEdit);
        productDescriptionEdit = findViewById(R.id.productDescriptionEdit);
        productCategoryEdit = findViewById(R.id.productCategoryEdit);
        productQuantityEdit = findViewById(R.id.productQuantityEdit);
        productPriceEdit = findViewById(R.id.productPriceEdit);
        productDiscountPriceEdit = findViewById(R.id.productDiscountPriceEdit);
        productDiscountNoteEdit = findViewById(R.id.productDiscountNoteEdit);
        submitProductBtn = findViewById(R.id.submitProductBtn);
        img_edit_product_back = findViewById(R.id.img_edit_product_back);
        userId = preference.getString(Constants.KEY_USER_ID);



        bundle = getIntent().getExtras();

        id =bundle.getString("id");
        productTitleEdit.setText(bundle.getString("title"));
        productDescriptionEdit.setText(bundle.getString("description"));
        productDiscountNoteEdit.setText(bundle.getString("discountNote"));
        productPriceEdit .setText(bundle.getString("price"));
        productCategoryEdit .setText(bundle.getString("category"));
        productPriceEdit .setText(bundle.getString("price"));
        productDiscountPriceEdit .setText(bundle.getString("discountPrice"));
        productQuantityEdit .setText(bundle.getString("quantity"));

        img_edit_product_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecyclerProductView.class);
                startActivity(intent);
            }
        });


        imge=bundle.getString("Url");
         id = bundle.getString("id");


        submitProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String P_productTitle = productTitleEdit.getText().toString();
                String P_productDescription = productDescriptionEdit.getText().toString();
                String P_productCategory = productCategoryEdit.getText().toString();
                String P_productDiscountNote = productDiscountNoteEdit.getText().toString();
                Float P_price = Float.valueOf(productPriceEdit.getText().toString());
                Float P_discountPrice = Float.valueOf(productDiscountPriceEdit.getText().toString());
                Float P_quantity = Float.valueOf(productQuantityEdit.getText().toString());

                if (TextUtils.isEmpty(P_productTitle)) {
                    productTitleEdit.setError("Please enter product Title");
                } else if (TextUtils.isEmpty(P_productDescription)) {
                    productDescriptionEdit.setError("Please enter product Description");
                } else if (TextUtils.isEmpty(P_productCategory)) {
                    productCategoryEdit.setError("Please enter Product category");
                } else if (TextUtils.isEmpty(P_productDiscountNote)) {
                    productDiscountNoteEdit.setError("Please enter discount Note");
                } else if (TextUtils.isEmpty(String.valueOf(P_price))) {
                    productPriceEdit.setError("Please enter Price");
                } else if (TextUtils.isEmpty(String.valueOf(P_discountPrice))) {
                    productDiscountPriceEdit.setError("Please enter discount Note");
                } else if (TextUtils.isEmpty(String.valueOf(P_quantity))) {
                    productQuantityEdit.setError("Please enter Quantity");
                } else {
                    updateProducts(P_productTitle,P_productDescription,P_productCategory,P_productDiscountNote,P_price,P_discountPrice,P_quantity);

                }
            }




            private void updateProducts( String productTitle, String productDescription, String productCategory, String productDiscountNote, float price, float discountPrice, float quantity) {

                Product updateProducts = new Product(id,userId,productTitle, productDescription, productCategory,imge, productDiscountNote ,price,discountPrice,quantity);



                db.collection("Products").

                        document(id).
                        set(updateProducts).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProduct.this, "product has been updated..", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), RecyclerProductView.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProduct.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
    }}

