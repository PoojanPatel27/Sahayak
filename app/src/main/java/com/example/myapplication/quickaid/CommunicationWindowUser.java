package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class CommunicationWindowUser extends AppCompatActivity {


    ImageButton sendBtn;
    EditText messageEt;
    RecyclerView recyclerView;

    Query reference;
    FirebaseAuth userAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_window);

        Intent intent = getIntent();
        String senderuid = intent.getStringExtra("uid");
        String orgId = "1";
        recyclerView = findViewById(R.id.chatRecView);

        reference = FirebaseDatabase.getInstance().getReference("collaboration chats").orderByChild("message");
        userAuth = FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();



        sendBtn = findViewById(R.id.btnSendCollab);
        messageEt = findViewById(R.id.messageEtCollab);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageEt.getText().toString();
                if (msg!=null){

                } else {
                    Toast.makeText(CommunicationWindowUser.this, "Can't send empty message!", Toast.LENGTH_SHORT).show();
                }
                messageEt.setText("");
            }
        });


    }
}

