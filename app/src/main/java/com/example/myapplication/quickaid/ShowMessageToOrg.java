package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowMessageToOrg extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView msgRecyclerView;
    private MessageAdapter messageAdapter;
    private FirebaseAuth orgAuth;
    private FirebaseUser currentOrg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message_to_org);

        msgRecyclerView = findViewById(R.id.msgRecView);
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this,new ArrayList<>());
        msgRecyclerView.setAdapter(messageAdapter);

        orgAuth= FirebaseAuth.getInstance();
        currentOrg = orgAuth.getCurrentUser();

        String uid = currentOrg.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        String orgUid = uid;

        DatabaseReference orgMsgRef = databaseReference.child("Organization").child(orgUid).child("messages");
        orgMsgRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MessageModel> messageList = new ArrayList<>();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()){
                    MessageModel messageModel = messageSnapshot.getValue(MessageModel.class);
                    messageList.add(messageModel);
                }
                messageAdapter.setMessageList(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}