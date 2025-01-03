package com.example.myapplication.quickaid;

import android.content.res.AssetManager;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TFLiteHelper {

    private Interpreter tflite;

    public TFLiteHelper(String modelPath, AssetManager assetManager) {
        try {
            tflite = new Interpreter(loadModelFile(modelPath, assetManager));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MappedByteBuffer loadModelFile(String modelPath, AssetManager assetManager) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(assetManager.openFd(modelPath).getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetManager.openFd(modelPath).getStartOffset();
        long declaredLength = assetManager.openFd(modelPath).getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public Interpreter getInterpreter() {
        return tflite;
    }

    public float[] predict(float[] humidity, float[] temperature, float[] windSpeed, float[] pressure) {
        // Combine inputs if the model requires one tensor
        float[][] inputTensor = new float[1][4];

        inputTensor[0][1] = temperature[0];
        inputTensor[0][0] = humidity[0];
        inputTensor[0][2] = windSpeed[0];
        inputTensor[0][3] = pressure[0];

        // Prepare output tensor
        float[][] outputTensor = new float[1][1]; // Adjust NUM_CLASSES based on your model

        // Run the model
        tflite.run(inputTensor, outputTensor);

        // Return the first row of output tensor
        return outputTensor[0];
    }
}
