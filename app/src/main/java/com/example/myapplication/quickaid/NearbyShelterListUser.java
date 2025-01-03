package com.example.myapplication.quickaid;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NearbyShelterListUser extends AppCompatActivity {

    RecyclerView recyclerView;
    NearbyShelterAdapter adapter;
    List<ShelterinfoModel> shelterList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nearby_shelter_list_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.nearbyShelterRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shelterList = new ArrayList<>();

        String orgUid = "yka4i9sqqmRPH3HBJ6MJ33qZUlh1";
        reference = FirebaseDatabase.getInstance().getReference("Organization");

        reference.child(orgUid).child("Shelter_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShelterinfoModel model = dataSnapshot.getValue(ShelterinfoModel.class);
                    shelterList.add(model);
                }
                adapter = new NearbyShelterAdapter(getApplicationContext(),shelterList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NearbyShelterListUser.this, "Failed to load the data", Toast.LENGTH_SHORT).show();
            }
        });




    }
}