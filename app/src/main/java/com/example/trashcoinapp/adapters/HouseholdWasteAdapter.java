package com.example.trashcoinapp.adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcoinapp.R;
        import com.example.trashcoinapp.activities.householdDisposer.HouseholdwasteDetails;
import com.example.trashcoinapp.activities.householdDisposer.HouseholdwasteUpdate;
        import com.example.trashcoinapp.models.HouseholdWaste;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HouseholdWasteAdapter extends RecyclerView.Adapter<HouseholdWasteAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<HouseholdWaste> wasteArrayList;
    private Context context;


    // creating constructor for our adapter class
    public HouseholdWasteAdapter(ArrayList<HouseholdWaste> wasteArrayList, Context context) {
        this.wasteArrayList = wasteArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HouseholdWasteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.household_waste_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HouseholdWasteAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        HouseholdWaste householdWaste = wasteArrayList.get(position);
        holder.idTVName.setText(householdWaste.getName());
        holder.idTVPhone.setText(householdWaste.getPhone());
        holder.idTVAddress.setText(householdWaste.getAddress());
        holder.idTVWasteItem.setText(householdWaste.getHouseWaste());


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("HouseholdWaste").document(householdWaste.getObjectId()).delete();

                Intent myIntent = new Intent(context, HouseholdwasteDetails.class);
                context.startActivity(myIntent);


            }
        });

    }


    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return wasteArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView idTVName;
        private final TextView idTVPhone;
        private final TextView idTVAddress;
        private final TextView idTVWasteItem;
        private final Button btn_delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            idTVName= itemView.findViewById(R.id.idTVName);
            idTVPhone = itemView.findViewById(R.id.idTVPhone);
            idTVAddress= itemView.findViewById(R.id.idTVAddress);
            idTVWasteItem = itemView.findViewById(R.id.idTVWasteItem);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    HouseholdWaste householdWaste = wasteArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, HouseholdwasteUpdate.class);

                    // below line is for putting our  object to our next activity.
                    i.putExtra("householdWaste", householdWaste);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);
                }


            });





        }
    }
}



