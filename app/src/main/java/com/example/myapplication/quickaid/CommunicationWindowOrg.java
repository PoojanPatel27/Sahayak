package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommunicationWindowOrg extends AppCompatActivity {

    TextView nameTv;
    ImageView back;
    ImageButton send;
    EditText msgEt;
    String uid;
    String chk;

    RecyclerView recyclerView;



    Query reference;
    DatabaseReference ref2;
    FirebaseAuth userAuth;
    FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_window_org);

        nameTv = findViewById(R.id.orgNameTitleComOrg);
        back = findViewById(R.id.backImgBtnComOrg);
        send = findViewById(R.id.btnSendCollabOrg);
        msgEt = findViewById(R.id.messageEtCollabOrg);

        recyclerView = findViewById(R.id.chatRecViewOrg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String userId = intent.getStringExtra("userId");


        ref2 = FirebaseDatabase.getInstance().getReference("Users");
        userAuth = FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();
        String sender = currentUser.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        ref2.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    uid = userSnapshot.getKey();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (name!=null){
            nameTv.setText(name);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgEt.getText().toString();
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(CommunicationWindowOrg.this, "cannot send empty message!!!", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(sender,uid,msg);
                    msgEt.setText("");
                }
            }
        });



    }

    private void sendMessage(String sender, String uid, String msg) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",uid);
        hashMap.put("message",msg);

        reference.child("collaboration chats").push().setValue(hashMap);

    }

}