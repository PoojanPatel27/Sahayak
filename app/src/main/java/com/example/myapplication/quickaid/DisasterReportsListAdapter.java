package com.example.myapplication.quickaid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DisasterReportsListAdapter extends RecyclerView.Adapter<DisasterReportsListAdapter.ViewHolder> {

    Context context;
    ArrayList<DisasterReportModel>listDisasterReport;

    public DisasterReportsListAdapter(Context context, ArrayList<DisasterReportModel> listDisasterReport) {
        this.context = context;
        this.listDisasterReport = listDisasterReport;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.disaster_alert_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String description;

        DisasterReportModel model = listDisasterReport.get(position);

        holder.location.setText(model.getLocation());
        holder.contact.setText(model.getContact());
        Glide.with(context).load(model.getImageUrl()).into(holder.image);

        description = model.getDescription();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisasterReportDetailsOrg.class);
                intent.putExtra("location",model.getLocation());
                intent.putExtra("contact",model.getContact());
                intent.putExtra("description",description);
                intent.putExtra("imageUrl",model.getImageUrl());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return listDisasterReport.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView location,contact;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.disasterLocationTVOrg_item);
            contact = itemView.findViewById(R.id.disasterContactTVOrg_item);
            image = itemView.findViewById(R.id.disasterReportIVOrg_item);
        }
    }

}
