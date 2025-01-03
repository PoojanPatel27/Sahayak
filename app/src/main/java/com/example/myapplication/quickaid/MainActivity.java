package com.example.myapplication.quickaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    Button org,user,sos;
    CardView carduser,organization;
    FirebaseAuth authUser;
    FirebaseUser currentUser;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        org = findViewById(R.id.orgBtn);
        user = findViewById(R.id.userBtn);
        sos = findViewById(R.id.sosBtnMainActivity);
        carduser = findViewById(R.id.cardUser);
        organization = findViewById(R.id.cradOrganization);





        organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginType("organization");
                Intent intent = new Intent(getApplicationContext(),LoginOrg.class);
                startActivity(intent);

            }
        });

        carduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginType("user");
                Intent intent = new Intent(getApplicationContext(),LoginUser.class);
                startActivity(intent);
            }
        });

//        String lastLoginType = getLastLoginType();
//
//        if ("user".equals(lastLoginType)){
//            startActivity(new Intent(getApplicationContext(),DashboardUser.class));
//        } else if("organization".equals(lastLoginType)){
//            startActivity(new Intent(getApplicationContext(),DashboardOrg.class));
//        }



        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserSOS.class));
            }
        });




    }

    private String getLastLoginType() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getString("login_type","");
    }

    private void saveLoginType(String type) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login_type",type);
        editor.apply();
    }
}