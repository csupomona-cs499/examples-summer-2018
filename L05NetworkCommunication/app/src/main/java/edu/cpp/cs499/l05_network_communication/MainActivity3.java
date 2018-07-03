package edu.cpp.cs499.l05_network_communication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.cs499.l05_network_communication.data.WeatherOutputData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity3 extends AppCompatActivity {

    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.minTempTextView)
    TextView minTextView;
    @BindView(R.id.maxTempTextView)
    TextView maxTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;

    WeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://samples.openweathermap.org")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(WeatherService.class);
    }

    @OnClick(R.id.sendButton)
    public void onSendButtonClick() {
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
}
