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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginOrg extends AppCompatActivity {

    TextView clickhere;
    EditText email,password;
    Button login;
    FirebaseAuth authUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_org);

        clickhere = findViewById(R.id.clickHeretv);

        email = findViewById(R.id.emailEtloginorg);
        password = findViewById(R.id.passwordLoginorg);
        login = findViewById(R.id.loginBtn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        
        authUser = FirebaseAuth.getInstance();

        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterOrg.class);
                startActivity(intent);
            }
        });
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                
                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginOrg.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    email.setError("Email cannot be empty!!");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginOrg.this, "Enter password!!", Toast.LENGTH_SHORT).show();
                    password.setError("Password cannot be empty!!");
                    password.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginOrg.this, "Re-enter email!!", Toast.LENGTH_SHORT).show();
                    email.setError("Enter valid email!!");
                    email.requestFocus();
                } else {
                    progressDialog.show();
                    loginOrg(textEmail,textPassword);
                }
            }
        });
        
        
    }

    private void loginOrg(String textEmail, String textPassword) {
        authUser.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginOrg.this, "Logged in succefully!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),DashboardOrg.class));
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginOrg.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = authUser.getCurrentUser();

        if (currentUser!=null){
            startActivity(new Intent(getApplicationContext(),DashboardOrg.class));
            finish();
        } else {

        }
    }
}