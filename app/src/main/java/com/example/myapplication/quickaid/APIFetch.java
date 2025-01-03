package com.example.myapplication.quickaid;

import android.os.Bundle;
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

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIFetch extends AppCompatActivity {

    private String temperature;
    private String humidity;
    private String windSpeed;
    private String pressure;

    private TextView textviewTemperature;
    private TextView textviewHumidity;
    private TextView textviewWindSpeed;
    private TextView textviewPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apifetch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textviewTemperature= findViewById(R.id.temperatureTextAPI);
        textviewHumidity= findViewById(R.id.humidityTextAPI);
        textviewWindSpeed= findViewById(R.id.windSpeedTextAPI);
        textviewPressure= findViewById(R.id.pressureTextAPI);


        String url = "https://api.tomorrow.io/v4/weather/forecast?location=22.3071,73.1812&apikey=qoLty7hLos8lEeXdnbDggLBr4D8wFMSz";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(APIFetch.this, "Request failed.. "+e, Toast.LENGTH_SHORT).show();
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

                            temperature = values.has("temperature") ? values.get("temperature").getAsString() : "N/A";
                            humidity = values.has("humidity") ? values.get("humidity").getAsString() : "N/A";
                            windSpeed = values.has("windSpeed") ? values.get("windSpeed").getAsString() : "N/A";
                            pressure = values.has("pressureSurfaceLevel") ? values.get("pressureSurfaceLevel").getAsString() : "N/A";

                        }
                    }



                    runOnUiThread(()->{
                        textviewTemperature.setText(temperature);
                        textviewHumidity.setText(humidity);
                        textviewWindSpeed.setText(windSpeed);
                        textviewPressure.setText(pressure);
                    });




                } else {
                    Toast.makeText(APIFetch.this, "Request not successful!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}