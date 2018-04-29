package edu.temple.listenup;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private String userID;


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        userID = intent.getExtras().getString("userID");
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("tokenRefresh", "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String token){
        /*
        code to store the token in the realtime database
         */
        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = myDatabase.getReference("fcmTokens").child(userID);
        databaseReference.setValue(token);
    }


}
