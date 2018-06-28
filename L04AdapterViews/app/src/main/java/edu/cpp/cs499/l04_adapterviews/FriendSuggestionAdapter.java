package edu.cpp.cs499.l04_adapterviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FriendSuggestionAdapter extends ArrayAdapter<FriendSuggestion> {

    private List<FriendSuggestion> friends;
    private Context context;

    public FriendSuggestionAdapter(
            @NonNull Context context,
            int resource,
            @NonNull List<FriendSuggestion> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friends = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item_friend_suggestion, parent, false);

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        nameTextView.setText(friends.get(position).getName());

        TextView numTextView = view.findViewById(R.id.numTextView);
        numTextView.setText(friends.get(position).getNumMutualFriends() + " mutual friends");

        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        profileImageView.setImageResource(friends.get(position).getProfilePhotoResId());

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friends.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
