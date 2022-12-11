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
import com.example.trashcoinapp.databinding.ItemContainerCollectorBinding;
import com.example.trashcoinapp.databinding.ItemContainerRecyclerBinding;
import com.example.trashcoinapp.models.Cart;
import com.example.trashcoinapp.models.Collectors;
import com.example.trashcoinapp.models.Product;
import com.example.trashcoinapp.models.Recyclers;
import com.example.trashcoinapp.utilities.Constants;
import com.example.trashcoinapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecyclersAdapter extends RecyclerView.Adapter<RecyclersAdapter.RecyclersViewHolder> {


    private final List<Recyclers> recyclers;
    public RecyclersAdapter(List<Recyclers> recyclers) {
            this.recyclers = recyclers;
        }

        @NonNull
        @Override
        public RecyclersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemContainerRecyclerBinding itemContainerRecyclerBinding = ItemContainerRecyclerBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new RecyclersViewHolder(itemContainerRecyclerBinding);
        }

    @Override
    public void onBindViewHolder(@NonNull RecyclersViewHolder holder, int position) {
        holder.setRecyclerData(recyclers.get(position));
    }



        @Override
        public int getItemCount() {
            return recyclers.size();
        }

        class RecyclersViewHolder extends RecyclerView.ViewHolder{

            ItemContainerRecyclerBinding binding;

            public RecyclersViewHolder(ItemContainerRecyclerBinding itemContainerRecyclerBinding) {
                super(itemContainerRecyclerBinding.getRoot());
                binding = itemContainerRecyclerBinding;
            }

            void setRecyclerData(Recyclers recyclers){
                binding.tvRecyclerNameAns.setText(recyclers.recyclerName);
                binding.tvCollectorPhoneAns.setText(recyclers.recyclerTelephone);
                binding.tvRecyclerCompanyServiceAns.setText(recyclers.recyclerService);

            }
        }
    }




