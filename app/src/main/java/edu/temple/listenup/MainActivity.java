package edu.temple.listenup;
//Gmo branch

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import kaaes.spotify.webapi.android.models.UserPrivate;


public class MainActivity extends Activity implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private FirebaseAuth mAuth;
    // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "35c44a4ac64340ee951b71b2308ca072";//you receive the client id from the developer dashbored
    private Player mPlayer;
    private String myAccessToken;
    private DatabaseReference myDatabase;

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "listenup://callback";// test URI so spotify knows what app to send the info back to

    private AuthenticationResponse response;

    //test playbar

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = FirebaseDatabase.getInstance().getReference();
/*
        myDatabase.child("users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Log.i("MainActivity", dsp.getValue().toString());

                        }
                        Log.i("MainActivity", dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
*/

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);//signin object
        builder.setScopes(new String[]{"user-read-private", "streaming"});//the scope this (basically mean what we need from the object....)
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);// this call the login activity in the spotfiy app

    }

    @Override
    public void onLoggedIn() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
        Log.d("MainActivity", "User logged In");
        //mPlayer.playUri(null, "spotify:track:4jtyUzZm9WLc2AdaJ1dso7", 0, 0);// format for  track  ...(for testing)potify:track:4jtyUzZm9WLc2AdaJ1dso

    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
        mAuth.signOut();
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MainActivity", "User login failed");

    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "temporary");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("MainActivity", "connectionmessage");
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "playbackevnt");
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "played back error");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            //get the data from then intent
            response = AuthenticationClient.getResponse(resultCode, data);

            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                //get authentication token and set to myAccessToken variable
                myAccessToken = response.getAccessToken();
                Log.wtf("accessToken", myAccessToken);

                //sets access token in Shared Preferences if not saved already
                PreferencesUtils.setMyAccessToken(myAccessToken, getApplicationContext());

                //sets access token in the Spotify API, you need this to get the service, which in turn, allows you to make calls to the API
                SpotifyAPIManager.setMyAccessToken(myAccessToken);

                //You need to use an AsyncTask for network operations. Getting user from SpotifyAPI is a network process
                //Write SpotifyUser into our User object
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        UserPrivate user = SpotifyAPIManager.getService().getMe();
                        writeNewUser(user);
                    }
                });

                Config playerConfig = new Config(this, myAccessToken, CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {//method to initialize the player
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }

        }
    } // end onActivityResult

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    private void writeNewUser(UserPrivate user) {
        Log.i("MainActivity", "User added: " + user.display_name);
        Log.i("MainActivity", "User info: " + user.id);
        Log.i("MainActivity", "User info: " + user.email);
        try {//this check to see if rhe user has a image if the use
        Log.i("MainActivity", "Image info: " + user.images.get(0).url + "");
        }catch (IndexOutOfBoundsException e){
            System.out.println("this was the issue");
        }
        PreferencesUtils.setMyDisplayName(user.display_name, getApplicationContext());
        PreferencesUtils.setMySpotifyUserID(user.id, getApplicationContext());

        try {
            PreferencesUtils.setMyPicInfo(user.images.get(0).url + "", getApplicationContext());
        }catch (IndexOutOfBoundsException e){
            System.out.println("this was the issue");
        }


        User newUser = new User();

        newUser.setID(user.id);
        newUser.setDisplayName(user.display_name);
        newUser.setEmail(user.email);

        myDatabase.child("users").child(newUser.getID()).setValue(newUser);
    }

}
