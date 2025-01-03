package com.example.myapplication.quickaid;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PredectionActivity extends AppCompatActivity {


    double temperatureMean = 34.83898524;
    double humidityMean = 75.15512242;
    double windMean = 31.91727892;
    double pressureMean = 986.96119244;

    double temperatureScale = 8.21632595;
    double humidityScale = 22.59535275;
    double windScale = 13.0861531;
    double pressureScale = 26.59615109;

    private double temperatureAPI;
    private double humidityAPI;
    private double windSpeedAPI;
    private double pressureAPI;

//    double temperatureMain = 42.0;
//    double humidityMain = 82.71;
//    double windspeedMain = 45.7811;
//    double preassureMain = 985.260;

    TextView result,temparatureTV,humidityTV,windspeedTV,pressureTV;
    Button resultButton;
    RelativeLayout relativeLayout;

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable stopAudioRunnable;
    private long duration = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_predection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        relativeLayout = findViewById(R.id.whiteScreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.GONE);
            }
        },3500);



        result = findViewById(R.id.predectionResult);
        resultButton = findViewById(R.id.predectionResultBtn);

        temparatureTV = findViewById(R.id.temperaturePredTV);
        humidityTV = findViewById(R.id.humidityPredTV);
        windspeedTV = findViewById(R.id.windSpeedTVPred);
        pressureTV = findViewById(R.id.pressurePredTV);


        String url = "https://api.tomorrow.io/v4/weather/forecast?location=22.3071,73.1812&apikey=QXAFXqEPleW7M8SI6zPQluIXQF5aN7BL";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(PredectionActivity.this, "Request failed.. "+e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonData = response.body().string();
                    JsonObject jsonResponse = JsonParser.parseString(jsonData).getAsJsonObject();

                    JsonObject timelines = jsonResponse
                            .getAsJsonObject("timelines");
                    if (timelines != null){
                        JsonArray minutelyArray = timelines.getAsJsonArray("minutely");
                        if (minutelyArray != null && minutelyArray.size() >0){
                            JsonObject firstEntry = minutelyArray.get(0).getAsJsonObject();
                            JsonObject values = firstEntry.getAsJsonObject("values");

                            temperatureAPI = Double.parseDouble(values.has("temperature") ? values.get("temperature").getAsString() : "N/A");
                            humidityAPI = Double.parseDouble(values.has("humidity") ? values.get("humidity").getAsString() : "N/A");
                            windSpeedAPI = Double.parseDouble(values.has("windSpeed") ? values.get("windSpeed").getAsString() : "N/A");
                            pressureAPI = Double.parseDouble(values.has("pressureSurfaceLevel") ? values.get("pressureSurfaceLevel").getAsString() : "N/A");

                        }
                    }



                    runOnUiThread(()->{


                        temparatureTV.setText(String.valueOf("Temperature: "+temperatureAPI)+" °C");
                        humidityTV.setText(String.valueOf("Humidity: "+humidityAPI)+"%");
                        double WindInKM = Double.parseDouble(String.valueOf(windSpeedAPI * 3.6));
                        windspeedTV.setText(String.format("WindSpeed: %.2f", WindInKM)+" km/h");
                        pressureTV.setText(String.valueOf("Pressure: "+pressureAPI)+" pa");

                    });




                } else {
                    Toast.makeText(PredectionActivity.this, "Request not successful!!", Toast.LENGTH_SHORT).show();
                }
            }
        });








        float scaleTemperature = (float) ((temperatureAPI-temperatureMean)/temperatureScale);
        float[] temperature = {scaleTemperature};

        float scaleHumidity = (float) ((humidityAPI-humidityMean)/humidityScale);
        float[] humidity = {scaleHumidity};

        float scaleWindspeed = (float) ((windSpeedAPI-windMean)/windScale);
        float[] windSpeed = {scaleWindspeed};

        float scalePreassure = (float) ((pressureAPI-pressureMean)/pressureScale);
        float[] preassure = {scalePreassure};

        TFLiteHelper tfLiteHelper = new TFLiteHelper("model.tflite",getAssets());

        float[] predictions = tfLiteHelper.predict(temperature,humidity,windSpeed,preassure);

        StringBuilder predectionResult = new StringBuilder();
        for (int i=0; i< predictions.length; i++){
//            predectionResult.append("Class ").append(": ").append(predictions[i]).append("\n");
            predectionResult.append(predictions[i]);

            if (predictions[i] <= 0.5)
            {
                resultButton.setBackgroundColor(Color.parseColor("#3cff56"));
                resultButton.setText("No disaster detected...");
                resultButton.setTextColor(Color.parseColor("#000000"));


                PlayAlertSound(predictions[i]);


            } else {
                resultButton.setBackgroundColor(Color.parseColor("#ff3030"));
                resultButton.setText("Disaster detected...");
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (vibrator != null && vibrator.hasVibrator()){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(3000,VibrationEffect.DEFAULT_AMPLITUDE));
                            }
                        }
                    }
                },3500);
            }


        }
    }

    private void PlayAlertSound(float prediction) {
        if (prediction<=0.5){

            mediaPlayer = MediaPlayer.create(this,R.raw.alert_audio);

            mediaPlayer.setVolume(0.3f,0.3f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            stopAudioRunnable = new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!= null){
                        mediaPlayer.start();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        Toast.makeText(PredectionActivity.this, "Audio sopped after "+(duration/1000)+" seconds", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            handler.postDelayed(stopAudioRunnable,duration);
            Toast.makeText(this, "playing audio at 50% volume in loop", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (stopAudioRunnable != null){
            handler.removeCallbacks(stopAudioRunnable);
        }
    }
}