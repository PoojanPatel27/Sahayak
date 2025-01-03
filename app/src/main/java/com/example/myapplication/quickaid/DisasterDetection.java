package com.example.myapplication.quickaid;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisasterDetection extends AppCompatActivity {

    TextView wind,humidity,rainAmm,temperature,prediction;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser cUser;
    Button predictBtn;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_disaster_detection);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        wind = findViewById(R.id.windTVDD);
        humidity = findViewById(R.id.humidityTVDD);
        rainAmm = findViewById(R.id.rainAmmTVDD);
        temperature = findViewById(R.id.temperatureTVDD);
        prediction = findViewById(R.id.predictionTVDD);
        predictBtn = findViewById(R.id.predictBtn);
        progressBar = findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                prediction.setVisibility(View.VISIBLE);
                Toast.makeText(DisasterDetection.this, "No alert will be sent...", Toast.LENGTH_SHORT).show();
            }
        },3000);

        reference = FirebaseDatabase.getInstance().getReference("Disaster Detection Data");

        auth = FirebaseAuth.getInstance();
        cUser = auth.getCurrentUser();

        String uid = cUser.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DisasterDetectionUploadModel model = dataSnapshot.getValue(DisasterDetectionUploadModel.class);

                    wind.setText("Rain: "+model.getWindSpeed());
                    humidity.setText("Humidity: "+model.getHumidity());
                    rainAmm.setText("Rain Ammount: "+model.getRainAmount());
                    temperature.setText("Temperature: "+model.getTemperature());
                    prediction.setText("Prediction: "+model.getPrediction());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        predictBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prediction.setVisibility(View.VISIBLE);
//            }
//        });




    }

    private void progressBarVisible() {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                prediction.setText(View.VISIBLE);
            }
        },3000);
    }

}