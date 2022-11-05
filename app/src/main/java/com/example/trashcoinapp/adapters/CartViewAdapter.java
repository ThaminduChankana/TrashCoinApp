package com.example.trashcoinapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.cart.CartActivity;
import com.example.trashcoinapp.models.Cart;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartViewAdapter  extends RecyclerView.Adapter<CartViewAdapter.ViewHolder>{
    private ArrayList<Cart> cartArrayList;
    private Context context;

    public CartViewAdapter(ArrayList<Cart> cartArrayList, Context context) {
        this.cartArrayList = cartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.ViewHolder holder, int position) {

        Cart cart = cartArrayList.get(position);
        holder.productName.setText(cart .getProductName());
        holder.productPrice.setText(String.valueOf(cart.getTotalPrice()));
        holder.productQuantity.setText(String.valueOf(cart .getQuantity()));

        holder.qtyIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("Cart").document(cart.getId()).update("quantity",cart.getQuantity()+1);
                FirebaseFirestore.getInstance().collection("Cart").document(cart.getId()).update("totalPrice",(cart.getQuantity()+1)*cart.getPrice());
                Intent myIntent = new Intent(context,CartActivity.class);
                context.startActivity(myIntent);


            }
        });

        holder.qtyDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("Cart").document(cart.getId()).update("quantity",cart.getQuantity()-1);
                FirebaseFirestore.getInstance().collection("Cart").document(cart.getId()).update("totalPrice",(cart.getQuantity()-1)*cart.getPrice());
                Intent myIntent = new Intent(context,CartActivity.class);
                context.startActivity(myIntent);


            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("Cart").document(cart.getId()).delete();

                Intent myIntent = new Intent(context,CartActivity.class);
                context.startActivity(myIntent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView productName;
        private final TextView productPrice;
        private final TextView productQuantity;
        private final ImageView qtyIncrement;
        private final ImageView qtyDecrement;
        private final ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.TVProductName);
            productPrice = itemView.findViewById(R.id.TVProductPrice);
            productQuantity = itemView.findViewById(R.id.TVProductQuantity);
            qtyIncrement = itemView.findViewById(R.id.qtyIncrement);
            qtyDecrement = itemView.findViewById(R.id.qtyDecrement);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }

}
