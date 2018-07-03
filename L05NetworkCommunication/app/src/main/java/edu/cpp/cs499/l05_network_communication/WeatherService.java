package edu.cpp.cs499.l05_network_communication;

import edu.cpp.cs499.l05_network_communication.data.WeatherOutputData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather")
    public Call<WeatherOutputData> getWeatherInfo(
            @Query("q") String q, @Query("appid") String appId);

}
