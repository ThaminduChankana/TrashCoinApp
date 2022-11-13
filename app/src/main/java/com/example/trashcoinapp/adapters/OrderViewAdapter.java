package com.example.trashcoinapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.models.Order;

import java.util.ArrayList;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewHolder> {
    private ArrayList<Order> orderArrayList;
    private Context context;

    public OrderViewAdapter(ArrayList<Order> orderArrayList, Context context) {
        this.orderArrayList = orderArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewAdapter.ViewHolder holder, int position) {
        Order orders = orderArrayList.get(position);
        holder.TVOrderNo.setText(orders.getOrderNo());
        holder.btn_status .setText(orders.getStatus());
    }
    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView TVOrderNo;
       private final Button btn_status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVOrderNo= itemView.findViewById(R.id.TVOrderNo);
            btn_status = itemView.findViewById(R.id. btn_status);

        }
    }
}
