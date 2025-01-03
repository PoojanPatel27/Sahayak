package com.example.myapplication.quickaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ComplaintStatusOrg extends AppCompatActivity {

    TextView name,contact,location,problem;
    Button acceptHelp;

    FirebaseAuth auth;
    FirebaseUser cUser;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complaint_status_org);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.nameHSTV);
        contact = findViewById(R.id.contactHSTV);
        location = findViewById(R.id.locationHSTV);
        problem = findViewById(R.id.problemHSTV);
        acceptHelp = findViewById(R.id.acceptHelpBtn);

        auth = FirebaseAuth.getInstance();
        cUser = auth.getCurrentUser();

        Intent intent = getIntent();

        name.setText("Name: "+intent.getStringExtra("name"));
        contact.setText("Contact: "+intent.getStringExtra("contact"));
        location.setText("Location: "+intent.getStringExtra("location"));
        problem.setText("Problem: "+intent.getStringExtra("problem"));

        String messageUid = intent.getStringExtra("messageUid");
        String orgUid = cUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Organization").child(orgUid).child("messages").child(messageUid);




        acceptHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusCodeOrg();
            }
        });

    }

    private void updateStatusCodeOrg() {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("status","1");

        databaseReference.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ComplaintStatusOrg.this, "Status Updated Successfully...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ComplaintStatusOrg.this, "error due to "+e, Toast.LENGTH_SHORT).show();
            }
        });
    }





}