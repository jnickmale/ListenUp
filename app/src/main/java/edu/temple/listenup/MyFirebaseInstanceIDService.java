package edu.temple.listenup;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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
        code to store the token in the realtime database
         */
    }


}
