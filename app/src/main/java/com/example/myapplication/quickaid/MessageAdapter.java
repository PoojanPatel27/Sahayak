package com.example.myapplication.quickaid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<MessageModel> messageList;


    public MessageAdapter(Context context, List<MessageModel> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public void setMessageList(List<MessageModel> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
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
                Intent intent = new Intent(context,ComplaintStatusOrg.class);
//                intent.putExtra("name",message.getName());
//                intent.putExtra("userId",message.getUserUid());
//                context.startActivity(intent);

                intent.putExtra("name",message.getName());
                intent.putExtra("contact",message.getContact());
                intent.putExtra("location",message.getLocation());
                intent.putExtra("problem",message.getProblem());
                intent.putExtra("messageUid",message.getMessageUid());
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
