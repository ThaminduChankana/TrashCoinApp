package com.example.trashcoinapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.inventory.EditInventory;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private Context context;
    private ArrayList item_id, item_name, item_description, pickup_date;
    int position;

    public InventoryAdapter(Context context, ArrayList item_id, ArrayList item_name, ArrayList item_description, ArrayList pickup_date){
        this.context = context;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_description = item_description;
        this.pickup_date = pickup_date;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_container_inventory, parent, false);
        return new InventoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.InventoryViewHolder holder,  final int position) {

        holder.tv_inventory_item_id_ans.setText(String.valueOf(item_id.get(position)));
        holder.tv_inventory_item_name_ans.setText(String.valueOf(item_name.get(position)));
        holder.tv_item_description_ans.setText(String.valueOf(item_description.get(position)));
        holder.tv_pickup_date_ans.setText(String.valueOf(pickup_date.get(position)));
        holder.btn_inventory_item_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditInventory.class);
                intent.putExtra("itemId", String.valueOf(item_id.get(holder.getAdapterPosition())));
                intent.putExtra("itemName", String.valueOf(item_name.get(holder.getAdapterPosition())));
                intent.putExtra("itemDescription", String.valueOf(item_description.get(holder.getAdapterPosition())));
                intent.putExtra("pickUpDate", String.valueOf(pickup_date.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    public class InventoryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_inventory_item_id_ans, tv_inventory_item_name_ans, tv_item_description_ans, tv_pickup_date_ans;
        Button btn_inventory_item_edit;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_inventory_item_id_ans = itemView.findViewById(R.id.tv_inventory_item_id_ans);
            tv_inventory_item_name_ans = itemView.findViewById(R.id.tv_inventory_item_name_ans);
            tv_item_description_ans = itemView.findViewById(R.id.tv_item_description_ans);
            tv_pickup_date_ans = itemView.findViewById(R.id.tv_pickup_date_ans);
            btn_inventory_item_edit = itemView.findViewById(R.id.btn_inventory_item_edit);
        }
    }
}
