package edu.cpp.cs499.l06_flickr_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailsActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView photoImageView;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.ownerTextView)
    TextView ownerTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String owner = getIntent().getStringExtra("owner");

        Picasso.get().load(url).into(photoImageView);
        titleTextView.setText(title);
        ownerTextView.setText(owner);
    }
}
