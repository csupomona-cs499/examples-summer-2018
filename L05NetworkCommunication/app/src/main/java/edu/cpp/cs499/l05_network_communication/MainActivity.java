package edu.cpp.cs499.l05_network_communication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.cpp.cs499.l05_network_communication.data.WeatherOutputData;

public class MainActivity extends AppCompatActivity {

    Button sendButton;
    TextView resultTextView;

    TextView minTextView;
    TextView maxTextView;
    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        resultTextView = findViewById(R.id.resultTextView);
        minTextView = findViewById(R.id.minTempTextView);
        maxTextView = findViewById(R.id.maxTempTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncTask<Void, Void, Void> loadingTask = new AsyncTask<Void, Void, Void>() {
                    private String result;
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            URL url = new URL("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream inputstream = new BufferedInputStream(urlConnection.getInputStream());

                            final int bufferSize = 1024;
                            final char[] buffer = new char[bufferSize];
                            final StringBuilder out = new StringBuilder();
                            Reader in = new InputStreamReader(inputstream, "UTF-8");
                            for (; ; ) {
                                int rsz = in.read(buffer, 0, buffer.length);
                                if (rsz < 0)
                                    break;
                                out.append(buffer, 0, rsz);
                            }
                            result = out.toString();
                        } catch (IOException e) {
                            Log.e("TEST", "Failed to send the request.", e);
                        } finally {
                            //urlConnection.disconnect();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            WeatherOutputData data = objectMapper.readValue(result, WeatherOutputData.class);
                            resultTextView.setText(result);
                            minTextView.setText("Min temp: " + data.getMain().getTemp_min());
                            maxTextView.setText("Max temp: " + data.getMain().getTemp_max());
                            descriptionTextView.setText(data.getWeather().get(0).getDescription());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                loadingTask.execute();

            }
        });
    }
}
