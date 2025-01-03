package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegisterOrg extends AppCompatActivity {

    private EditText name,number,category,email,passsword, addressIn,pincode;
    private TextView autoLocation,lattitude,longtitude;
    private Button register;
//    FirebaseAuth authUser;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    String uid;
    FirebaseAuth auth;
    FirebaseUser user;

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE=100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_org);

        name = findViewById(R.id.ngoNameEtReg);
        number = findViewById(R.id.ngomobileNoEtReg);
        category = findViewById(R.id.ngocategoryEtReg);
        email = findViewById(R.id.ngoemailEtReg);
        passsword = findViewById(R.id.ngopasswordEtReg);
        addressIn = findViewById(R.id.ngoaddressEtReg);
        pincode = findViewById(R.id.orgAreaPinReg);
        autoLocation = findViewById(R.id.autoLocationTv);
        lattitude = findViewById(R.id.latTV);
        longtitude = findViewById(R.id.longTv);
        register = findViewById(R.id.orgRegBtn);
        progressBar = findViewById(R.id.orgProgbarReg);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");

        auth = FirebaseAuth.getInstance();
        String fUser = auth.getUid();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textName = name.getText().toString();
                String textnumber = number.getText().toString();
                String textcategory = category.getText().toString();
                String textemail = email.getText().toString();
                String textpassword = passsword.getText().toString();
                String textAddress = addressIn.getText().toString();
                String textPincode = pincode.getText().toString();
                String textuid = fUser;
                
                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(RegisterOrg.this, "Enter name of organization!!", Toast.LENGTH_SHORT).show();
                    name.setError("Name cannot be empty");
                    name.requestFocus();
                } else if(TextUtils.isEmpty(textnumber)){
                    Toast.makeText(RegisterOrg.this, "Enter Contact Number!!", Toast.LENGTH_SHORT).show();
                    number.setError("mobile number must required!!");
                    number.requestFocus();
                } else if (textnumber.length() != 10){
                    Toast.makeText(RegisterOrg.this, "Enter 10 digit mobile number!!", Toast.LENGTH_SHORT).show();
                    number.setError("Enter valid mobile number");
                    number.requestFocus();
                } else if (TextUtils.isEmpty(textcategory)){
                    Toast.makeText(RegisterOrg.this, "Enter category of organization!!", Toast.LENGTH_SHORT).show();
                    category.setError("Category must required!!");
                    category.requestFocus();
                } else  if (TextUtils.isEmpty(textemail)){
                    Toast.makeText(RegisterOrg.this, "Enter email!!", Toast.LENGTH_SHORT).show();
                    email.setError("Email must required!!");
                    email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()){
                    Toast.makeText(RegisterOrg.this, "Re-enter email!!", Toast.LENGTH_SHORT).show();
                    email.setError("Enter valid email!!");
                    email.requestFocus();
                }
                else if(TextUtils.isEmpty(textpassword)){
                    Toast.makeText(RegisterOrg.this, "Re-Enter Password!!", Toast.LENGTH_SHORT).show();
                    passsword.setError("Password cannot be empty!!");
                    passsword.requestFocus();
                }
                else if (textpassword.length() < 6){
                    Toast.makeText(RegisterOrg.this, "Re-enter password!", Toast.LENGTH_SHORT).show();
                    passsword.setError("Password must caontain 6 character!!");
                    passsword.requestFocus();
                }
                else if (TextUtils.isEmpty(textAddress)){
                    Toast.makeText(RegisterOrg.this, "Enter Address!!", Toast.LENGTH_SHORT).show();
                    addressIn.setError("address cannot be empty!!");
                    addressIn.requestFocus();
                } else  if (TextUtils.isEmpty(textPincode)){
                    Toast.makeText(RegisterOrg.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
                    pincode.setError("Pincode required!!");
                    pincode.requestFocus();
                }
                else {
//                        progressBar.setVisibility(View.VISIBLE);
                        progressDialog.show();
                        registerOrg(textName,textnumber,textcategory,textemail,textpassword,textAddress,textPincode);
                }
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        autoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });




    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location!=null){
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    if (addresses!=null){
                                        Address address = addresses.get(0);
                                        String completeAddress = address.getAddressLine(0);
//                                        double lat = address.getLatitude();
//                                        double lng = address.getLongitude();

//                                        lattitude.setText((int) address.getLatitude());
//                                        longtitude.setText((int) address.getLongitude());


                                        RegisterOrg.this.addressIn.setText(completeAddress);
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
        ActivityCompat.requestPermissions(RegisterOrg.this,new String[]
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

    private void registerOrg(String textName, String textnumber, String textcategory, String textemail, String textpassword, String textAddress, String textPincode) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textemail,textpassword).addOnCompleteListener(RegisterOrg.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    String orgUid = auth.getUid();
//                    progressBar.setVisibility(View.GONE);
                    FirebaseUser user = auth.getCurrentUser();


                OrgDetailsModel orgdetails = new OrgDetailsModel(textName,textnumber,textcategory,textemail,textAddress,textPincode,orgUid);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Organization");

                    reference.child(user.getUid()).setValue(orgdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
//                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                Toast.makeText(RegisterOrg.this, "Registered Successfully!!!", Toast.LENGTH_SHORT).show();

                            } else {
//                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                Toast.makeText(RegisterOrg.this, "Error occured!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
            }
        });
    }
}