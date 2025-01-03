package com.example.myapplication.quickaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class DisasterReportDetailsOrg extends AppCompatActivity {

    TextView location,contact,description;
    ImageView disasterImage;
    Button openMapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_disaster_report_details_org);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        location = findViewById(R.id.locationTVDisasterDetails);
        contact = findViewById(R.id.contactTVDisasterDetails);
        description = findViewById(R.id.descriptionTVDisasterDetails);
        disasterImage = findViewById(R.id.disasterImgIVDisasterDetails);
        openMapBtn = findViewById(R.id.openMapBtn);


        Intent intent = getIntent();

        location.setText("Location: "+ intent.getStringExtra("location"));
        contact.setText("Contact: "+ intent.getStringExtra("contact"));
        description.setText(intent.getStringExtra("description"));

        String imgUrl = intent.getStringExtra("imageUrl");
        Glide.with(this).load(imgUrl).into(disasterImage);

        openMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = intent.getStringExtra("location");
                openMap(address);
            }
        });

    }

    private void openMap(String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Handle the case where Google Maps is not installed
        }
    }
}