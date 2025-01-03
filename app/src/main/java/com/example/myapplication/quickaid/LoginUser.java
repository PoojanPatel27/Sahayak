package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {

    private EditText email,password;
    private ProgressBar progressBar;
    TextView reg;
    Button login;
    private FirebaseAuth authUser;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        email = findViewById(R.id.emailEtLoginUser);
        password = findViewById(R.id.passwordEtloginUser);
        reg = findViewById(R.id.registerTv);
        login = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progBarLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please Wait...");


        authUser = FirebaseAuth.getInstance();



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterUser.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String textEmail = email.getText().toString();
                    String textPassword = password.getText().toString();

//                    progressBar.setVisibility(View.VISIBLE);

                    if (TextUtils.isEmpty(textEmail)){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginUser.this, "Please enter email!!", Toast.LENGTH_SHORT).show();
                        email.setError("Email can't be empty");
                        email.requestFocus();
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginUser.this, "Please re-enter email!!", Toast.LENGTH_SHORT).show();
                        email.setError("Enter valid email!!!");
                        email.requestFocus();
                    }else if(TextUtils.isEmpty(textPassword)){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginUser.this, "Enter password", Toast.LENGTH_SHORT).show();
                        password.setError("Password can't be empty");
                        password.requestFocus();
                    }else  {
//                        progressBar.setVisibility(View.VISIBLE);
                        progressDialog.show();
                        loginUser(textEmail,textPassword);

                        
                    }
            }
        });
    }

    private void loginUser(String textEmail, String textPassword) {
        authUser.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
//                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(LoginUser.this, "Logged in!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),DashboardUser.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginUser.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
       FirebaseUser mFirebaseUser = authUser.getCurrentUser();

       if (mFirebaseUser != null){

           Intent intent = new Intent(getApplicationContext(),DashboardUser.class);
           startActivity(intent);
           finish();
       }
       else {

       }
    }

}