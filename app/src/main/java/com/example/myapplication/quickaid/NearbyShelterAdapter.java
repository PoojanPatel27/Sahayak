package com.example.myapplication.quickaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NearbyShelterAdapter extends RecyclerView.Adapter<NearbyShelterAdapter.ShelterViewHolder> {

    private Context context;
    private List<ShelterinfoModel> shelterlList;

    public NearbyShelterAdapter(Context context, List<ShelterinfoModel> shelterlList) {
        this.context = context;
        this.shelterlList = shelterlList;
    }

    @NonNull
    @Override
    public ShelterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.nearby_shelter_list_item,parent,false);
        return new ShelterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterViewHolder holder, int position) {

        ShelterinfoModel model = shelterlList.get(position);

        holder.name.setText("Name: "+model.getName());
        holder.contact.setText("Contact: "+model.getContact());
        holder.capacity.setText("Total Capactity: "+model.getCapactiy());
        holder.location.setText("Location: "+model.getLocation());

    }

    @Override
    public int getItemCount() {
        return shelterlList.size();
    }

    public static class ShelterViewHolder extends RecyclerView.ViewHolder{

        TextView name,contact,capacity,location;

        public ShelterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shelterNameTV_item);
            contact = itemView.findViewById(R.id.shelterContactTV_item);
            capacity = itemView.findViewById(R.id.shelterCapacity_item);
            location = itemView.findViewById(R.id.shelterLocation_item);

        }
    }

}
