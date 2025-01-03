package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserSOS extends AppCompatActivity {
    private EditText name,contact, locationEt,areaCode;
    private Button submit;
    private ProgressBar progressBar;
    private CheckBox chk1,chk2,chk3,chk4,chk5,chk6;

    DatabaseReference reference;
    private FirebaseUser currentUser;

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sos);

        name = findViewById(R.id.sosNameEt);
        contact = findViewById(R.id.sosContactEt);
        locationEt = findViewById(R.id.sosLocationEt);
        areaCode = findViewById(R.id.sosAreaCodeEt);
        submit = findViewById(R.id.sossubmitBtn);
        progressBar = findViewById(R.id.progBarUserSos);

        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);
        chk3 = findViewById(R.id.chk3);
        chk4 = findViewById(R.id.chk4);
        chk5 = findViewById(R.id.chk5);
        chk6 = findViewById(R.id.chk6);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser!=null){
            String uid = currentUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String nameEt = snapshot.child("name").getValue(String.class);
                        String contactEt = snapshot.child("number").getValue(String.class);

                        if (nameEt!=null){
                            name.setText(nameEt);
                        }
                        if (contactEt!=null){
                            contact.setText(contactEt);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

//        contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPhoneNumber();
//            }
//        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textName = name.getText().toString();
                String textContact = contact.getText().toString();
                String textLocation = locationEt.getText().toString();
                String textAreacode = areaCode.getText().toString();


                if (TextUtils.isEmpty(textName) || TextUtils.isEmpty(textContact)){
                    name.setText("null");
                }
//                else if (TextUtils.isEmpty(textContact)){
//                    contact.setText("null");
//                }

//                else if ((textContact.length()!=10)){
//                    contact.setError("Enter Valid No.");
//                }

                else if (TextUtils.isEmpty(textLocation)){
                    locationEt.setError("Enter Location");
                } else if (TextUtils.isEmpty(textAreacode)){
                    areaCode.setError("Enter Pincode");
                } else if (textAreacode.length()!=6){
                    areaCode.setError("Enter Valid Pincode");
                }

                else {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadSosAlert(textName,textContact,textLocation,textAreacode);
                }
            }
        });
    }

    private void uploadSosAlert(String textName, String textContact, String textLocation, String textAreacode) {
        FirebaseAuth userAuth = FirebaseAuth .getInstance();
        FirebaseUser currentUser = userAuth.getCurrentUser();
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        String uid;
        if (currentUser==null){
            uid = dateFormat.format(currentTime);
        } else {
             uid = userAuth.getUid();
        }


        SosAlertModel sosModel = new SosAlertModel(textName,textContact,textLocation,textAreacode,uid);
        
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("sosAlerts");
        
        reference.child(uid).setValue(sosModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UserSOS.this, "Alert Sent Successfully!!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    contact.setText("");
                    locationEt.setText("");
                    areaCode.setText("");
//                    startActivity(new Intent(getApplicationContext(),CommunicationWindowUser.class));
                    Intent intent = new Intent(getApplicationContext(),CommunicationWindowUser.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UserSOS.this, "Error occured!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        getPhoneNumber();
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        Geocoder geocoder = new Geocoder(UserSOS.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                if (addresses!=null){
                                    Address address = addresses.get(0);
                                    String addr = address.getAddressLine(0);
                                    String pincode = address.getPostalCode();
                                    UserSOS.this.locationEt.setText(addr);
                                    UserSOS.this.areaCode.setText(pincode);
                                }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(UserSOS.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Required Permission!!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(UserSOS.this,new String[]{
                        Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS
                },121);
                return;
            }
            String phoneNumber = telephonyManager.getLine1Number();
            contact.setText(phoneNumber);
            Log.wtf("PhoneNumber","onCreate: "+phoneNumber);
           // Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==121 && resultCode == RESULT_OK){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}