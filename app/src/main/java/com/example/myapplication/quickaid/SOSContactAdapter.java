package com.example.myapplication.quickaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SOSContactAdapter extends RecyclerView.Adapter<SOSContactAdapter.ViewHolder> {

    Context context;
    ArrayList<SOSContactsModel> contactList;

    public SOSContactAdapter(Context context, ArrayList<SOSContactsModel> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sos_contacts_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SOSContactsModel model =contactList.get(position);

        holder.contact1.setText("Contact 1: "+model.getContact1());
        holder.contact2.setText("Contact 2: "+model.getContact2());
        holder.contact3.setText("Contact 3: "+model.getContact3());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView contact1,contact2,contact3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contact1 = itemView.findViewById(R.id.sosContactTV1_item);
            contact2 = itemView.findViewById(R.id.sosContactTV2_item);
            contact3 = itemView.findViewById(R.id.sosContactTV3_item);
        }
    }
}
