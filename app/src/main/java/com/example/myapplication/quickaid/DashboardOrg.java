package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardOrg extends AppCompatActivity {

    ImageView logout;
    CardView card1,card2,card3,card4,card5;
    TextView orgName;

    DatabaseReference reference,reference2;
    FirebaseAuth userAuth;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_org);

        logout = findViewById(R.id.imgLogoutOrg);
        card1 = findViewById(R.id.card1org);
        card2 = findViewById(R.id.card2org);
        card3 = findViewById(R.id.card3Org);
        card4 = findViewById(R.id.card4org);
        card5 = findViewById(R.id.card5org);
        orgName = findViewById(R.id.orgName);


        userAuth = FirebaseAuth.getInstance();
        fuser = userAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Organization");

        reference2 = FirebaseDatabase.getInstance().getReference("Organization");


        if (fuser!=null){
            String uidOrg = fuser.getUid();
            reference.child(uidOrg).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        OrgDetailsModel model = snapshot.getValue(OrgDetailsModel.class);
                        if (model!=null){
                            String textName = model.getName();
                            orgName.setText(textName);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowMessageToOrg.class));
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SOSAlertListOrg.class));
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CommunicationWindowOrg.class));
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DisasterReportsListOrg.class));
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddShelterInfoOrg.class));
            }
        });



    }
}