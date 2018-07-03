package edu.cpp.cs499.l06_flickr_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Photo> {

    private Context context;
    private List<Photo> photos;

    public PhotoAdapter(@NonNull Context context, int resource, @NonNull List<Photo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.photos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.gridview_item, parent, false);
        ImageView imageView = view.findViewById(R.id.photoImageView);

        Picasso.get().load(photos.get(position).getUrl_s()).into(imageView);

        return view;
    }
}
