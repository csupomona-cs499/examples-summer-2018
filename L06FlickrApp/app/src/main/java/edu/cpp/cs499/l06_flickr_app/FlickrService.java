package edu.cpp.cs499.l06_flickr_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {

    @GET("/services/rest/")
    public Call<RecentPhotoResponse> getRecentPhotos(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("extras") String extras,
            @Query("format") String format,
            @Query("nojsoncallback") String nojsoncallback,
            @Query("auth_token") String auth_token,
            @Query("api_sig") String api_sig
    );
}
