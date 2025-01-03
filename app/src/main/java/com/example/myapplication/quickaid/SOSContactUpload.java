package com.example.myapplication.quickaid;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SOSContactUpload extends AppCompatActivity {

    EditText e1,e2,e3;
    Button uploadContact;


    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_soscontact_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        e1 = findViewById(R.id.contact1Et);
        e2 = findViewById(R.id.contact2Et);
        e3 = findViewById(R.id.contact3Et);
        uploadContact = findViewById(R.id.uploadBtn);


        uploadContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtContact1 = e1.getText().toString().trim();
                String txtContact2 = e2.getText().toString().trim();
                String txtContact3 = e3.getText().toString().trim();

                if (TextUtils.isEmpty(txtContact1)){
                    e1.setError("Enter Contact 1");
                    e1.requestFocus();
                }
                else if (TextUtils.isEmpty(txtContact2)) {
                    e2.setError("Enter Contact 2");
                    e2.requestFocus();
                }
                else if (TextUtils.isEmpty(txtContact3)) {
                    e3.setError("Enter Contact 3");
                    e3.requestFocus();
                }
                else if (txtContact1.length() < 10){
                    e1.setError("Enter Valid Number!");
                    e1.requestFocus();
                }
                else if (txtContact2.length() < 10){
                    e1.setError("Enter Valid Number!");
                    e1.requestFocus();
                }
                else if (txtContact3.length() < 10){
                    e1.setError("Enter Valid Number!");
                    e1.requestFocus();
                }
                else {
                    UploadContact(txtContact1,txtContact2,txtContact3);
                }

            }
        });


    }

    private void UploadContact(String txtContact1, String txtContact2, String txtContact3) {

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        fUser = mAuth.getCurrentUser();

        String uid = fUser.getUid();

        SOSContactsModel model = new SOSContactsModel(txtContact1,txtContact2,txtContact3);

        reference.child(uid).child("SOS_Contacts").setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SOSContactUpload.this, "Sucessfully uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SOSContactUpload.this, "Error Occured!! Failed to upload!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}