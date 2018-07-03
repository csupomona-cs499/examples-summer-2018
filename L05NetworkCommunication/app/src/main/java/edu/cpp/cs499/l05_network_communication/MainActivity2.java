package edu.cpp.cs499.l05_network_communication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://samples.openweathermap.org")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        final WeatherService service = retrofit.create(WeatherService.class);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<WeatherOutputData> callRequest =
                        service.getWeatherInfo("london,uk", "b6907d289e10d714a6e88b30761fae22");
                callRequest.enqueue(new Callback<WeatherOutputData>() {
                    @Override
                    public void onResponse(Call<WeatherOutputData> call, Response<WeatherOutputData> response) {
                        WeatherOutputData data = response.body();
                        minTextView.setText("Min temp: " + data.getMain().getTemp_min());
                        maxTextView.setText("Max temp: " + data.getMain().getTemp_max());
                        descriptionTextView.setText(data.getWeather().get(0).getDescription());
                    }

                    @Override
                    public void onFailure(Call<WeatherOutputData> call, Throwable t) {

                    }
                });

            }
        });
    }
}
