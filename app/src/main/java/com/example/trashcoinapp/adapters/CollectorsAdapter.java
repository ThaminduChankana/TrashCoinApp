package com.example.trashcoinapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.activities.collectors.CollectorsForDisposers;
import com.example.trashcoinapp.databinding.ItemContainerCollectorBinding;
import com.example.trashcoinapp.models.Collectors;

import java.util.List;

public class CollectorsAdapter extends RecyclerView.Adapter<CollectorsAdapter.CollectorsViewHolder> {

    private final List<Collectors> collectors;

    public CollectorsAdapter(List<Collectors> collectors) {
        this.collectors = collectors;
    }

    @NonNull
    @Override
    public CollectorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerCollectorBinding itemContainerCollectorBinding = ItemContainerCollectorBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CollectorsViewHolder(itemContainerCollectorBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectorsViewHolder holder, int position) {
        holder.setCollectorData(collectors.get(position));
    }

    @Override
    public int getItemCount() {
        return collectors.size();
    }

    class CollectorsViewHolder extends RecyclerView.ViewHolder{

        ItemContainerCollectorBinding binding;

        public CollectorsViewHolder(ItemContainerCollectorBinding itemContainerCollectorBinding) {
            super(itemContainerCollectorBinding.getRoot());
            binding = itemContainerCollectorBinding;
        }

        void setCollectorData(Collectors collectors){
            binding.tvCollectorNameAns.setText(collectors.collectorName);
            binding.tvCollectorPhoneAns.setText(collectors.collectorTelephone);
            binding.tvCollectorCompanyAns.setText(collectors.collectorCompany);
            binding.tvCollectorStartTimeAns.setText(collectors.startTime);
            binding.tvCollectorEndTimeAns.setText(collectors.endTime);
            binding.tvColWorkingAreaAns.setText(collectors.workingArea);
            binding.tvColAvailabilityAns.setText(collectors.availability);
        }
    }
}
