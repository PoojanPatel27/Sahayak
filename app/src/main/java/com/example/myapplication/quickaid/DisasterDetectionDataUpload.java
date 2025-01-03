package com.example.myapplication.quickaid;

import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisasterDetectionDataUpload extends AppCompatActivity {

    EditText wind,rain,humidity,temperature,prediction;
    Button upload;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_disaster_detection_data_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        wind = findViewById(R.id.windSpeedETDU);
        rain = findViewById(R.id.rainAmmETDU);
        humidity = findViewById(R.id.humidityETDU);
        temperature = findViewById(R.id.temperatureETDU);
        prediction = findViewById(R.id.predictionETDU);

        upload = findViewById(R.id.uploadDataBtn);

        reference = FirebaseDatabase.getInstance().getReference("Disaster Detection Data");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtWind = wind.getText().toString().trim();
                String txtRain = rain.getText().toString().trim();
                String txtHumidity = humidity.getText().toString().trim();
                String txtTemperature = temperature.getText().toString().trim();
                String txtPrediction = prediction.getText().toString().trim();

                UploadDetectionData(txtWind,txtRain,txtHumidity,txtTemperature,txtPrediction);
            }
        });


    }

    private void UploadDetectionData(String txtWind, String txtRain, String txtHumidity, String txtTemperature, String txtPrediction) {

        FirebaseAuth auth;
        FirebaseUser cUser;

        auth = FirebaseAuth.getInstance();
        cUser = auth.getCurrentUser();

        String uid = cUser.getUid();

        DisasterDetectionUploadModel model = new DisasterDetectionUploadModel(txtWind,txtRain,txtHumidity,txtTemperature,txtPrediction);

        reference.child(uid).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DisasterDetectionDataUpload.this, "data uploaded success..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisasterDetectionDataUpload.this, "error.."+e, Toast.LENGTH_SHORT).show();
            }
        });

    }
}