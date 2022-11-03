package com.example.trashcoinapp.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.CartActivity;
import com.example.trashcoinapp.activities.cart.ProductViewActivity;
import com.example.trashcoinapp.models.Product;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.UUID;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder>{
    private ArrayList<Product> productArrayList;
    private Context context;
    private long count;

    PreferenceManager preference= new PreferenceManager(ProductViewActivity.getContext());
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    String userId = preference.getString(Constants.KEY_USER_ID);

    public ProductViewAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter.ViewHolder holder, int position) {
        Product products = productArrayList.get(position);
        holder.productTitleTv.setText(products.getTitle());
        holder.productPriceTv.setText(String.valueOf(products.getPrice()));
        holder.productDiscountTv.setText(products.getDiscountNote());
        Glide.with(context).load(productArrayList.get(position).getPicURL()).into(holder.imageView);

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }




        });

    }



    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productTitleTv;
        private final TextView productPriceTv;
        private final TextView productDiscountTv;
        ImageView imageView;
        Button addToCart;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitleTv = itemView.findViewById(R.id.productTitle);
            productPriceTv = itemView.findViewById(R.id.productPrice);
            productDiscountTv= itemView.findViewById(R.id.productDiscount);
            imageView = itemView.findViewById(R.id.productImage);
            addToCart = itemView.findViewById(R.id.addToCart);




        }
    }
}
