package com.example.myapplication.quickaid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserHelpHistoryAdapter extends RecyclerView.Adapter<UserHelpHistoryAdapter.ViewHolder> {

    private Context context;
    private List<UserHelpHistoryModel> helpHistoryList;

    public UserHelpHistoryAdapter(Context context, List<UserHelpHistoryModel> helpHistoryList) {
        this.context = context;
        this.helpHistoryList = helpHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_org_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         UserHelpHistoryModel helpHistoryModel= helpHistoryList.get(position);

         holder.name.setText("Name: "+helpHistoryModel.getName());
         holder.contact.setText("Contact: "+helpHistoryModel.getContact());
         holder.location.setText("Location: "+helpHistoryModel.getLocation());
         holder.problem.setText("Problem: "+helpHistoryModel.getProblem());

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, UserHelpStatus.class);
                 intent.putExtra("msgUid",helpHistoryModel.getMsgUid());

                 context.startActivity(intent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return helpHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,contact,location,problem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTextView);
            contact = itemView.findViewById(R.id.contactTextView);
            location = itemView.findViewById(R.id.locationTextView);
            problem = itemView.findViewById(R.id.messageTextView);
        }
    }

}
