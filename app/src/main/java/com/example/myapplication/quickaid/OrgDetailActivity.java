package com.example.myapplication.quickaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OrgDetailActivity extends AppCompatActivity {

    private TextView name,experties,contact,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_detail);

        name = findViewById(R.id.orgNameDtl);
        experties = findViewById(R.id.orgContactTv);
        contact = findViewById(R.id.orgContactTv);
        address = findViewById(R.id.orgAddressTV);

        Intent intent = getIntent();
        String iname = intent.getStringExtra("name");
        String icategory = intent.getStringExtra("category");
        String icontact = intent.getStringExtra("contact");
        String iaddress = intent.getStringExtra("address");



        if (intent!=null){
           name.setText(iname);
           experties.setText(icategory);
           contact.setText(icontact);
           address.setText(iaddress);
        } else {
            name.setText("");
            experties.setText("");
            contact.setText("");
            address.setText("");
        }


    }
}