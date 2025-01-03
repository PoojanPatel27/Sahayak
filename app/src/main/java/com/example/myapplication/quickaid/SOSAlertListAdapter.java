package com.example.myapplication.quickaid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SOSAlertListAdapter extends RecyclerView.Adapter<SOSAlertListAdapter.ViewHolder> {

    Context context;
    ArrayList<SosAlertModel> listSosAlert;

    public SOSAlertListAdapter(Context context, ArrayList<SosAlertModel> listSosAlert) {
        this.context = context;
        this.listSosAlert = listSosAlert;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sos_alert_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SosAlertModel model = listSosAlert.get(position);
        holder.txtName.setText("Name: "+model.getName());
        holder.txtContact.setText("Contact: "+model.getContact());
        holder.txtLocation.setText("Location: "+model.getLocation());
        holder.txtPincode.setText("Pincode: "+model.getAreaCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                message activity code start

//                Intent intent = new Intent(context,NewMsgActivity.class);
//                intent.putExtra("name",model.getName());
//                intent.putExtra("userId",model.getUid());
//                context.startActivity(intent);

//                message activity code end
                openMap(model.getLocation());

            }
        });

    }

    private void openMap(String location) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        } else {
            // Handle the case where Google Maps is not installed
        }
    }

    @Override
    public int getItemCount() {
        return listSosAlert.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtContact,txtLocation,txtPincode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.nameSosItem);
            txtContact = itemView.findViewById(R.id.contactSosItem);
            txtLocation = itemView.findViewById(R.id.locationSosItem);
            txtPincode = itemView.findViewById(R.id.areacodeSosItem);

        }
    }
}
