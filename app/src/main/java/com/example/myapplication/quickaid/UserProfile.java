package com.example.myapplication.quickaid;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {

//    RecyclerView recyclerView;
//    UserComplaintHistoryAdapter adapter;
//    List<MessageModel> messageList;
//    DatabaseReference reference;
//    FirebaseAuth mAuth;
//    FirebaseUser cUser;

//    private TextView name,email,contact;
    private EditText name,email,contact;
    private RecyclerView recyclerView;
    private UserHelpHistoryAdapter adapter;
    private List<UserHelpHistoryModel> helpHistoryList;
    private DatabaseReference databaseReference,profilereference;
    private FirebaseAuth auth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        recyclerView = findViewById(R.id.userProfileRV);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        messageList = new ArrayList<>();
//        adapter = new UserComplaintHistoryAdapter(this,messageList);
//        recyclerView.setAdapter(adapter);
//
//        mAuth = FirebaseAuth.getInstance();
//        cUser = mAuth.getCurrentUser();
//
//        String UID = cUser.getUid();
//
//
//
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("help history");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
//                    messageList.add(messageModel);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        String uid ="yka4i9sqqmRPH3HBJ6MJ33qZUlh1";
//
//        reference = FirebaseDatabase.getInstance().getReference("Organization").child(uid).child("messages");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
//                    messageList.add(messageModel);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        name = findViewById(R.id.nameETUserProfile);
        email = findViewById(R.id.emailETUserProfile);
        contact = findViewById(R.id.contactETUserProfile);


        recyclerView = findViewById(R.id.userProfileRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        helpHistoryList = new ArrayList<>();
        adapter = new UserHelpHistoryAdapter(this,helpHistoryList);
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        FirebaseUser cUser = auth.getCurrentUser();

        if (cUser!=null){
            String uid = cUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("help history");
            FetchHelpHistory();
            FetchProfileDetails(uid);
        }





    }

    private void FetchProfileDetails(String uid) {

        profilereference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        profilereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserDetailsModel model = snapshot.getValue(UserDetailsModel.class);
                    if (model!=null){
                        name.setText("Name: "+model.getName());
                        contact.setText("Contact: "+model.getNumber());
                        email.setText("Email: "+model.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void FetchHelpHistory() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserHelpHistoryModel model = dataSnapshot.getValue(UserHelpHistoryModel.class);
                    helpHistoryList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    private void RetriveAllMsg(){
//        reference = FirebaseDatabase.getInstance().getReference("Organization");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                messageList.clear();
//
//                for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
//                    // Iterate through all messages under each UID
//                    for (DataSnapshot messageSnapshot : uidSnapshot.child("messages").getChildren()) {
//                        MessageModel message = messageSnapshot.getValue(MessageModel.class);
//                        messageList.add(message);
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}

