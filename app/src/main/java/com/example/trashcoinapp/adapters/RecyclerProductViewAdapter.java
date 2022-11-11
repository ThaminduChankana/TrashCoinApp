package com.example.trashcoinapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.CartActivity;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.activities.cart.SingleProductActivity;
import com.example.trashcoinapp.activities.dashboards.WasteRecyclerDashboard;
import com.example.trashcoinapp.activities.recyclerProduct.EditProduct;
import com.example.trashcoinapp.activities.recyclerProduct.RecyclerProductView;
import com.example.trashcoinapp.models.Cart;
import com.example.trashcoinapp.models.Product;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecyclerProductViewAdapter extends RecyclerView.Adapter<RecyclerProductViewAdapter.ViewHolder>{
    ArrayList<Product> productArrayList;
    private Context context;

    private FirebaseFirestore db;




    public RecyclerProductViewAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }


    @Override
    public RecyclerProductViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerProductViewAdapter.ViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.description.setText(String.valueOf(product.getDescription()));
        Glide.with(context).load(productArrayList.get(position).getPicURL()).into(holder.imageView);









        holder.productsDeleteRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("Products").document(product.getId()).delete();
                Intent myIntent = new Intent(context,RecyclerProductView.class);
                context.startActivity(myIntent);


            }
        });



        holder.productsEditRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProduct.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", product.getId());
                bundle.putString("title", product.getTitle());
                bundle.putString("picUrl", product.getPicURL());
                bundle.putString("description", product.getDescription());
                bundle.putString("discountNote", product.getDiscountNote());
                bundle.putString("price", String.valueOf(product.getPrice()));
                bundle.putString("quantity", String.valueOf(product.getQuantity()));
                bundle.putString("discountPrice", String.valueOf(product.getDiscountPrice()));
                bundle.putString("category", product.getCategory());
                bundle.putString("Url", product.getPicURL());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });





//        private void deleteProduct() {
//
//            db.collection("Products").
//
//                    document(product.getId()).
//
//                    delete().
//
//                    addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//
//                            if (task.isSuccessful()) {
//
//                                Toast.makeText(RecyclerProductView.this, "Course has been deleted from Database.", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(RecyclerProductView.this, WasteRecyclerDashboard.class);
//                                startActivity(i);
//                            } else {
//
//                                Toast.makeText(RecyclerProductView.this, "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }



    }
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;
        private final TextView description;
        ImageView imageView;
        private final Button productsEditRC;
        private final Button productsDeleteRC;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            title = itemView.findViewById(R.id.productNameRC);
            price = itemView.findViewById(R.id.productPriceRC);
            description = itemView.findViewById(R.id.productDescriptionRC);
            imageView = itemView.findViewById(R.id.productImage);
            productsEditRC = itemView.findViewById(R.id.productsEditRC);
            productsDeleteRC = itemView.findViewById(R.id.productsDeleteRC);




        }
    }




}



