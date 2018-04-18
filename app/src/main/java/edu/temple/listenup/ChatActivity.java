package edu.temple.listenup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.temple.listenup.Fragments.ChatFragment;

public class ChatActivity extends AppCompatActivity {
    private String thisUser;
    private String chattingWith;
    private String chatID;
    private DataSnapshot chatData;
    private FirebaseDatabase myDatabase;
    private DatabaseReference chatReference;


    private ArrayList<Message> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        thisUser = intent.getStringExtra("username");
        chattingWith = intent.getStringExtra("matchUsername");
        chatID = intent.getStringExtra("chatID");
        myDatabase = FirebaseDatabase.getInstance();

        chatReference = myDatabase.getReference("chats").child(chatID);

        setChatDatabaseListener();

        Fragment chatFragment = ChatFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.chatFragHolder, chatFragment).commit();
    }

    public void setChatDatabaseListener(){
        chatReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //add the new message
                addMessage(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        chatReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                chatData = dataSnapshot;
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

    /**
     * update all the chat data with the chats DataSnapshot
     * @param dataSnapshot
     */
    public void updateChatData(DataSnapshot dataSnapshot){
        Message theNewMessage;
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            theNewMessage = ds.getValue(Message.class);
        }
        messages.add(dataSnapshot.getValue(Message.class));
    }

    /**
     * add a single message to the data
     * @param dataSnapshot
     */
    public void addMessage(DataSnapshot dataSnapshot){
        Message theNewMessage = dataSnapshot.getValue(Message.class);
        messages.add(theNewMessage);
    }


    private class Message{
        private String ID, content, fromUsername, toUsername;
        private Date dateSent, dateReceived;

        public Message(String messageID, String content, String dateSentString, String dateReceivedString, String fromUser, String toUser){
            ID = messageID;
            this.content = content;


            dateSent = null;
            dateReceived = null;
            DateFormat dateFormat = new SimpleDateFormat();
            try {
                dateSent = dateFormat.parse(dateSentString);
                dateReceived = dateFormat.parse(dateReceivedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            fromUsername = fromUser;
            toUsername = toUser;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFromUsername() {
            return fromUsername;
        }

        public void setFromUsername(String fromUsername) {
            this.fromUsername = fromUsername;
        }

        public String getToUsername() {
            return toUsername;
        }

        public void setToUsername(String toUsername) {
            this.toUsername = toUsername;
        }

        public Date getDateSent() {
            return dateSent;
        }

        public void setDateSent(Date dateSent) {
            this.dateSent = dateSent;
        }

        public Date getDateReceived() {
            return dateReceived;
        }

        public void setDateReceived(Date dateReceived) {
            this.dateReceived = dateReceived;
        }
    }

}
