package com.example.myapplication.quickaid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserComplaintHistoryAdapter extends RecyclerView.Adapter<UserComplaintHistoryAdapter.ViewHolder> {
    private Context context;
    private List<MessageModel> messageList;

    public UserComplaintHistoryAdapter(Context context, List<MessageModel> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_org_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);

        holder.nameTv.setText("Name: "+message.getName());
        holder.contactTv.setText("Contact: "+message.getContact());
        holder.locationTv.setText("Location: "+message.getLocation());
        holder.messageTv.setText("Problem: "+message.getProblem());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserHelpStatus.class);
                intent.putExtra("msgUid",message.getMessageUid());
                context.startActivity(intent);


            }
        });

    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,contactTv,locationTv,messageTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTextView);
            contactTv = itemView.findViewById(R.id.contactTextView);
            locationTv = itemView.findViewById(R.id.locationTextView);
            messageTv = itemView.findViewById(R.id.messageTextView);
        }
    }

}
