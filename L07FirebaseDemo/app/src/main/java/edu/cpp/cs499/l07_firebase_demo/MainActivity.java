package edu.cpp.cs499.l07_firebase_demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.schoolEditText)
    EditText schoolEditText;
    @BindView(R.id.friendListView)
    ListView listView;

    private FriendArrayAdapter friendArrayAdapter;
    private List<Friend> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        loadFriendList();
    }

    @OnClick(R.id.addButton)
    public void addButtonClicked() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Friend friend = new Friend();
        String friendId = UUID.randomUUID().toString().substring(0, 6);
        friend.setId(friendId);
        friend.setName(nameEditText.getText().toString());
        friend.setSchool(schoolEditText.getText().toString());

        DatabaseReference myRef = database.getReference("friends/" + UserManager.userId + "/" + friendId);
        myRef.setValue(friend);
    }

    private void loadFriendList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("friends/" + UserManager.userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Friend friend = ds.getValue(Friend.class);
                    friendList.add(friend);
                }
                friendArrayAdapter = new FriendArrayAdapter(MainActivity.this, R.layout.listview_friend_item, friendList);
                listView.setAdapter(friendArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Friend f = friendArrayAdapter.getItem(i);
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("targetUserId", f.getUserId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
