package com.example.myapplication.quickaid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHelpStatus extends AppCompatActivity {

    TextView status,messageUID;
    DatabaseReference reference;
    Button helpStatusBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_help_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reference = FirebaseDatabase.getInstance().getReference("Organization");
        status = findViewById(R.id.statusTV);
        messageUID = findViewById(R.id.msgUidTV);
        helpStatusBtn = findViewById(R.id.helpStatusBtn);

        Intent intent = getIntent();
       String msgUID = intent.getStringExtra("msgUid");

       messageUID.setText(msgUID);

       fetchStatusCode(msgUID);

    }

    private void fetchStatusCode(String msgUID) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean messageFound = false;

                for (DataSnapshot orgSnapshot : snapshot.getChildren()){
                    DataSnapshot messagesSnapshot = orgSnapshot.child("messages");

                    if (messagesSnapshot.hasChild(msgUID)){
                        String txtStatus = messagesSnapshot.child(msgUID).child("status").getValue(String.class);

                        String acccepted = "1";
                        String notAccepted = "0";

                        if (txtStatus.equals(acccepted)){
                            status.setText("Help Status: Accepted By The Organization");
                            helpStatusBtn.setText("Help Accepted");
                            helpStatusBtn.setBackgroundColor(Color.parseColor("#6ffa80"));
                        } else if (txtStatus.equals(notAccepted)){
                            status.setText("Help Status: In Process...");
                            helpStatusBtn.setText("In Progress");
                            helpStatusBtn.setBackgroundColor(Color.parseColor("#ffe73a"));


                        }

                        messageFound = true;
                        break;
                    }
                }
                if (!messageFound){
                    status.setText("status not found");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                status.setText("Error fetching status "+ error);
            }
        });
    }
}