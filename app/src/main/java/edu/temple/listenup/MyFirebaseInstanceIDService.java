package edu.temple.listenup;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import edu.temple.listenup.Helpers.PreferencesUtils;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("tokenRefresh", "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String token){
        /*
            store the token in the realtime database
         */
        String userID = PreferencesUtils.getMySpotifyUserID(getApplicationContext());

        if(userID != null) {
            //if the spotify userID has already been recorded
            FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = myDatabase.getReference("fcmTokens").child(userID);
            databaseReference.setValue(token);
        }
    }


}
