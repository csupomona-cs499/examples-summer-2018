package edu.cpp.cs499.l04_adapterviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FriendSuggestionAdapter friendSuggestionAdapter;

    private Button addFriendButton;
    private List<FriendSuggestion> friendSuggestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        addFriendButton = findViewById(R.id.addFriendButton);

        friendSuggestions = new ArrayList<>();
        friendSuggestions.add(new FriendSuggestion("Allison", 20, R.drawable.friend_1));
        friendSuggestions.add(new FriendSuggestion("Messi", 10, R.drawable.friend_2));
        friendSuggestions.add(new FriendSuggestion("C. Ronaldo", 30, R.drawable.friend_3));
        friendSuggestions.add(new FriendSuggestion("Ronaldo", 6, R.drawable.friend_4));
        friendSuggestions.add(new FriendSuggestion("Curry", 18, R.drawable.friend_5));
        friendSuggestions.add(new FriendSuggestion("Neymar", 29, R.drawable.friend_6));
        friendSuggestions.add(new FriendSuggestion("Bale", 34, R.drawable.friend_7));

        friendSuggestionAdapter = new FriendSuggestionAdapter(this, R.layout.listview_item_friend_suggestion, friendSuggestions);
        listView.setAdapter(friendSuggestionAdapter);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] names = new String[]{"Allison", "Messi", "C Ronaldo", "Ronaldo", "Curry"};
                int[] images = new int[]{R.drawable.friend_1,R.drawable.friend_2,R.drawable.friend_3,R.drawable.friend_4,R.drawable.friend_5};
                int index = new Random().nextInt(5);
                FriendSuggestion friendSuggestion =
                        new FriendSuggestion(names[index], new Random().nextInt(100), images[index]);
                friendSuggestions.add(friendSuggestion);
                friendSuggestionAdapter.notifyDataSetChanged();
            }
        });
    }
}
