package com.example.myapplication.quickaid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class AddShelterInfoOrg extends AppCompatActivity {

    EditText name,contact,capacity,location;
    Button upload;
    ProgressBar progressBar;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser fUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_shelter_info_org);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.shelterName);
        contact = findViewById(R.id.shelterContact);
        capacity = findViewById(R.id.shelterCapacity);
        location = findViewById(R.id.shelterLocation);
        progressBar = findViewById(R.id.progressBarAddShelter);
        upload = findViewById(R.id.shelterUploadBtn);

        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();



        reference = FirebaseDatabase.getInstance().getReference("Organization");





        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = name.getText().toString().trim();
                String txtContact = contact.getText().toString().trim();
                String txtCapacity = capacity.getText().toString().trim();
                String txtLocation = location.getText().toString().trim();


                if (txtName.isEmpty()){
                    name.requestFocus();
                    name.setError("cannot be empty");
                } else if (txtContact.isEmpty()){
                    contact.requestFocus();
                    contact.setError("cannot be empty");
                } else if (txtCapacity.isEmpty()) {
                    capacity.requestFocus();
                    capacity.setError("cannot be empty");
                } else if (txtLocation.isEmpty()) {
                    location.requestFocus();
                    location.setError("cannot be empty");
                } else {
                    uploadShelterData(txtName,txtContact,txtCapacity,txtLocation);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void uploadShelterData(String txtName, String txtContact, String txtCapacity, String txtLocation) {

        String shelterUid = reference.push().getKey();
        String orgUid = fUser.getUid();

        ShelterinfoModel shelterinfoModel = new ShelterinfoModel(txtName,txtContact,txtCapacity,txtLocation,shelterUid);

        reference.child(orgUid).child("Shelter_Info").child(shelterUid).setValue(shelterinfoModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddShelterInfoOrg.this, "Shelter Added Success...", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddShelterInfoOrg.this, "Error occured: "+e, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}