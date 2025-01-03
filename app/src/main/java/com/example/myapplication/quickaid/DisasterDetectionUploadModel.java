package com.example.myapplication.quickaid;

public class DisasterDetectionUploadModel {
    String windSpeed,rainAmount,humidity,temperature,prediction;

    public DisasterDetectionUploadModel() {
    }

    public DisasterDetectionUploadModel(String windSpeed, String rainAmount, String humidity, String temperature, String prediction) {
        this.windSpeed = windSpeed;
        this.rainAmount = rainAmount;
        this.humidity = humidity;
        this.temperature = temperature;
        this.prediction = prediction;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(String rainAmount) {
        this.rainAmount = rainAmount;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
