package edu.temple.listenup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends Activity {
    private String thisUser;
    private String chattingWith;
    private DataSnapshot chatData;
    private FirebaseDatabase myDatabase;
    private DatabaseReference chatReference;


    private ArrayList<Message> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        thisUser = intent.getStringExtra("user");
        chattingWith = intent.getStringExtra("match");
        myDatabase = FirebaseDatabase.getInstance();

        chatReference = myDatabase.getReference("chats").child(thisUser + chattingWith);

        setChatDatabaseListener();

    }

    public void setChatDatabaseListener(){
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                updateChatData(dataSnapshot);
                Log.d("Read database attempt", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read database attempt", "Failed to read value.", error.toException());
            }
        });
    }

    public void updateChatData(DataSnapshot dataSnapshot){

    }

}
