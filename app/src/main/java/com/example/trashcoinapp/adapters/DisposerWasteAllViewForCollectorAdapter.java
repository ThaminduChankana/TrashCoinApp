
package com.example.trashcoinapp.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.trashcoinapp.R;
import com.example.trashcoinapp.models.HouseholdWaste;

import java.util.ArrayList;


public class DisposerWasteAllViewForCollectorAdapter extends RecyclerView.Adapter<DisposerWasteAllViewForCollectorAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<HouseholdWaste> wasteArrayList;
    private Context context;

    public DisposerWasteAllViewForCollectorAdapter(ArrayList<HouseholdWaste> wasteArrayList, Context context) {
        this.wasteArrayList = wasteArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public DisposerWasteAllViewForCollectorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.disposer_item_for_collector, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DisposerWasteAllViewForCollectorAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        HouseholdWaste householdWaste = wasteArrayList.get(position);
        holder.idTVName2.setText(householdWaste.getName());
        holder.idTVPhone2.setText(householdWaste.getPhone());
        holder.idTVAddress2.setText(householdWaste.getAddress());
        holder.idTVWasteItem2.setText(householdWaste.getHouseWaste());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return wasteArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.

        private final TextView idTVName2;
        private final TextView idTVPhone2;
        private final TextView idTVAddress2;
        private final TextView idTVWasteItem2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.

            idTVName2 = itemView.findViewById(R.id.idTVName2);
            idTVPhone2 = itemView.findViewById(R.id.idTVPhone2);
            idTVAddress2 = itemView.findViewById(R.id.idTVAddress2);
            idTVWasteItem2 = itemView.findViewById(R.id.idTVWasteItem2);
        }
    }
}

