package com.example.trashcoinapp.activities.recyclerProduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.models.Product;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class AddProduct extends AppCompatActivity {


    private EditText productTitle, productDescription, productCategory, productQuantity, productPrice, productDiscountPrice, productDiscountNote;
    private Button addProductBtn;
    private ImageView imageView;
    String userId;
    private Uri imageUri;
    String img;
    PreferenceManager preference;
    private FirebaseFirestore db;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    ImageView img_add_product_back;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        db = FirebaseFirestore.getInstance();
        preference = new PreferenceManager(getApplicationContext());
        productTitle = findViewById(R.id.productTitle);
        productDescription = findViewById(R.id.productDescription);
        productCategory = findViewById(R.id.productCategory);
        productQuantity = findViewById(R.id.productQuantity);
        productPrice = findViewById(R.id.productPrice);
        productDiscountPrice = findViewById(R.id.productDiscountPrice);
        productDiscountNote = findViewById(R.id.productDiscountNote);
        addProductBtn = findViewById(R.id.addProductBtn);
        imageView = findViewById(R.id.productImage);
        userId = preference.getString(Constants.KEY_USER_ID);

        ImageView img_add_product_back=findViewById(R.id.img_add_product_back);

        img_add_product_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecyclerProductView.class);
                startActivity(intent);
            }
        });


//        String P_productTitle = productTitle.getText().toString();
//        String P_productDescription = productDescription.getText().toString();
//        String P_productCategory = productCategory.getText().toString();
//        String P_productDiscountNote = productDiscountNote.getText().toString();
//        Float P_price = Float.valueOf(productPrice.getText().toString());
//        Float P_discountPrice = Float.valueOf(productDiscountPrice.getText().toString());
//        Float P_quantity = Float.valueOf(productQuantity.getText().toString());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             if (TextUtils.isEmpty(P_productTitle)) {
//                      productTitle.setError("Please enter product Title");
//                } else if (TextUtils.isEmpty(P_productDescription)) {
//                      productDescription.setError("Please enter product Description");
//                } else if (TextUtils.isEmpty(P_productCategory)) {
//                      productCategory.setError("Please enter Product category");
//                } else if (TextUtils.isEmpty(P_productDiscountNote)) {
//                      productDiscountNote.setError("Please enter discount Note");
//                } else if (TextUtils.isEmpty(String.valueOf(P_price))) {
//                      productPrice.setError("Please enter Price");
//                } else if (TextUtils.isEmpty(String.valueOf(P_discountPrice))) {
//                      productDiscountPrice.setError("Please enter discount Note");
//                } else if (TextUtils.isEmpty(String.valueOf(P_quantity))) {
//                      productQuantity.setError("Please enter Quantity");
               if (imageUri == null) {
                    Toast.makeText(AddProduct.this, "Please select image", Toast.LENGTH_SHORT).show();
                } else {
                    uploadToFirebase(imageUri);
                }
            }
        });
    }

    private void addProduct(EditText productTitle, EditText productDescription, EditText productCategory, EditText productPrice, EditText productDiscountPrice, EditText productDiscountNote, Uri productImage, String userId) {
//        CollectionReference dbProducts = db.collection("Products");
        id = UUID.randomUUID().toString();
        DocumentReference dbProducts = db.collection("Products").document(id);


        img = "https://firebasestorage.googleapis.com/v0/b/trashcoin-app-4d00d.appspot.com/o/compost.jpg?alt=media&token=9ff378a8-3c67-4532-ae32-5bc9981ec194";
        String title = productTitle.getText().toString();
        String description = productDescription.getText().toString();
        String category = productCategory.getText().toString();
        float quantity = Float.valueOf(productQuantity.getText().toString());
        String imageURL = imageUri.toString();
        String discountNote = productDiscountNote.getText().toString();
        float price = Float.valueOf(productPrice.getText().toString());
        float discount = Float.valueOf(productDiscountPrice.getText().toString());


        Product product = new Product(id,userId, title, description, category, img, discountNote, price, discount,quantity);

        dbProducts.set(product);
//        dbProducts.add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//
//                Toast.makeText(AddProduct.this, "Your Product has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(AddProduct.this, "Fail to add product \n" + e, Toast.LENGTH_SHORT).show();
//            }
//        });

        Intent intent = new Intent(this, WasteRecyclerDashboard.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        addProduct(productTitle, productDescription, productCategory, productPrice, productDiscountPrice, productDiscountNote, uri, userId);
                        Toast.makeText(AddProduct.this, "Upload success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProduct.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }


}
