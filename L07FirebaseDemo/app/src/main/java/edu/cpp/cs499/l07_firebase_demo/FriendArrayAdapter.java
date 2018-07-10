package edu.cpp.cs499.l07_firebase_demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendArrayAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private List<Friend> friendList;

    public FriendArrayAdapter(@NonNull Context context, int resource, @NonNull List<Friend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friendList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_friend_item, parent, false);

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        nameTextView.setText(friendList.get(position).getName());
        TextView schoolTextView = view.findViewById(R.id.schoolTextView);
        schoolTextView.setText(friendList.get(position).getSchool());
        if (friendList.get(position).getImageUrl() != null) {
            ImageView imageView = view.findViewById(R.id.userImageView);
            Picasso.get().load(friendList.get(position).getImageUrl())
                    .resize(92, 92).into(imageView);
        }
        return view;
    }
}
