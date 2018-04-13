package edu.temple.listenup;
//Gmo branch
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import edu.temple.listenup.Fragments.UserSettingsFragment;


public class MainActivity extends Activity implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    Fragment userSettingsFragment = new UserSettingsFragment();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private FirebaseAuth mAuth;
    // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
    private static final int REQUEST_CODE = 1337;
    private  static final String CLIENT_ID = "35c44a4ac64340ee951b71b2308ca072";//you recevie the client id from the developer dashbored
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

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);//signin object
        builder.setScopes(new String[]{"user-read-private", "streaming"});//the scope this (basicaly mean what we need from the object....)
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);// this call the login activity in the spotfiy app

    }

    @Override
    public void onLoggedIn() {
        //attaching the user settings fragment after user logs in
        Intent intent = new Intent(this,HomeScreen.class);
        //todo: place value need in bundle to send to HomeScreen
        startActivity(intent);
        Log.d("MainActivity", "User logged In");
        mPlayer.playUri(null, "spotify:track:4jtyUzZm9WLc2AdaJ1dso7", 0, 0);// format for  track  ...(for testing)potify:track:4jtyUzZm9WLc2AdaJ1dso

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
                myAccessToken = response.getAccessToken();
                Log.wtf("accessToken",myAccessToken);
                writeNewUser(myAccessToken);
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
                        System.out.println("yeeeeeeeees");
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }


        }
    }//end onActivityResult

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    private void writeNewUser(String userID) {
        Log.i("MainActivity", "User added: " + userID);
       myDatabase.child("users").child("userID").setValue(userID);
//DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//ref.child("users").push();
//ref.child("users").child("userID").setValue("test");
    }

}
