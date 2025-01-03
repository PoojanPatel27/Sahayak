package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;

public class DashboardUser extends AppCompatActivity {

    CardView card1,card2,card3,card5,card4,card6,card7,card8,card9;
    ImageView userProfile;
    ImageButton logout,notification;
    TextView nametv,chatFromOrg,detectiondataUpload;
    FirebaseAuth userAuth;
    FirebaseUser fuser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.userCard2);
        card3 = findViewById(R.id.card3User);
        card5 = findViewById(R.id.card5);
        card4 = findViewById(R.id.card4);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        card9 = findViewById(R.id.card9);
        nametv = findViewById(R.id.userNameTv);
        userProfile = findViewById(R.id.imgUser);
        logout = findViewById(R.id.imageButtonLogout);
        notification = findViewById(R.id.notificationIBUser);
        chatFromOrg = findViewById(R.id.tvChatFromOrg);
        detectiondataUpload = findViewById(R.id.titleUploadDetectionData);

        userAuth = FirebaseAuth.getInstance();
        fuser = userAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        chatFromOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),ChatFromOrgToUser.class));
            }
        });

        if (fuser != null){
            String uid = fuser.getUid();

            reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        UserDetailsModel user = snapshot.getValue(UserDetailsModel.class);
                        if (user!=null){
                            String username = user.getName();
                            nametv.setText(username);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserComplain.class));
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserSOS.class));
            }
        });

//        not in use
//        card3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),NewMsgActivity.class);
//                intent.putExtra("userId",fuser.getUid());
//                startActivity(intent);
////                startActivity(new Intent(getApplicationContext(),NewMsgActivity.class));
//            }
//        });
//        not in use end

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NearbyShelterListUser.class));
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NgoList.class);
                startActivity(intent);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SOSContacts.class));
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReportDisaster.class));
            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DisasterPlanningWebView.class));
            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapRadar.class));
            }
        });



        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PredectionActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InboxUser.class));
            }
        });

////
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });

        detectiondataUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DisasterDetectionDataUpload.class));
            }
        });

        nametv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), APIFetch.class));
            }
        });
    }
}