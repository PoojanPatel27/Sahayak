package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private EditText name,number,email,password;
    Button regBtn;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        name = findViewById(R.id.nameEtReg);
        number = findViewById(R.id.mobileNoEtReg);
        email = findViewById(R.id.emailEtReg);
        password = findViewById(R.id.passwordEtReg);
        progressBar = findViewById(R.id.progBar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        regBtn = findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textfullName = name.getText().toString();
                String textNumber = number.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();

                if (TextUtils.isEmpty(textfullName)){
                    Toast.makeText(RegisterUser.this, "Enter name!!!", Toast.LENGTH_SHORT).show();
                    name.setError("name cannot be empty!!");
                    name.requestFocus();
                }
                else if (TextUtils.isEmpty(textNumber)){
                    Toast.makeText(RegisterUser.this, "Enter Mobile Number!!!", Toast.LENGTH_SHORT).show();
                    number.setError("Mobile number cannot be empty!!");
                    number.requestFocus();
                }
                else if (textNumber.length() != 10){
                    Toast.makeText(RegisterUser.this, "Re-enter mobile no!!!", Toast.LENGTH_SHORT).show();
                    number.setError("Enter valid mobile no!!");
                    number.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegisterUser.this, "Re-enter email!!!", Toast.LENGTH_SHORT).show();
                    email.setError("enter valid email address!!");
                    email.requestFocus();
                }
                else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(RegisterUser.this, "Enter password!!!", Toast.LENGTH_SHORT).show();
                    password.setError("Password cannot be empty!!");
                    password.requestFocus();
                }
                else if (textPassword.length() < 6){
                    Toast.makeText(RegisterUser.this, "Re-enter Password!!!", Toast.LENGTH_SHORT).show();
                    password.setError("Password must contains 6 character!!");
                    password.requestFocus();
                }
                else {
//                    progressBar.setVisibility(View.VISIBLE);
                    progressDialog.show();
                    registerUser(textfullName, textNumber, textEmail, textPassword);
                }
            }
        });
    }

    private void registerUser(String textfullName, String textNumber, String textEmail, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    String uid = auth.getUid();

                    FirebaseUser firebaseUser = auth.getCurrentUser();


                    UserDetailsModel userDetails = new UserDetailsModel(textfullName, textNumber, textEmail, uid);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");



                    reference.child(firebaseUser.getUid()).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "User Registered Successfull!!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),DashboardUser.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(RegisterUser.this, "User registration failed!!", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        }
                    });
                } else {

                }
            }
        });
    }
}