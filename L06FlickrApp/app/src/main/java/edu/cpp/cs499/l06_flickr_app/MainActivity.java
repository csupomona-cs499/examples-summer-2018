package edu.cpp.cs499.l06_flickr_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    FlickrService service;

    @BindView(R.id.gridView)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        service = retrofit.create(FlickrService.class);

        Call<RecentPhotoResponse> call = service.getRecentPhotos("flickr.photos.getRecent",
                "a2086779788f5ebfa838bdc35fb22fde",
                "url_s", "json", "1", "72157698153796544-08e34a67a5efaedc",
                "364f6b9264a6d8421a955cf8ea063be2");

        call.enqueue(new Callback<RecentPhotoResponse>() {
            @Override
            public void onResponse(Call<RecentPhotoResponse> call, Response<RecentPhotoResponse> response) {
                RecentPhotoResponse photoResponse = response.body();
                Log.i("TEST", "GET RECENT PHOTOS: " + photoResponse.getPhotos().getTotal());
                final PhotoAdapter photoAdapter = new PhotoAdapter(
                        MainActivity.this, R.layout.gridview_item, photoResponse.getPhotos().getPhoto());
                gridView.setAdapter(photoAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, PhotoDetailsActivity.class);
                        intent.putExtra("url", photoAdapter.getItem(i).getUrl_s());
                        intent.putExtra("title", photoAdapter.getItem(i).getTitle());
                        intent.putExtra("owner", photoAdapter.getItem(i).getOwner());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<RecentPhotoResponse> call, Throwable t) {
                Log.e("TEST", "Failed to get the recent photos");
            }
        });
    }
}
