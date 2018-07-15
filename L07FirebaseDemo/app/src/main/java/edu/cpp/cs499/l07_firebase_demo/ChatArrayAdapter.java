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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<Message> {

    private Context context;
    private List<Message> messageList;

    public ChatArrayAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        this.context = context;
        this.messageList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (messageList.get(position).getSenderId().equals(UserManager.userId)) {
            view = layoutInflater.inflate(R.layout.listview_item_chat, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.listview_item_chat_left, parent, false);
        }

        if (messageList.get(position).getType() == Message.IMAGE) {
            TextView chatTextView = view.findViewById(R.id.chatMessageTextView);
            chatTextView.setText("");
            chatTextView.setVisibility(View.GONE);
            ImageView chatImageView = view.findViewById(R.id.chatMessageImageView);
            Picasso.get().load(messageList.get(position).getMessage())
                    .resize(200, 200)
                    .into(chatImageView);
        } else {
            TextView chatTextView = view.findViewById(R.id.chatMessageTextView);
            chatTextView.setText(messageList.get(position).getMessage());
        }


        TextView timeTextView = view.findViewById(R.id.timeTextView);
        timeTextView.setText(new Date(messageList.get(position).getTime()).toLocaleString());

        return view;
    }
}
