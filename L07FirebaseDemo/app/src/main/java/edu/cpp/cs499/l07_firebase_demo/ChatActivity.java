package edu.cpp.cs499.l07_firebase_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chatEditText)
    EditText chatEditText;
    @BindView(R.id.chatListView)
    ListView chatListView;

    private String chatKey;
    private List<Message> messages;

    private ChatArrayAdapter chatArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        String myUserId = UserManager.userId;
        String targetUserId = getIntent().getStringExtra("targetUserId");
        chatKey = myUserId.compareTo(targetUserId) > 0 ?
                targetUserId + "-" + myUserId : myUserId + "-" + targetUserId;

        loadChatMessage();
    }

    private void loadChatMessage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chat/" + chatKey);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    messages.add(message);
                }
                chatArrayAdapter = new ChatArrayAdapter(ChatActivity.this, R.layout.listview_item_chat, messages);
                chatListView.setAdapter(chatArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.sendButton)
    public void sendOnClicked() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Message message = new Message();
        message.setTime(System.currentTimeMillis());
        message.setMessage(chatEditText.getText().toString());
        message.setSenderId(UserManager.userId);

        DatabaseReference myRef = database.getReference("chat/" + chatKey + "/" + System.currentTimeMillis());
        myRef.setValue(message);
    }
}
