package com.example.myapplication.quickaid;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ReportDisaster extends AppCompatActivity {

    ImageView disasterImage;
    TextView autoLoacation;
    EditText locationET,contact,description;
    Button submit;
    private ProgressDialog progressDialog;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageuri;

    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_disaster);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        disasterImage = findViewById(R.id.disasterIV);
        autoLoacation = findViewById(R.id.autoLocationTVDisasterReport);
        locationET = findViewById(R.id.disasterLocationET);
        contact = findViewById(R.id.contactET);
        description = findViewById(R.id.disasterDescriptionET);
        submit = findViewById(R.id.submitDisasterBtn);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Disaster Reports");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        disasterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallary();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtLocation = locationET.getText().toString().trim();
                String txtContact = contact.getText().toString().trim();
                String txtDescription = description.getText().toString().trim();

                if (TextUtils.isEmpty(txtLocation)){
                    locationET.setError("Enter Location");
                    locationET.requestFocus();
                } else if (TextUtils.isEmpty(txtContact)){
                    contact.setError("Enter Contact");
                    contact.requestFocus();
                } else if (contact.length()!=10){
                    contact.setError("Enter a Valid Contact");
                    contact.requestFocus();
                } else if (TextUtils.isEmpty(txtDescription)){
                    description.setError("Enter desccription");
                    description.requestFocus();
                } else if (imageuri==null){
                    Toast.makeText(ReportDisaster.this, "Select an image..", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    UploadData(txtLocation,txtContact,txtDescription);
                }
            }
        });

        autoLoacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
                getPhoneNumber();
            }
        });


    }

    private void UploadData(String txtLocation, String txtContact, String txtDescription) {

        String fileName = UUID.randomUUID().toString();
        final StorageReference imageRef = storageReference.child("images/"+fileName);

        imageRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();
                        String uploadID = databaseReference.push().getKey();

                        DisasterReportModel model = new DisasterReportModel(txtLocation,txtContact,txtDescription,downloadUrl);

                        databaseReference.child(uploadID).setValue(model);
                        Toast.makeText(ReportDisaster.this, "Data uploaded Success", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        locationET.setText("");
                        contact.setText("");
                        description.setText("");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReportDisaster.this, "error "+e, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void OpenGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            imageuri = data.getData();
            disasterImage.setImageURI(imageuri);
        }
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            if (addresses!=null){
                                Address address = addresses.get(0);
                                String addr = address.getAddressLine(0);
                                locationET.setText(addr);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } else
        {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(ReportDisaster.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    private void getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ReportDisaster.this,new String[]{
                        Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS
                },121);
                return;
            }
            String phoneNumber = telephonyManager.getLine1Number();

            if (phoneNumber != null && phoneNumber.startsWith("+91")) {
                phoneNumber = phoneNumber.substring(3);
                contact.setText(phoneNumber);
            }

            Log.wtf("PhoneNumber","onCreate: "+phoneNumber);
            // Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }
}